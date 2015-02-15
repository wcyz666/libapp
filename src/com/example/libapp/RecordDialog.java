package com.example.libapp;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class RecordDialog extends DialogFragment{
	private ArrayList mSelectedItems;
	private ArrayList<CKBook> list;
	private String[] list2;
	public static final String PREFS_NAME = "LoginPrefs";
	private ArrayList<CKBook> renewList;
	private Record r;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    mSelectedItems = new ArrayList();  // Where we track the selected items
	    renewList = new ArrayList<CKBook>();
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		try {
			list = new RecordListAsync().execute().get();
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
	    	list2[i] = list.get(i).name;
	    }
	    // Set the dialog title
	    // Specify the list array, the items to be selected by default (null for none),
	    // and the listener through which to receive callbacks when items are selected
	    // Set the action buttons
	    System.out.println("reached...dialog...");
	    builder.setTitle(R.string.library_record).setMultiChoiceItems(list2, null, new DialogInterface.OnMultiChoiceClickListener() {
	    	@Override
	        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
	    		if (isChecked) {
	    			// If the user checked the item, add it to the selected items
	    			mSelectedItems.add(which);
	    			renewList.add(list.get(which));
	    		} else if (mSelectedItems.contains(which)) {
	    			// Else, if the item is already in the array, remove it 
	    			mSelectedItems.remove(Integer.valueOf(which));
	    			renewList.remove(Integer.valueOf(which));
	    		}
	    	}
	    }) 
	    .setPositiveButton(R.string.record_renew, new DialogInterface.OnClickListener() {
	    	@SuppressWarnings("unchecked")
			@Override
		    public void onClick(DialogInterface dialog, int id) {
	    		// User clicked OK, so save the mSelectedItems results somewhere
		        // or return them to the component that opened the dialog
	    		if(renewList.size() == 0) {
	    			Toast.makeText(RecordDialog.this.getActivity(), "no book selected", Toast.LENGTH_LONG).show();
	    			return;
	    		}
	    		boolean result = false;
				try {
					result = new RenewListAsync().execute(renewList).get();
					System.out.println(".....renewed......");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		if(result == true) {
	    			Toast.makeText(RecordDialog.this.getActivity(), "renew successfully", Toast.LENGTH_LONG).show();
	    		} else {
	    			Toast.makeText(RecordDialog.this.getActivity(), "sorry, error occurs", Toast.LENGTH_LONG).show();	    			
	    		}
	    	}
	    })
	    .setNegativeButton(R.string.record_cancel, new DialogInterface.OnClickListener() {
	    	@Override
	    	public void onClick(DialogInterface dialog, int id) {
	    		dialog.dismiss();
	    	}
	    });
	    return builder.create();
	}
	
	public class RecordListAsync extends AsyncTask<Void, Void, ArrayList<CKBook>> {
		@Override
		protected ArrayList<CKBook> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			r = new Record();
			SharedPreferences settings = RecordDialog.this.getActivity().getSharedPreferences(PREFS_NAME, 0);
		    String mSID = settings.getString("SID", null);
		    String mPassword = settings.getString("pw", null);
		    System.out.println(mSID+"..."+mPassword);
	        r.Log(mSID,mPassword);		
	        ArrayList<CKBook> list = r.getBooks();
	        System.out.println("list:==="+list);
			return list;
		}		
	}
	
	public class RenewListAsync extends AsyncTask<ArrayList<CKBook>, Void, Boolean> {
		@Override
		protected Boolean doInBackground(ArrayList<CKBook>... params) {
			// TODO Auto-generated method stub
			ArrayList<CKBook> list = params[0];	
			for(CKBook c : list) {
				System.out.println(c);
			}
			boolean result = r.RenewSome(list);
			System.out.println("..........RESULT....."+result);
			r.getItems();
			return result;
		}		
	}

}
