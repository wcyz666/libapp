package com.example.libapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

public class BookRoomDialog extends DialogFragment {
	private ArrayList<String> bookList = new ArrayList<String>();
	private ArrayList<Detail> list = new ArrayList<Detail>();
	private String[] list2;
	public static final String PREFS_NAME = "LoginPrefs";
	private int position;
	private OnCancelSelectedListener cl;
	private ProgressDialog pDialog;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		Bundle b = getArguments();
		position = b.getInt("cancel");
		
		pDialog = new ProgressDialog(getActivity());
	    pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setTitle("Cancelling");
	    pDialog.setMessage("Cancelling Booking Records");		

	    builder.setPositiveButton(R.string.cancel_button_yes, new DialogInterface.OnClickListener() {
	    	@Override
		    public void onClick(DialogInterface dialog, int id) {
	    		// User clicked OK, so save the mSelectedItems results somewhere
		        // or return them to the component that opened the dialog
	    		BookRoomDialog.this.cancel();
	    		pDialog.show();
	    	}
	    })
	    .setNegativeButton(R.string.cancel_button_no, new DialogInterface.OnClickListener() {
	    	@Override
	    	public void onClick(DialogInterface dialog, int id) {
	    		dialog.dismiss();
	    	}
	    });
	    builder.setTitle("Cancel booking ?").setItems(list2, null);
	    return builder.create();
	}
	public class CancelRoomAsync extends AsyncTask<Integer, Void, Void> {
		@Override
		protected Void doInBackground(Integer... params) {
			// cancel booking
			SharedPreferences settings = BookRoomDialog.this.getActivity().getSharedPreferences(PREFS_NAME, 0);
		    String mSID = settings.getString("SID", null);
		    String mPassword = settings.getString("pw", null);
	        RoomViewer test = new RoomViewer();
	        test.setUsernameAndPassword(mSID, mPassword);
	        test.viewingExec();
	        List<RoomRecord> list = test.getRecord();
	        list.get(params[0]).cancel();
	        return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
		}
	}
	
	public interface OnCancelSelectedListener {
		public void onCancelSelected();
	}
	
	private void cancel() {
		new CancelRoomAsync().execute(position);
		cl.onCancelSelected();
	}
		
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			cl = (OnCancelSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnCancelSelectedListener");
		}
	}
}
