package com.example.libapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

public class PickDateDialog extends DialogFragment {
	private DatePicker picker;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater  inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.book_room_date_picker, null);
        picker= (DatePicker) view.findViewById(R.id.date_picker);
    	setDisabledTextViews(picker);
    	
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setMessage(R.string.book_room_date)
           .setPositiveButton(R.string.book_room_dialog_yes, new DialogInterface.OnClickListener() {
               @Override
			public void onClick(DialogInterface dialog, int id) {
                   // FIRE ZE MISSILES!
            	   int day  = picker.getDayOfMonth();
                   int month= picker.getMonth() + 1;
                   int year = picker.getYear();
                   System.out.println("IN pick date");
                   DialogFragment newFragment = new PickStartTimeDialog();
                   Bundle args = new Bundle();
                   args.putInt("year", year);
                   args.putInt("month", month);
                   args.putInt("day", day);
                   newFragment.setArguments(args);
                   newFragment.show(getFragmentManager(), "book room start");
            	   dismiss();
               }
           })
           .setNegativeButton(R.string.book_room_dialog_no, new DialogInterface.OnClickListener() {
               @Override
			public void onClick(DialogInterface dialog, int id) {
                   // User cancelled the dialog
            	  dialog.dismiss();
               }
           });
    // Create the AlertDialog object and return it
    return builder.create();	
    }
	

    private void setDisabledTextViews(ViewGroup dp) 
    {
        for (int x = 0, n = dp.getChildCount(); x < n; x++) 
        {
            View v = dp.getChildAt(x);

            if (v instanceof TextView) 
            {
                v.setEnabled(false);
            } 
            else if (v instanceof ViewGroup) 
            {
                 setDisabledTextViews((ViewGroup)v);
            }
        }
    }
    
}