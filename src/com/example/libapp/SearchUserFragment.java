package com.example.libapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class SearchUserFragment extends Fragment {
	private SearchView view;
	private Spinner spinner;
	private LinearLayout layout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		SearchManager searchManager = (SearchManager) this.getActivity().getSystemService(Context.SEARCH_SERVICE);
		layout = (LinearLayout) inflater.inflate(R.layout.activity_search_user, null);
		view = (SearchView) layout.findViewById(R.id.search_view);
	    view.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
	    view.setIconifiedByDefault(false); 
	    view.setQuery("",true);
	    view.setFocusable(true);
	    view.setQueryHint("Book Name");
	    
	    spinner = (Spinner) layout.findViewById(R.id.spinner);
	    final List<String> strings = new ArrayList<>();
	    strings.add("Books");
	    strings.add("Papers");
	       
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, strings);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	   
	    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String sInfo= arg0.getItemAtPosition(arg2).toString();  
	            if (sInfo.equalsIgnoreCase("papers")){
	            	view.setQueryHint("Course Code or Title");
	            }
	            else
	            	view.setQueryHint("Book Name");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		
	    });
		return layout;
	}
}
