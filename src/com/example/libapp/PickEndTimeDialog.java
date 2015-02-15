package com.example.libapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class PickEndTimeDialog extends DialogFragment {
	private int year;
	private int month;
	private int day;
	private int startHour;
	private int startMinute;
	private int endHour;
	private int endMinute;
	public static final String PREFS_NAME = "LoginPrefs";
	private ProgressDialog pDialog;
	private Handler handler;
	private View view;
	private LayoutInflater inflater;
	private OnBookedListener cl;
	private Context context;
	private NotificationManager nManager;
	private Notification notification;
	private Notification.Builder builder2;
	private StringBuffer roomInfo;
	private StringBuffer roomPosition;
	

	private TimePicker picker;
	
	@SuppressLint({ "InflateParams", "HandlerLeak" })
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		context = getActivity();

		roomInfo = new StringBuffer("");
		roomPosition = new StringBuffer("");
		Bundle b = getArguments();
		year = b.getInt("year");
		month = b.getInt("month");
		day = b.getInt("day");
		startHour = b.getInt("startHour");
		startMinute = b.getInt("startMinute");
		inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        roomInfo.append(year).append('-').append(month).append('-').append(day).append(' ').
        append(startHour).append(':').append(startMinute).append(' ').append('-').append(' ');
		view = inflater.inflate(R.layout.book_room_time_picker, null);
        picker = (TimePicker) view.findViewById(R.id.time_picker);

        setDisabledTextViews(picker);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        
        handler = new Handler(){
        	@SuppressLint("NewApi")
			@Override
        	public void handleMessage(Message msg) {
        		// TODO Auto-generated method stub
        		super.handleMessage(msg);
        		
        		switch (msg.what) {
				case 1:
					if (pDialog != null){
						Log.i("wcyz666", "aaaaa");
						pDialog.cancel();
					}
					else
						Log.i("wcyz666", "aaaa");
					break;
				case 2:
			        pDialog.setTitle("Booking");
			        pDialog.setMessage("Finding rooms");
			        pDialog.show();
					break;
				case 3:
					Toast.makeText(context, (String)msg.obj, Toast.LENGTH_SHORT).show();
					break;
				case 4:
					RemoteViews rViews = new RemoteViews(context.getPackageName(), R.layout.notificationbar_download);
					rViews.setTextViewText(R.id.content_view_text2, roomInfo.toString());					
					rViews.setTextViewText(R.id.content_view_text3, roomPosition.toString());
					notification = builder2.build();
					notification.bigContentView = rViews;
					nManager.notify(1000, notification);
					break;
				default:
					break;
				}
        	}
        };
		notificationinit();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setMessage(R.string.book_room_end_time)
           .setPositiveButton(R.string.book_room_dialog_done, new DialogInterface.OnClickListener() {
               @Override
			public void onClick(DialogInterface dialog, int id) {
                   // FIRE ZE MISSILES!
            	   endHour = picker.getCurrentHour();
                   endMinute = picker.getCurrentMinute() < 30 ? 0 : 30;
                   roomInfo.append(endHour).append(':').append(endMinute).append("   ");
                   dialog.dismiss();
                   new BookRoomTask().execute(getActivity());
               }
           })
           .setNegativeButton(R.string.book_room_dialog_no, new DialogInterface.OnClickListener() {
               @Override
			public void onClick(DialogInterface dialog, int id) {
                   // User cancelled the dialog
            	  dialog.dismiss();
            	  roomInfo.delete(0, roomInfo.length());
               }
           });
    // Create the AlertDialog object and return it
    return builder.create();	
    }
	
	@SuppressLint("NewApi")
	private void notificationinit(){
		
		nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(context, BookRoomList.class);
		PendingIntent peIntent = PendingIntent.getActivity(context, 0, intent, 0);
		builder2 = new Notification.Builder(context);
		builder2.setContentIntent(peIntent).
		setSmallIcon(R.drawable.ic_launcher).
		setDefaults(Notification.DEFAULT_ALL).setAutoCancel(false).
		setTicker("Your reservation has been completed");
	}
	
	
    public interface OnBookedListener {
    	public void onBooked();
    }
	
	private void onBooked() {
		cl.onBooked();
	}
		
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			cl = (OnBookedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnCancelSelectedListener");
		}
	}

    
    private void setDisabledTextViews(ViewGroup dp) 
    {
        for (int x = 0, n = dp.getChildCount(); x < n; x++) 
        {
            View v = dp.getChildAt(x);
            if (v instanceof TextView) 
            {
                v.setEnabled(false);
            } 
            else if (v instanceof ViewGroup) 
            {
                 setDisabledTextViews((ViewGroup)v);
            }
        }
    }
	
	class BookRoomTask extends AsyncTask<Context, Void, Map<String, String>>{
    	
    	Map<String, String> myResult;
    	String booknum;
    	String[] roomList, roomList2;
    	RoomBooker rb;
    	private Context context;
    	@Override
    	protected void onPreExecute() {
    		// TODO Auto-generated method stub
            Message message = Message.obtain();
            message.what = 2;
            handler.sendMessage(message);
    		super.onPreExecute();
    	}
    	
		@Override
		protected Map<String, String> doInBackground(Context... params) {
			// TODO Auto-generated method stub
			context = params[0];
			SharedPreferences settings = PickEndTimeDialog.this.getActivity().getSharedPreferences(PREFS_NAME, 0);
		    String mSID = settings.getString("SID", null);
		    String mPassword = settings.getString("pw", null);		    
			rb = new RoomBooker();
			rb.setUsernameAndPassword(mSID, mPassword);
			rb.setParameters(year+"", month+"", day+"", startHour+"", startMinute+"", endHour+"", endMinute+"", "53722423");
			return rb.bookingExecFirstPhase();
		}
    	
		@Override
		protected void onPostExecute(Map<String, String> result) {
			// TODO Auto-generated method stu		
			
			pDialog.dismiss();
			Log.i("wcyz666", "ssss111");
			if (result == null || result.size() == 0){
	            Message message = Message.obtain();
	            message.what = 3;
	            message.obj = rb.getError();
	            handler.sendMessage(message);
				roomInfo.delete(0, roomInfo.length());
			}
			else{
				roomList = result.keySet().toArray(new String[result.keySet().size()]);
				List<String> list = new ArrayList<>();
				for (String room : roomList) {
					list.add(room + RoomSeat.querySeat(room));	
				}
				roomList2 = list.toArray(new String[list.size()]);
		        myResult = result; 
		        AlertDialog.Builder builder = new AlertDialog.Builder(context);
		        AlertDialog aDialog = builder.setTitle("Choose a room").setCancelable(false)
		        .setSingleChoiceItems(roomList2, -1, new DialogInterface.OnClickListener() {
					 
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						booknum = myResult.get(roomList[which]);
						dialog.dismiss();			
		                pDialog.setTitle("Booking");
		                pDialog.setMessage("Booking rooms...");
		                pDialog.show();
		                roomPosition.append(roomList[which]);
		                new BookConfirmTask().execute(booknum);
		                
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						roomInfo.delete(0, roomInfo.length());
					}
				}).create(); 
		        aDialog.show();
			}
		}
		
		class BookConfirmTask extends AsyncTask<String, Void, Void>{

			@Override
			protected Void doInBackground(String... params) {
				// TODO Auto-generated method stub
				rb.bookingExecSecondPhase(params[0]);
				return null;
			}
			
			@SuppressLint("NewApi")
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				pDialog.dismiss();
	            Message message = Message.obtain();
	            message.what = 4;
	            handler.sendMessage(message);
				PickEndTimeDialog.this.onBooked();
			}
		}
		
    }
    
}