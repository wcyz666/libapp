package com.example.libapp;

import java.util.ArrayList;

import android.app.Activity;
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

public class NewCollectionActivity extends Activity {
	private ArrayList<NormalBook> newCollectionList = null;
	private NewCollectionAdapter adapter = null;
	private ListView listView = null;
	private View view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		newCollectionList = new ArrayList<NormalBook>();
		NormalBook book = null;
//=============refine after connecting to internet===========
		for(int i = 0; i < 10; i++) {
			book = new NormalBook("Java Book One","Author","Location","Status","Cover_image","url", null, null, null);
			newCollectionList.add(book);
		}
//========================================================
		adapter = new NewCollectionAdapter(this,newCollectionList);
        LayoutInflater mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.activity_new_collection, null);
		listView = (ListView) view.findViewById(R.id.list_new_collection);
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
//================enlarge the listview===========================
				int itemOnView = arg1 + arg2;
				if(itemOnView == arg3) {
				NormalBook book = null;
				for(int i = 0; i < 10; i++) {
					book = new NormalBook("Java Book Two","Author","Location","Status","Cover_image","url", null, null, null);
					newCollectionList.add(book);
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
				Toast.makeText((NewCollectionActivity.this),"succeed...",Toast.LENGTH_LONG).show();
				Intent intent = new Intent((NewCollectionActivity.this),OneBookActivity.class);
				intent.putExtra("URL", ((ImageView) view.findViewById(R.id.book_cover)).getContentDescription());
				startActivity(intent);
			}
		}); 
		listView.setAdapter(adapter);
		setContentView(view);
	}
	
	
	public class NewCollectionAdapter extends BaseAdapter {
		private Context context;
        private ArrayList<NormalBook> list;
        public NewCollectionAdapter(Context context, ArrayList<NormalBook> bookList){
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
//           cover.setContentDescription(c.getBookURL());
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
