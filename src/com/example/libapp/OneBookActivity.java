package com.example.libapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OneBookActivity extends Activity {
	private View view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LayoutInflater mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.activity_one_book, null);
//==================== GET the URL of THIS book=======================       
        Intent intent= getIntent();
        Bundle b = intent.getExtras();
		Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.java1); 
        if(b!=null) {
        	bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        }
        String s1 = null;
//==================== Use the URL to request HTTP and parse==========
        ImageView img = (ImageView) view.findViewById(R.id.book_detail_cover);
        Button button = (Button) view.findViewById(R.id.see_reviews);
        TextView title= (TextView) view.findViewById(R.id.book_detail_title);
        TextView status = (TextView) view.findViewById(R.id.book_detail_status);
        TextView author = (TextView) view.findViewById(R.id.book_detail_author);
        TextView edition = (TextView) view.findViewById(R.id.book_detail_edition);
        TextView press = (TextView) view.findViewById(R.id.book_detail_press);
        TextView location = (TextView) view.findViewById(R.id.book_detail_location);
        img.setImageBitmap(bitmap);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//Intent intent = new Intent(OneBookActivity.this, Review.class);
				// send review url & other info.
				//startActivity(intent);
			}        	
        });
        if((s1 = b.getString("BookTitle")) != null) {
            title.setText(s1);        	
        } else {
        	title.setText("sorry, we can't get title");
        }
        if((s1 = b.getString("BookStatus")) != null) {
            status.setText(s1);
        } else {
        	title.setText("sorry, we can't get status");
        }
        if((s1 = b.getString("BookAuthor")) != null) {
            author.setText(s1);
        } else {
        	author.setText("sorry, we can't get author");
        }
        if((s1 = b.getString("BookEdition")) != null) {
            edition.setText(s1);
        } else {
        	edition.setText("sorry, we can't get edition");
        }
        if((s1 = b.getString("BookPress")) != null) {
            press.setText(s1);
        } else {
        	press.setText("sorry, we can't get press");
        }
        if((s1 = b.getString("BookLocation")) != null) {
            location.setText(s1);
        } else {
        	location.setText("sorry, we can't get location");
        }


//====================================================================      
		setContentView(view);
	}


}
