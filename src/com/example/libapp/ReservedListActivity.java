package com.example.libapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class ReservedListActivity extends Activity {
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reserved_list);
		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		expListView.setOnChildClickListener(new OnChildClickListener() {
		    @Override
		    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		    	TextView text = (TextView) v.findViewById(R.id.lblListItem);
		    	String s = (String) text.getText();
		    	System.out.println("ASDFASFASDGASDg======"+s);
		    	
				DialogFragment newFragment = new ReservedListDialog();
		        Bundle args = new Bundle();
		        args.putString("course", s);
		        newFragment.setArguments(args);		 
			    newFragment.show(getFragmentManager(), "reserved");
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
		listDataHeader.add("FAAS");
		listDataHeader.add("UGEA");
		listDataHeader.add("UGEB");
		
		// Adding child data
		List<String> faas = new ArrayList<String>();
		faas.add("FAAS 1600");
		faas.add("FAAS 3105");
		faas.add("FAAS 3111");
		faas.add("FAAS 5302");
		
		List<String> ugea = new ArrayList<String>();
		ugea.add("UGEA 2100");
		ugea.add("UGEA 2150");
		ugea.add("UGEA 2180");
		ugea.add("UGEA 2190");
		ugea.add("UGEA 2200");
		ugea.add("UGEA 2220");
		ugea.add("UGEA 2234");
		ugea.add("UGEA 2320");	
						
		List<String> ugeb = new ArrayList<String>();
		ugeb.add("UGEB 1570");
		ugeb.add("UGEB 2222");
		ugeb.add("UGEB 2262");
		ugeb.add("UGEB 2341");
		ugeb.add("UGEB 2350");
		ugeb.add("UGEB 2361");
		ugeb.add("UGEB 2362");
		ugeb.add("UGEB 2401");
		ugeb.add("UGEB 2440");
		ugeb.add("UGEB 2491");
		ugeb.add("UGEB 2502");
		ugeb.add("UGEB 2510");
		ugeb.add("UGEB 2650");
		ugeb.add("UGEB 2721");
		ugeb.add("UGEB 2806");
		
		
		listDataChild.put(listDataHeader.get(0), faas); 
		listDataChild.put(listDataHeader.get(1), ugea);
		listDataChild.put(listDataHeader.get(2), ugeb);
	}
	
}