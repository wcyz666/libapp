package com.example.libapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

public class PickStartTimeDialog extends DialogFragment {
	private TimePicker picker;
	private int year;
	private int month;
	private int day;
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle b = getArguments();
		year = b.getInt("year");
		month = b.getInt("month");
		day = b.getInt("day");

		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.book_room_time_picker, null);
        picker= (TimePicker) view.findViewById(R.id.time_picker);
    	setDisabledTextViews(picker);
  	
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setMessage(R.string.book_room_start_time)
           .setPositiveButton(R.string.book_room_dialog_yes, new DialogInterface.OnClickListener() {
               @Override
			public void onClick(DialogInterface dialog, int id) {
                   // FIRE ZE MISSILES!
            	   int startHour = picker.getCurrentHour();
                   int startMinute = picker.getCurrentMinute() < 30 ? 0 : 30;
                   System.out.println("time:"+startHour+":::"+startMinute);
                   DialogFragment newFragment = new PickEndTimeDialog();
                   Bundle args = new Bundle();
                   args.putInt("year", year);
                   args.putInt("month", month);
                   args.putInt("day", day);
                   args.putInt("startHour", startHour);
                   args.putInt("startMinute", startMinute);
                   newFragment.setArguments(args);
                   newFragment.show(getFragmentManager(), "book room end");
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