package com.example.libapp;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;

public class ReservedListDialog extends DialogFragment {
	private ArrayList<String> bookList = new ArrayList<String>();
	private ArrayList<Detail> list = new ArrayList<Detail>();
	private String[] list2;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    String course = getArguments().getString("course");
	System.out.println("title==="+course);
	    try {
			list = new ReservedListAsync().execute(course).get();
			System.out.println("YES");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    list2 = new String[list.size()];
	    for(int i = 0; i < list.size(); i++) {
	    	list2[i] = list.get(i).Title+" "+list.get(i).Info;
	    }
	    
	    builder.setTitle(course).setItems(list2, null);
	    return builder.create();
	}
	public class ReservedListAsync extends AsyncTask<String, Void, ArrayList<Detail>> {
		@Override
		protected ArrayList<Detail> doInBackground(String... params) {
	        CourseReserved test = new CourseReserved();
	        String[] arr = params[0].split(" ");
//	        String s = arr[0].toLowerCase()+" "+arr[1];
//	        test.GetCourses(s);
//			ugea2100 A + ugea2100 B
	        test.GetDetails("/search/r?SEARCH="+arr[0].toLowerCase()+"+"+arr[1]);
			return test.Details;
		}		
	}
}
