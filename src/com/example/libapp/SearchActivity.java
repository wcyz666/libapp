package com.example.libapp;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

public class SearchActivity extends Activity{
	private ArrayList<NormalBook> resultList = null;
	private SearchResultAdapter adapter = null;
	private ListView listView = null;
	private View view;
	private BookParser test = null;
	private boolean loaded = false;
	private TextView searchString;
	private ProgressDialog pDialog;
	
	@SuppressLint("InflateParams")
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setTitle("Viewing");
        pDialog.setMessage("Finding Booking Records");	
		
        LayoutInflater mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.search, null);
        searchString = (TextView) view.findViewById(R.id.search_string);
        resultList = new ArrayList<NormalBook>();
		test = new BookParser();
        Intent intent = getIntent();
        
        
        
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
        	String query = intent.getStringExtra(SearchManager.QUERY);
        	searchString.setText(query);
        	String[] sarr = query.split(":");
        	if("loc".equalsIgnoreCase(sarr[0])) {
		    	Intent i = new Intent(this, DisplayMessageActivity.class);
		    	i.putExtra(MainActivity.EXTRA_MESSAGE1, "book");
		    	i.putExtra(MainActivity.EXTRA_MESSAGE2, sarr[1]);
		    	startActivity(i);
        		finish();
        	} else {
        		doSearch(query);
        	}
        }
        listView = (ListView) view.findViewById(R.id.list_search_result);
        listView.setOnScrollListener(new OnScrollListener() {
        	@Override
        	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
        //================enlarge the listview===========================
        //================next page======================================
        		int itemOnView = arg1 + arg2;  
        		if (loaded) {
	        		if(itemOnView == arg3) {
	        			loaded = false;
	        			new NextPageAsync().execute();
	        		}
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
        		ImageView img = (ImageView) view.findViewById(R.id.book_cover);
        		img.buildDrawingCache();
        		Intent intent = new Intent((SearchActivity.this),OneBookActivity.class);
        	    Bitmap bitmap = img.getDrawingCache();
        	    intent.putExtra("BitmapImage", bitmap);
        	    NormalBook nb = resultList.get(position);
        	    intent.putExtra("BookTitle",nb.getTitle());
        	    intent.putExtra("BookStatus", nb.getStatus());
        	    intent.putExtra("BookAuthor", nb.getAuthor());
        	    intent.putExtra("BookEdition", nb.getEdition());
        	    intent.putExtra("BookPress",nb.getPress());
        	    intent.putExtra("BookLocation",nb.getBookNumber());
        		startActivity(intent);
        	}
        }); 
    	setContentView(view);            
    }
	//=================GET books from online library=============================
	private void doSearch(String queryStr) {
        System.out.println("searching..." + queryStr);       
        new SearchAsync().execute(queryStr);		
    }	
    
    public class NextPageAsync extends AsyncTask<Void, Void, ArrayList<NormalBook>> {
    	
		@Override
		protected ArrayList<NormalBook> doInBackground(Void... params) {
			Thread.currentThread().setName("NextPageAsync...");
			int size = resultList.size();
			test.getNextPage("briefcitImageBox", " / ", " ; ", 11000);
			ArrayList<NormalBook> list = (ArrayList<NormalBook>) test.getBookList();	
			ArrayList<NormalBook> newList = new ArrayList<NormalBook>();
	        for(int i = size ; i < list.size(); i++) {
		       BookDetailParser bdp = new BookDetailParser(list.get(i));	        
		       bdp.getHTML("<!-- field C -->","<!--", "<!-- field % -->", "</td>", "bibInfoData", "</td>", "<!-- field 1 -->", "</td>");
		       newList.add(bdp.getDetail());
	        }
	        return newList;
		} 
		
		@Override
		protected void onPostExecute(ArrayList<NormalBook> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			resultList.addAll(result);
			adapter.notifyDataSetChanged();
		}
    }
    
    public class SearchAsync extends AsyncTask<String, Void, ArrayList<NormalBook>> {
    	
    	@Override
    	protected void onPreExecute() {
    		// TODO Auto-generated method stub
    		super.onPreExecute();
    		pDialog.show();
    	}
    	
		@Override
		protected ArrayList<NormalBook> doInBackground(String... arg0) {
			Thread.currentThread().setName("SearchAsync...");
			// for testing, use Java---test.setKeyWords("Java");
			String query = "";
			for(int i = 0; i < arg0.length; i++) {
				query += arg0[i]+" ";
			}
			test.setKeyWords(query);
	        test.getHTML("briefcitImageBox", " / ", " ; ", 11000);
	        List<NormalBook> con= test.getBookList();
	        for(int i = 0; i < con.size(); i++) {
		       BookDetailParser bdp = new BookDetailParser(con.get(i));	        
		       bdp.getHTML("<!-- field C -->","<!--", "<!-- field % -->", "</td>", "bibInfoData", "</td>", "<!-- field 1 -->", "</td>");
		       con.set(i,bdp.getDetail());
	        }
	        return (ArrayList<NormalBook>) con;
		}
		@Override
		protected void onPostExecute(ArrayList<NormalBook> list) {		
			pDialog.dismiss();
			System.out.println("size is"+list.size());
			if(list.size() == 0) {
				Toast.makeText(SearchActivity.this,"sorry, no result",Toast.LENGTH_LONG).show();
			}
			resultList = list;
			adapter = new SearchResultAdapter(SearchActivity.this,resultList);
        	listView.setAdapter(adapter);
			loaded = true;
		}
	}
 
    public class GetBookImgAsync extends AsyncTask<ImageViewBookImg, Void, ImageViewBookImg> {
		@Override
		protected ImageViewBookImg doInBackground(ImageViewBookImg... params) {
			Thread.currentThread().setName("GetBookImg...");
			ImageViewBookImg obj = params[0];
			NormalBook book = obj.getNormalBook();
			URL url = book.getimgURL();
			Bitmap img = null;
			try {
				img = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Not a ioe");
				System.out.println("The resource is not a book, maybe an electronic information...");
				e.printStackTrace();
			} finally {
				book.setBitmap(img);
			}
			System.gc();
			return obj;
		}    	
		@Override
		protected void onPostExecute(ImageViewBookImg obj) {
			ImageView view = obj.getImageView();
			NormalBook book = obj.getNormalBook();
			Bitmap img = book.getBitmap();
			if(img != null) {
				view.setImageBitmap(img);
			}
			System.gc();
		}
    }
    
    public class SearchResultAdapter extends BaseAdapter {
		private Context context;
        private ArrayList<NormalBook> list;
        public SearchResultAdapter(Context context, ArrayList<NormalBook> bookList){
            this.context = context;
            this.list = bookList;
        }
        @SuppressLint({ "ViewHolder", "InflateParams" })
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.book_new_collection, null);
//            }            
           final NormalBook c = list.get(position); 
           ImageView cover = (ImageView) convertView.findViewById(R.id.book_cover);
           TextView title = (TextView) convertView.findViewById(R.id.book_title);
           TextView author = (TextView) convertView.findViewById(R.id.book_author);
           TextView status = (TextView) convertView.findViewById(R.id.book_status);
           ImageButton map = (ImageButton) convertView.findViewById(R.id.book_map);
           map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// show the map
				Toast.makeText(context,"map...",Toast.LENGTH_LONG).show();
				Intent intent = new Intent(context, DisplayMessageActivity.class);
				intent.putExtra(MainActivity.EXTRA_MESSAGE1, "book");
		    	intent.putExtra(MainActivity.EXTRA_MESSAGE2, c.getBookNumber());
		    	startActivity(intent);

			}        	   
           });
           if(c.getBitmap() == null) {
	           cover.setImageResource(R.drawable.empty_book);
	           new GetBookImgAsync().execute(new ImageViewBookImg(cover, c));
           } else {
        	   cover.setImageBitmap(c.getBitmap());
           }
           System.gc();
           String s1 = c.getAuthor();
           if(s1.length() > 25) {
        	   s1 = s1.substring(0,20)+"...";
           }
           author.setText(s1);
           String s2 = c.getTitle();
           if(s2.length() > 50) {
        	   s2 = s2.substring(0,35)+"...";
           }
           title.setText(s2);
           String s3 = c.getStatus();
           s3.trim();
           status.setText(s3);
           if(c.getStatusID() == 1) {
               status.setTextColor(0xff00ff00);
           } else {
        	   status.setTextColor(0xffff0000);
           }
           System.out.println("IN000");
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
