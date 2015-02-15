package com.example.libapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MoreButton extends Button {
    
    
    private int resourceId = 0;
    private Bitmap bitmap;
    
    public MoreButton(Context context) {
        super(context,null);
    }
    
    public MoreButton(Context context,AttributeSet attributeSet, int resource) {
        super(context, attributeSet);
        this.setClickable(true);
        resourceId = resource;
        bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
		this.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					MoreButton.this.setBackgroundColor(Color.GRAY);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL){
					MoreButton.this.setBackgroundColor(Color.WHITE);
				}
				return false;
			}
		});
    }
    
    public void setIcon(int resourceId) 
    {
        this.bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        invalidate();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub

        canvas.drawBitmap(bitmap, 20, 20, null);
        canvas.translate(10,0);
        super.onDraw(canvas);
    }
    
    
}