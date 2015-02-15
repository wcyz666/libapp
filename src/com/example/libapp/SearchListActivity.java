package com.example.libapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class SearchListActivity extends Activity  {
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	
	//private static final String TAG = "Choose";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_list);
		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.slExp);
		expListView.setOnChildClickListener(new OnChildClickListener() {
		    @Override
		    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		    	Intent intent = new Intent(SearchListActivity.this, DisplayMessageActivity.class);
		    	TextView text = (TextView) v.findViewById(R.id.lblListItem);
		    	String s = (String) text.getText();
		    	intent.putExtra(MainActivity.EXTRA_MESSAGE1, "item");
		    	intent.putExtra(MainActivity.EXTRA_MESSAGE2, s);
		    	startActivity(intent);
		        return true;
		    }
		});
// preparing list data
		prepareListData();
		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
		// setting list adapter
		expListView.setAdapter(listAdapter);
	}
	
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		// Adding child data
		facilityList fclist = new facilityList();
		for(int i=0;i<fclist.number;i++)
		{
			listDataHeader.add(fclist.name[i]);
		}
		// Adding child data
		List<String> book = new ArrayList<String>();
		bookList bklist = new bookList();
		for (int i=0;i<bklist.number;i++)
		{
			book.add(bklist.name[i]);
		}
		
		List<String> bubble = new ArrayList<String>();
        for(int i=0;i<19;i++)
        {
        	int num = i+1;
        	bubble.add("Bubble Group Study Room "+num);
        }
		
						
		List<String> counter = new ArrayList<String>();
		counter.add("Circulation Counter");
		
		List<String> computer = new ArrayList<String>();
		for (int i=0;i<6;i++)
		{
			String str= "Computers";
			if(i==0){
				computer.add(str+"(GF)");
			}
			else{
				if(i==5)
				{
					computer.add(str+"(LG)");
				}
				else{
					computer.add(str+"("+i+"F)");
				}
			}
		}
		
		List<String> doctoral = new ArrayList<String>();
		doctoral.add("Doctoral Study Room");
		
		List<String> exhibition = new ArrayList<String>();
		exhibition.add("Exhibition Area");
		
		List<String> faculty = new ArrayList<String>();
		faculty.add("Faculty Study Room(3F)");
		faculty.add("Faculty Study Room(4F)");		
		
		List<String> gentleman = new ArrayList<String>();
		for (int i=0;i<6;i++)
		{
			String str= "Gentlemen's Room";
			if(i==0){
				gentleman.add(str+"(GF)");
			}
			else{
				if(i==5)
				{
					gentleman.add(str+"(LG)");
				}
				else{
					gentleman.add(str+"("+i+"F)");
				}
			}
		}

		List<String> study = new ArrayList<String>();
        for(int i=0;i<19;i++)
        {
        	int num = i+1;
        	study.add("Group Study Room "+num);
        }

		List<String> lady = new ArrayList<String>();
		for (int i=0;i<6;i++)
		{
			String str= "Ladies' Room";
			if(i==0){
				lady.add(str+"(GF)");
			}
			else{
				if(i==5)
				{
					lady.add(str+"(LG)");
				}
				else{
					lady.add(str+"("+i+"F)");
				}
			}
		}        
		
		List<String> lift = new ArrayList<String>();
		for (int i=0;i<6;i++)
		{
			String str= "Lift";
			if(i==0){
				lift.add(str+"(GF)");
			}
			else{
				if(i==5)
				{
					lift.add(str+"(LG)");
				}
				else{
					lift.add(str+"("+i+"F)");
				}
			}
		} 


		List<String> multi = new ArrayList<String>();
		multi.add("Multipurpose Room 1");
		multi.add("Multipurpose Room 2");


		List<String> copy = new ArrayList<String>();
		for (int i=0;i<6;i++)
		{
			String str= "Photocopier";
			if(i==0){
				copy.add(str+"(GF)");
			}
			else{
				if(i==5)
				{
					copy.add(str+"(LG)");
				}
				else{
					copy.add(str+"("+i+"F)");
				}
			}
		} 	

		List<String> printer = new ArrayList<String>();
		for (int i=0;i<6;i++)
		{
			String str= "Printers";
			if(i==0){
				printer.add(str+"(GF)");
			}
			else{
				if(i==5)
				{
					printer.add(str+"(LG)");
				}
				else{
					printer.add(str+"("+i+"F)");
				}
			}
		} 

		List<String> research = new ArrayList<String>();
		research.add("Research Consultation Room");

		List<String> checkout = new ArrayList<String>();
		checkout.add("Self-Checkout Machine");

		List<String> stair = new ArrayList<String>();
		for (int i=0;i<6;i++)
		{
			String str= "Staircase";
			if(i==0){
				stair.add(str+"(GF)");
			}
			else{
				if(i==5)
				{
					stair.add(str+"(LG)");
				}
				else{
					stair.add(str+"("+i+"F)");
				}
			}
		}
		listDataChild.put(listDataHeader.get(0), book); 
		listDataChild.put(listDataHeader.get(1), bubble);
		listDataChild.put(listDataHeader.get(2), counter);
		listDataChild.put(listDataHeader.get(3), computer);
		listDataChild.put(listDataHeader.get(4), doctoral);
		listDataChild.put(listDataHeader.get(5), exhibition);
		listDataChild.put(listDataHeader.get(6), faculty);
		listDataChild.put(listDataHeader.get(7), gentleman);
		listDataChild.put(listDataHeader.get(8), study);
		listDataChild.put(listDataHeader.get(9), lady);
		listDataChild.put(listDataHeader.get(10),lift);
		listDataChild.put(listDataHeader.get(11), multi);
		listDataChild.put(listDataHeader.get(12), copy);
		listDataChild.put(listDataHeader.get(13), printer);
		listDataChild.put(listDataHeader.get(14), research);
		listDataChild.put(listDataHeader.get(15), checkout);
		listDataChild.put(listDataHeader.get(16), stair);
	}
	
}