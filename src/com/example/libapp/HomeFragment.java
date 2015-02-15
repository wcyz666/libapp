package com.example.libapp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment{
	private static final String TAG = "MainActivity";
	private View view = null;
	private LinearLayout layout = null;
	private HorizontalScrollView scrollView = null;
	private RecentActivitiesAdapter adapter = null;
	private ArrayList<LibActivity> activityList = null;
	private ListView listView = null;
	private LinearLayout newCollectionBar = null;
	private int scrollX = 0;
	private int scrollY = 0;
	private ArrayList<URL> urlList = null;
	private ProgressBar pBar;
	private TextView textView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment		
		view = inflater.inflate(R.layout.fragment_home, container, false);
		scrollView = (HorizontalScrollView) view.findViewById(R.id.horizontal_scroll_view);
		layout = (LinearLayout) scrollView.findViewById(R.id.book_collection_layout);
		listView = (ListView) view.findViewById(R.id.activity_list);
		newCollectionBar = (LinearLayout) view.findViewById(R.id.new_collections_bar);
		pBar = (ProgressBar) view.findViewById(R.id.progressbar);
		textView = (TextView) view.findViewById(R.id.loadingtextview);
		activityList = new ArrayList<LibActivity>();
		urlList = new ArrayList<URL>();
		newCollectionBar.setClickable(true);
		newCollectionBar.setOnClickListener(new OnClickListener() {
			@Override
	        public void onClick(View v) {
					Toast.makeText((HomeFragment.this).getActivity(),"succeed...",Toast.LENGTH_LONG).show();
					Intent intent = new Intent((HomeFragment.this).getActivity(),NewCollectionActivity.class);
					startActivity(intent);
	        }
		});		
//===========DONE by Book Class==================		
		View itemView = null;
		ImageView img = null;
		TextView title = null;
		for(int i = 0; i < 6; i++) {
			itemView = inflater.inflate(R.layout.book_home_collection,null);
			img = (ImageView) itemView.findViewById(R.id.book_cover);
			img.setContentDescription("url");
			title = (TextView) itemView.findViewById(R.id.book_title);
			img.setImageResource(R.drawable.java1);
			title.setText("Java Book One\nJava Book");
			itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent((HomeFragment.this).getActivity(),OneBookActivity.class);
					intent.putExtra("URL", ((ImageView) v.findViewById(R.id.book_cover)).getContentDescription());
					startActivity(intent);
				}
				
			});
			layout.addView(itemView);
		}
//===========DONE by Book Class==================				
//===========Get the new activities online and show them all========
			new RecentActivityAsync().execute();
		return view;		
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		scrollX = scrollView.getScrollX();
		scrollY = scrollView.getScrollY();
	}
	@Override
	public void onResume()
	{
		super.onResume();
		//this is important. scrollTo doesn't work in main thread.
		scrollView.post(new Runnable() {
			@Override
			public void run() {
				scrollView.scrollTo(scrollX, scrollY);
			}
		});
	}
	
	public class RecentActivitiesAdapter extends BaseAdapter {
		private Context context;
        private ArrayList<LibActivity> list;
        public RecentActivitiesAdapter(Context context, ArrayList<LibActivity> arrayList){
            this.context = context;
            this.list = arrayList;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.recent_activity, null);
            }            
           LibActivity c = list.get(position); 
           TextView content = (TextView) convertView.findViewById(R.id.recent_activity_content);
           TextView time = (TextView) convertView.findViewById(R.id.recent_activity_time);
           content.setText(c.getTitle());
           time.setText(c.getDateString());
           return convertView;
        }
        @Override
        public int getCount() {
            return activityList.size();
        }
     
        @Override
        public Object getItem(int position) {       
            return activityList.get(position);
        }
     
        @Override
        public long getItemId(int position) {
            return position;
        }
	}
	public class RecentActivityAsync extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			LibActvityParser test = new LibActvityParser();
			ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			    if (networkInfo != null && networkInfo.isConnected()) {
					test.setURL("http://lib.cuhk.edu.hk/Common/Reader/Channel/ShowPage.jsp?Cid=90&Pid=13&Version=0&page=0");
					test.getHTML("<td class=\"a2\"> *<a href=\'([^\']*)\'> *(<span style=\"[^\"]*\"> *<font [^>]*>)?([^<]*)(<\\/FONT><\\/SPAN>)? - \\(([^\\)]*)\\)");
			    } else {
			        Log.d(TAG,"get error.......");
			    }
			List<LibActivity> con = test.getLibActvity();
			urlList = (ArrayList<URL>) test.getAllURL();		
			LibActivity la = null;
			for(int i = 0; i < con.size(); i++) {
				la = new LibActivity();
				la.setTitle(con.get(i).getTitle());
				la.setDate(con.get(i).getDateString());
				activityList.add(la);
				Log.d(TAG,(con.get(i)).getTitle());
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void param) {
			pBar.setVisibility(View.GONE);
			textView.setVisibility(View.GONE);
			adapter = new RecentActivitiesAdapter(getActivity(),activityList);
			listView.setAdapter(adapter);
		
			listView.setOnItemClickListener(new OnItemClickListener() {  
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlList.get(position).toString().replace("amp;", "")));				
					startActivity(intent);
				}
			}); 

		}
	}
}
