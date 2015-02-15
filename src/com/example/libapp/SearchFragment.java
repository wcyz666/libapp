package com.example.libapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchFragment extends Fragment{
	private ArrayList<NormalBook> recommendationList = null;
	private RecommendationAdapter adapter = null;
	private ListView listView = null;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_search, container, false);
		recommendationList = new ArrayList<NormalBook>();
		NormalBook book = null;
//=============refine after connecting to internet===========
		for(int i = 0; i < 10; i++) {
			book = new NormalBook("Java Book One"+i,"Author","Location","Status","Cover_image","url", null, null, null);
			recommendationList.add(book);
		}
//========================================================
		listView = (ListView) view.findViewById(R.id.list_recommendation);
		adapter = new RecommendationAdapter(getActivity(),recommendationList);
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
//================enlarge the listview===========================
				int itemOnView = arg1 + arg2;
				if(itemOnView == arg3) {
				NormalBook book = null;
				for(int i = 0; i < 10; i++) {
					book = new NormalBook("Java Book Two"+i,"Author","Location","Status","Cover_image","url", null, null, null);
					recommendationList.add(book);
				}
				adapter.notifyDataSetChanged();
				}
//============================================================
			}
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub				
			}			
		});

		listView.setOnItemClickListener(new OnItemClickListener() {  
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getActivity(),"succeed...",Toast.LENGTH_LONG).show();
				Intent intent = new Intent(getActivity(),OneBookActivity.class);
				intent.putExtra("URL", ((ImageView) view.findViewById(R.id.book_cover)).getContentDescription());
				startActivity(intent);
			}
		}); 
		listView.setAdapter(adapter);
	    return view;      
	}
	public static class RecommendationAdapter extends BaseAdapter {
		private Context context;
        private ArrayList<NormalBook> list;
        public RecommendationAdapter(Context context, ArrayList<NormalBook> bookList){
            this.context = context;
            this.list = bookList;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.book_new_collection, null);
            }            
           NormalBook c = list.get(position); 
           ImageView cover = (ImageView) convertView.findViewById(R.id.book_cover);
           TextView title = (TextView) convertView.findViewById(R.id.book_title);
           TextView status = (TextView) convertView.findViewById(R.id.book_status);
           ImageButton map = (ImageButton) convertView.findViewById(R.id.book_map);
           map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// show the map
				Toast.makeText(context,"map...",Toast.LENGTH_LONG).show();
			}        	   
           });
           cover.setImageResource(R.drawable.java1);
//           cover.setContentDescription(c.getUrl());
           title.setText(c.getTitle());
           status.setText(c.getStatus());
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
}
