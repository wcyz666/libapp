package com.example.libapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MoreFragment extends Fragment{
	public static final String PREFS_NAME = "LoginPrefs";
	private View view;
	//private RelativeLayout logout = null;
	//private RelativeLayout record = null;
	//private RelativeLayout reserve = null;
	//private RelativeLayout contact = null;
	//private RelativeLayout tips = null;
	//private RelativeLayout openBrowser = null;
	//private RelativeLayout bookRoom = null;
	private final int[] resouceIds = {R.drawable.more_logout, R.drawable.more_record,
			R.drawable.more_reserve, R.drawable.more_contact, R.drawable.more_tips, R.drawable.more_browser,
			R.drawable.book_room};
	
	private final String[] buttStrings = {"Logout", "Record", "Reserved List", "Contact us", "Tips",
			"Open in Browser", "Booking Room"};
	private AlertDialog.Builder aBuilder;
	
	private final View.OnClickListener[] listeners = {
			new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					aBuilder = new AlertDialog.Builder(getActivity());
					aBuilder.setIcon(R.drawable.ic_launcher).setTitle("Logout").setMessage("Do you want to logout this user?")
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							SharedPreferences settings = (MoreFragment.this).getActivity().getSharedPreferences(PREFS_NAME, 0);
				            SharedPreferences.Editor editor = settings.edit();
				            editor.remove("logged");
				            editor.commit();
				            Intent intent = new Intent(MoreFragment.this.getActivity(),LoginActivity.class);
				            startActivity(intent);
				            (MoreFragment.this).getActivity().finish();	
						}
					});
					aBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					aBuilder.create().show();
				}
		},
		
		new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment newFragment = new RecordDialog();
			    newFragment.show(getFragmentManager(), "record");
			}
		},
		
		new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MoreFragment.this.getActivity(), ReservedListActivity.class);
				startActivity(intent);
			}
		},
		
		new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.lib.cuhk.edu.hk/Common/Reader/Channel/ShowPage.jsp?Cid=389&Pid=35&Version=0&Charset=iso-8859-1&page=0"));
				startActivity(intent);
			}
		},		
		new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		},
		
		new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.lib.cuhk.edu.hk/Common/Reader/Channel/ShowCalendar.jsp?Cid=763&Pid=46&Version=0&Charset=iso-8859-1"));
				startActivity(intent);
			}
		},	
		
		new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MoreFragment.this.getActivity(), BookRoomList.class);
				startActivity(intent);
			}
		}
	};
	
	private ListView listView;
	private MoreListAdapter moreListAdapter;
	
	private class MoreListAdapter extends BaseAdapter{

		private MoreButton mButton;
		
		public MoreListAdapter(Context context){
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 7;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return buttStrings[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			mButton = new MoreButton(getActivity(), null, resouceIds[arg0]);
			mButton.setText(buttStrings[arg0]);
			mButton.setTextColor(Color.BLACK);
			mButton.setBackgroundColor(Color.WHITE);
			mButton.setHeight(40);
			mButton.setTextSize(18);
			//mButton.setTextAlignment(Button.TEXT_DIRECTION_LTR);
			mButton.setGravity(Gravity.LEFT);
			mButton.setPadding(150, 20, 0, 3);
			mButton.setOnClickListener(listeners[arg0]);
			return mButton;
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_more, container, false);
		
		listView = (ListView) view.findViewById(R.id.listView1);
		moreListAdapter = new MoreListAdapter(getActivity());
		listView.setAdapter(moreListAdapter);

		return view;
	}
}
