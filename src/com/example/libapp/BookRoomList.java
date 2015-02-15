package com.example.libapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.libapp.BookRoomDialog.OnCancelSelectedListener;
import com.example.libapp.PickEndTimeDialog.OnBookedListener;

public class BookRoomList extends Activity implements OnCancelSelectedListener, OnBookedListener {
	private ArrayList<RoomRecord> recordlist = null;
	private RoomRecordAdapter adapter = null;
	private ListView listView = null;
	private LinearLayout layout;
	private RelativeLayout button;
	public static final String PREFS_NAME = "LoginPrefs";
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setTitle("Viewing");
        pDialog.setMessage("Finding Booking Records");		
		
		new RoomRecordAsync().execute();
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		layout = (LinearLayout) inflater.inflate(R.layout.activity_book_room_list, null);
		button = (RelativeLayout) layout.findViewById(R.id.book_room_button);
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// Book Dialog
				System.out.println("IN");
				DialogFragment newFragment = new PickDateDialog();
			    newFragment.show(getFragmentManager(), "setDate");
			}			
		});
		recordlist = new ArrayList<RoomRecord>();
		listView = (ListView) layout.findViewById(R.id.list_book_room_result);
		listView.setOnItemClickListener(new OnItemClickListener() {  
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				System.out.println("IN");
				DialogFragment newFragment = new BookRoomDialog();
                Bundle args = new Bundle();
                args.putInt("cancel", position);
                newFragment.setArguments(args);
			    newFragment.show(getFragmentManager(), "book room cancel");
			}
		}); 
	    setContentView(layout);      
	}
	
	public class RoomRecordAdapter extends BaseAdapter {
		private Context context;
        private ArrayList<RoomRecord> list;
        public RoomRecordAdapter(Context context, ArrayList<RoomRecord> list){
            this.context = context;
            this.list = list;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.book_room_result, null);
            }            
           final RoomRecord c = list.get(position); 
           // to do something with the String
           TextView location = (TextView) convertView.findViewById(R.id.room_location);
           TextView time = (TextView) convertView.findViewById(R.id.room_time);
           TextView duration = (TextView) convertView.findViewById(R.id.room_duration);
           ImageButton map = (ImageButton) convertView.findViewById(R.id.book_map);
           map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// show the map
				// start the display location class function
				Toast.makeText(context,"map...",Toast.LENGTH_LONG).show();
				Intent intent = new Intent(context, DisplayMessageActivity.class);
		    	intent.putExtra(MainActivity.EXTRA_MESSAGE1, "item");
		    	intent.putExtra(MainActivity.EXTRA_MESSAGE2, c.getRoom().split("-")[0].trim());
		    	startActivity(intent);
			}        	   
           });
           
           // Library Group Study Room information
           location.setText(c.getLocation());
           time.setText(c.getStartDay());
           duration.setText(c.getDuration());
           
           return convertView;
        }
        @Override
        public int getCount() {
            return list.size();
        }
     
        @Override
        public Object getItem(int position) {       
            return list.get(position);
        }
     
        @Override
        public long getItemId(int position) {
            return position;
        }
	}
	
	public class RoomRecordAsync extends AsyncTask<Void, Void, ArrayList<RoomRecord>> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog.show();
		}
		
		@Override
		protected ArrayList<RoomRecord> doInBackground (Void... arg0) {
			Thread.currentThread().setName("RoomRecordAsync...");
			// for testing, use Java---test.setKeyWords("Java");
			SharedPreferences settings = BookRoomList.this.getSharedPreferences(PREFS_NAME, 0);
		    String mSID = settings.getString("SID", null);
		    String mPassword = settings.getString("pw", null);
	        RoomViewer test = new RoomViewer();
	        test.setUsernameAndPassword(mSID, mPassword);
	        test.viewingExec();
	        return (ArrayList)test.getRecord();
		}
		@Override
		protected void onPostExecute(ArrayList<RoomRecord> list) {			
			System.out.println("size is"+list.size());
			pDialog.dismiss();
			if(list.size() == 0) {
				Toast.makeText(BookRoomList.this,"sorry, no booking",Toast.LENGTH_LONG).show();
				recordlist.add(new RoomRecord("").setNull());
			} else {
				recordlist = list;			
			}
			adapter = new RoomRecordAdapter(BookRoomList.this, recordlist);
        	listView.setAdapter(adapter);
		}
	}
	
	public class AfterActionAsync extends AsyncTask<Void, Void, ArrayList<RoomRecord>> {
		@Override
		protected ArrayList<RoomRecord> doInBackground (Void... arg0) {
			pDialog.show();
			Thread.currentThread().setName("RoomRecordAsync...");
			// for testing, use Java---test.setKeyWords("Java");
			SharedPreferences settings = BookRoomList.this.getSharedPreferences(PREFS_NAME, 0);
		    String mSID = settings.getString("SID", null);
		    String mPassword = settings.getString("pw", null);
	        RoomViewer test = new RoomViewer();
	        test.setUsernameAndPassword(mSID, mPassword);
	        test.viewingExec();
	        List<RoomRecord> list = test.getRecord();
			return (ArrayList<RoomRecord>) list;
		}
		@Override
		protected void onPostExecute(ArrayList<RoomRecord> list) {			
			System.out.println("size is"+list.size());
			recordlist.clear();	
			pDialog.dismiss();
			if(list.size() == 0) {
				Toast.makeText(BookRoomList.this,"sorry, no booking",Toast.LENGTH_LONG).show();
				recordlist.add(new RoomRecord("").setNull());
			} else {
				recordlist.addAll(list);			
			}
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onCancelSelected() {
		// TODO Auto-generated method stub
		//new AfterActionAsync().execute();
	}

	@Override
	public void onBooked() {
		// TODO Auto-generated method stub
		//new AfterActionAsync().execute();				
	}
	
}
