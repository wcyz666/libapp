package com.example.libapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Review extends Activity {
	private View view = null;
	private LinearLayout slayout = null;
	private LinearLayout llayout = null;
	private ListView listView = null;
	// String shoud Post class
	private ArrayList<NormalBook> list = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.activity_review, null);
		slayout = (LinearLayout) view.findViewById(R.id.reviews);	
		llayout = (LinearLayout) inflater.inflate(R.layout.post_review, null);
		listView = (ListView) llayout.findViewById(R.id.available_reviews);
		
		// add list of reviews
		list = new ArrayList<NormalBook>();
		NormalBook book = null;
		for(int i = 0; i < 10; i++) {
			book = new NormalBook(null, null, null, null, null, null, null, null, null);
			list.add(book);
		}
		listView.setAdapter(new ReviewsAdapter(this,list));
		slayout.addView(llayout);
		setContentView(view);
	}
	public class ReviewsAdapter extends BaseAdapter {
		private Context context;
        private ArrayList<?> list;
        public ReviewsAdapter(Context context, ArrayList<?> arrayList){
            this.context = context;
            this.list = arrayList;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        	if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.one_post, null);
            }          
        	TextView author = (TextView) convertView.findViewById(R.id.post_author);
        	TextView opinion = (TextView) convertView.findViewById(R.id.post_opinion);
        	author.setText("Mr.Java");
        	opinion.setText("test...");
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
	public class GetReviewAsync extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}
		@Override
		protected void onPostExecute(Void param) {
		}
	}

}
