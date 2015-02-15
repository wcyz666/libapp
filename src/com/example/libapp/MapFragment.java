package com.example.libapp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MapFragment extends Fragment{
	public static final String PREFS_NAME = "LoginPrefs";
	private View view;
	private final int[] resouceIds = {R.drawable.cuhk, R.drawable.cc, R.drawable.uc, R.drawable.na};
	
	private final String[] buttStrings = {"University Library", "Elisabeth Luce Moore Library",
			"Wu Chung Multimedia Library", "Ch'ien Mu Library"};
	
	private final View.OnClickListener[] listeners = {
			new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MapFragment.this.getActivity(),SearchListActivity.class);
					startActivity(intent);
				}
		},
		
		new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MapFragment.this.getActivity(), "Not Available", Toast.LENGTH_SHORT).show();
			}
		},		
		new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MapFragment.this.getActivity(), "Not Available", Toast.LENGTH_SHORT).show();
			}
		},
		
		new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MapFragment.this.getActivity(), "Not Available", Toast.LENGTH_SHORT).show();
			}
		},		
	};
	
	private ListView listView;
	private MoreListAdapter moreListAdapter;
	
	private class MoreListAdapter extends BaseAdapter{

		private MoreButton mButton;
		

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 4;
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
			mButton.setHeight(50);
			mButton.setGravity(Gravity.LEFT);
			mButton.setPadding(150, 30, 0, 3);
			mButton.setOnClickListener(listeners[arg0]);
			return mButton;
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_more, container, false);

		listView = (ListView) view.findViewById(R.id.listView1);
		moreListAdapter = new MoreListAdapter();
		listView.setAdapter(moreListAdapter);

		return view;
	}
}
