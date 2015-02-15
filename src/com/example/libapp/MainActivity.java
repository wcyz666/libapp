package com.example.libapp;


import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


@SuppressLint("HandlerLeak")
public class MainActivity extends ActionBarActivity{
	public final static String EXTRA_MESSAGE1="com.example.LibApp.MESSAGE1";
	public final static String EXTRA_MESSAGE2="com.example.LibApp.MESSAGE2";
	private int[] HOMEpressed = {R.id.home,R.drawable.ic_home};
	private HomeFragment home;
	private SearchUserFragment search;
	private MapFragment map;
	private MoreFragment more;
	private ArrayList<CKBook> list;
	private EmptyFragment emptyFragment;
	View view;
	ImageView searchLongClick;
	public static Handler mainHandler;

	public void setRecordList(ArrayList<CKBook> list) {
		this.list = list;
	}
	public ArrayList<CKBook> getRecordList() {
		return list;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.actionbar_menu, menu);
	    MenuItem searchItem = menu.findItem(R.id.action_search);
	    SearchView view = (SearchView) MenuItemCompat.getActionView(searchItem);
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    view.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    view.setIconifiedByDefault(false); 
	    view.setQuery("",true);
	    view.setFocusable(true);
		setTitle("Home");
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
        case R.id.action_search:		
        	break;
        case R.id.action_option:
    		replaceFragment(more);
			setTitle("More");
            return true;
        default:
            return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);

		super.onCreate(savedInstanceState);
		
		mainHandler = new Handler(){
			
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				Log.i("wcyz666", "aaaa");
        		switch (msg.what) 
        		{
					case 1:
						if (MainActivity.this.getActionBar().isShowing() == false)
							MainActivity.this.getActionBar().show();
						break;
					case 2:
						break;
					default: 
						break;
        		}
			}
		};
		
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		LayoutInflater inflater = (LayoutInflater)   
		this.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		view = inflater.inflate(R.layout.activity_main, null);
		setContentView(view);
//SHOW fragments=================initial stage======================
		
		home = new HomeFragment();
		search = new SearchUserFragment();
		map = new MapFragment();
		more = new MoreFragment();

		
		home.setRetainInstance(true);
		search.setRetainInstance(true);
		map.setRetainInstance(true);
		more.setRetainInstance(true);
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.fragment_container, home);
		fragmentTransaction.commit();
				

//====================================================================
	}
	
//=======Replace Fragment
	private void replaceFragment (Fragment fragment){
		FragmentManager manager = getFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(R.id.fragment_container, fragment);
		ft.commit();
	}
	

// method for image pressed===================================================
	public void imagePressed(View view) {  
		int id = view.getId();
		ImageView img = null;
		if(id != HOMEpressed[0]) {
			switch(id) {
				case R.id.home: {
					ImageView imgX = (ImageView) findViewById(HOMEpressed[0]);
					imgX.setImageResource(HOMEpressed[1]);
					img = (ImageView) findViewById(id);
					img.setImageResource(R.drawable.ic_home_pressed);
					HOMEpressed[0] = id;
					HOMEpressed[1] = R.drawable.ic_home;
					setTitle("Home");
					replaceFragment(home);
				}break;
				case R.id.search: {					
					ImageView imgX = (ImageView) findViewById(HOMEpressed[0]);
					imgX.setImageResource(HOMEpressed[1]);
					img = (ImageView) findViewById(id);
					img.setImageResource(R.drawable.ic_search_pressed);
					HOMEpressed[0] = id;
					HOMEpressed[1] = R.drawable.ic_search;
					setTitle("Search");
					replaceFragment(search);
				}break;
				case R.id.map: {
					ImageView imgX = (ImageView) findViewById(HOMEpressed[0]);
					imgX.setImageResource(HOMEpressed[1]);				
					img = (ImageView) findViewById(id);
					setTitle("Map");
					img.setImageResource(R.drawable.ic_map_pressed);
					HOMEpressed[0] = id;
					HOMEpressed[1] = R.drawable.ic_map;
					replaceFragment(map);

				}break;
				case R.id.more: {
					ImageView imgX = (ImageView) findViewById(HOMEpressed[0]);
					imgX.setImageResource(HOMEpressed[1]);				
					img = (ImageView) findViewById(id);
					img.setImageResource(R.drawable.ic_more_pressed);
					setTitle("More");
					HOMEpressed[0] = id;
					HOMEpressed[1] = R.drawable.ic_more;
					replaceFragment(more);
				}break;
			}
		}
	}  
//============================================================================
//============================================================================	
}
