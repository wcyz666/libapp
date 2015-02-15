package com.example.libapp;


import android.R.anim;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity
{
	/** Called when the activity is first created. */
	private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f,MAX_ZOOM = 1f;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    private LinearLayout layout;
    private TextView text;
    
    /** Called when the activity is first created. */
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.display_message, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    public void openSearch()
    {
    	Intent intent = new Intent(this, SearchListActivity.class);
    	//intent.putExtra(EXTRA_MESSAGE, "");
    	startActivity(intent);
    }
    
    // add search item in action bar:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
            {
            	openSearch();
                return true;
            }
	        case R.id.action_settings:
	        {
	            return true;
	        }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	Intent intent = getIntent();
    	String message1 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
    	String message2 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = (LinearLayout) inflater.inflate(R.layout.activity_display_message, null);
        text = (TextView) layout.findViewById(R.id.map_location_text);
        setContentView(layout);
        ZoomableImageView view = (ZoomableImageView) findViewById(R.id.imageView2);
        //Bitmap image= BitmapFactory.decodeResource(this.getBaseContext().getResources(), R.drawable.secfloor);
    //get the location index:

        if(message2.compareTo("")==0)
        {
        	Bitmap bmp;
        	bmp = BitmapFactory.decodeResource(getResources(),
        	                R.drawable.grofloor).copy(Bitmap.Config.ARGB_8888, true);

	        Bitmap newBitmap = null;

	        newBitmap = Bitmap.createBitmap(bmp);
	        Canvas canvas = new Canvas(newBitmap);
	        Paint paint = new Paint();

	        paint.setColor(Color.WHITE);
	        paint.setAlpha(125);
	        canvas.drawRect(0, 0, bmp.getWidth(), bmp.getHeight(), paint);				

	        canvas.save(Canvas.ALL_SAVE_FLAG);
	        canvas.restore();
	        view.setImageBitmap(newBitmap);
        }
        else
        {
		    if(message1.compareTo("book")==0)
		    {
		        String second;
		        if(message2.charAt(1)>64)
		        {
		        	second =""+ message2.charAt(1);
		        }else
		        {
		        	second = ""; 
		        }
		        String index = ""+message2.charAt(0)+second;
		        
		        //find the physical location:
		        Allbook book = new Allbook();
		        book.setData();
		        int count=0;
		        double[] lx = new double[4];
		        double[] ly = new double[4];
		        int floor=1;
		        for (int i=0; i<book.booknum;i++)
		        {
		        	if(index.compareTo(book.bc[i].start)>=0 && index.compareTo(book.bc[i].end)<=0)
		        	{
		        		lx[count] = book.bc[i].x;
		        		ly[count] = book.bc[i].y;
		        		floor = book.bc[i].floor;
		        		count++;
		        	}
		        }
		        Bitmap bmp;
		        Log.d(TAG, ""+index);

		// Choose the floor image to display:        
		        switch(floor)
		        {
		        	case 0:
		        	{
		        		bmp = BitmapFactory.decodeResource(getResources(),
		        		                R.drawable.grofloor).copy(Bitmap.Config.ARGB_8888, true);
	    	    		text.setText("University Library\n"+"Ground Floor\n"+message2);
		        		break;
		        	}
		        	case 1:
		        	{
		        		bmp = BitmapFactory.decodeResource(getResources(),
		        		                R.drawable.firfloor).copy(Bitmap.Config.ARGB_8888, true);
	    	    		text.setText("University Library\n"+"1st Floor\n"+message2);

		        		break;
		        	}
		        	case 2:
		        	{
		        		bmp = BitmapFactory.decodeResource(getResources(),
		        		                R.drawable.secfloor).copy(Bitmap.Config.ARGB_8888, true);
	    	    		text.setText("University Library\n"+"2nd Floor\n"+message2);

		        		break;
		        	}
		        	case 3:
		        	{
		        		bmp = BitmapFactory.decodeResource(getResources(),
		        		                R.drawable.thifloor).copy(Bitmap.Config.ARGB_8888, true);
	    	    		text.setText("University Library\n"+"3rd Floor\n"+message2);

		        		break;
		        	} 
		        	case 4:
		        	{
		        		bmp = BitmapFactory.decodeResource(getResources(),
		        		                R.drawable.foufloor).copy(Bitmap.Config.ARGB_8888, true);
	    	    		text.setText("University Library\n"+"4th Floor\n"+message2);

		        		break;
		        	}
		        	case 5:
		        	{
		        		bmp = BitmapFactory.decodeResource(getResources(),
		        		                R.drawable.fivfloor).copy(Bitmap.Config.ARGB_8888, true);
		        		text.setText("University Library\n"+"Lower Ground Floor\n"+message2);
		        		break;
		        	}
		        	default:
		        		bmp = BitmapFactory.decodeResource(getResources(),
				                R.drawable.fivfloor).copy(Bitmap.Config.ARGB_8888, true);
		        		message2=message2 + ": No results found.";
		        		text.setText("University Library\n"+"Ground Floor\n"+message2);
		        		break;
		        		
		        }        
		        
		        // draw bitmap:
		        
				Log.d(TAG, "count"+ count);
		        Bitmap newBitmap = null;
		        newBitmap = Bitmap.createBitmap(bmp);
		        Canvas canvas = new Canvas(newBitmap);
		        Paint paint = new Paint();

		        double back_width = bmp.getWidth();
		        double back_height = bmp.getHeight();
		        
		        paint.setColor(Color.WHITE);
		        paint.setAlpha(125);
		        canvas.drawRect(0, 0, bmp.getWidth(), bmp.getHeight(), paint);

		        float Px=0, Py=0;
			
		        floorsize fs = new floorsize();
		        fs.setData();
		        
                Bitmap[] bitmapTag = new Bitmap[count];
                for(int i=0;i<count;i++)
                {
	          		bitmapTag[i] = ((BitmapDrawable) getResources().getDrawable(
	                 R.drawable.tag)).getBitmap();                
	                double tag_width = bitmapTag[i].getWidth();
	                double tag_height = bitmapTag[i].getHeight();	        		
	        		Px= (float)(lx[i]/fs.x[floor] * back_width - tag_width/2);
	        		Py= (float)(ly[i]/fs.y[floor] * back_height - tag_height);
	                paint = new Paint();
	                canvas.drawBitmap(bitmapTag[i],Px, Py, paint);
	            }
				
		        canvas.save(Canvas.ALL_SAVE_FLAG);
		        canvas.restore();
		        view.setImageBitmap(newBitmap);
		    }
		    else // item:
		    {
		    	Log.d(TAG, "message+"+ message2+"+");
	            itemList list = new itemList();
	    // Choose the floor image to display:  
	            list.setData();
	            int prefloor = -1;
				if(message1.compareTo("Ground Floor")==0)
					prefloor=0;
				if(message1.compareTo("First Floor")==0)
					prefloor=1;
				if(message1.compareTo("Second Floor")==0)
					prefloor=2;
				if(message1.compareTo("Third Floor")==0)
					prefloor=3;
				if(message1.compareTo("Fourth Floor")==0)
					prefloor=4;
				if(message1.compareTo("Fifth Floor")==0)
					prefloor=5;   
		        int count=0;
		        double[] lx = new double[10];
		        double[] ly = new double[10];
		        int floor =0;
	    		for (int i=0;i<list.number;i++)
	    		{	
	    			if(prefloor==-1)
		    		{
		    			if(message2.compareTo(list.item[i].name)==0)
		    			{
		    				lx[count] = list.item[i].x;
		    				ly[count] = list.item[i].y;
		    				count++;
		    				floor = list.item[i].floor;
		    			}
		    		}
		    		else
		    		{
		    			floor = prefloor;
		    			if(message2.compareTo(list.item[i].name)==0 && list.item[i].floor==prefloor)
		    			{
		    				lx[count] = list.item[i].x;
		    				ly[count] = list.item[i].y;
		    				count++;
		    			}
		    		}
	    		}
	    		
	    		Log.d(TAG, "message+"+ list.item[27].name+"+");
	    		Bitmap bmp;
	    		// only for university library	    		
	            switch(floor)
	            {
	            	case 0:
	            	{
	            		bmp = BitmapFactory.decodeResource(getResources(),
	            		                R.drawable.grofloor).copy(Bitmap.Config.ARGB_8888, true);
	    	    		text.setText("University Library\n"+"Ground Floor\n"+message2);
	            		break;
	            	}
	            	case 1:
	            	{
	            		bmp = BitmapFactory.decodeResource(getResources(),
	            		                R.drawable.firfloor).copy(Bitmap.Config.ARGB_8888, true);
	    	    		text.setText("University Library\n"+"1st Floor\n"+message2);

	            		break;
	            	}
	            	case 2:
	            	{
	            		bmp = BitmapFactory.decodeResource(getResources(),
	            		                R.drawable.secfloor).copy(Bitmap.Config.ARGB_8888, true);
	    	    		text.setText("University Library\n"+"2nd Floor\n"+message2);

	            		break;
	            	}
	            	case 3:
	            	{
	            		bmp = BitmapFactory.decodeResource(getResources(),
	            		                R.drawable.thifloor).copy(Bitmap.Config.ARGB_8888, true);
	    	    		text.setText("University Library\n"+"3rd Floor\n"+message2);

	            		break;
	            	} 
	            	case 4:
	            	{
	            		bmp = BitmapFactory.decodeResource(getResources(),
	            		                R.drawable.foufloor).copy(Bitmap.Config.ARGB_8888, true);
	    	    		text.setText("University Library\n"+"4th Floor\n"+message2);

	            		break;
	            	}
	            	case 5:
	            	{
	            		bmp = BitmapFactory.decodeResource(getResources(),
	            		                R.drawable.fivfloor).copy(Bitmap.Config.ARGB_8888, true);
	    	    		text.setText("University Library\n"+"Lower Ground Floor\n"+message2);

	            		break;
	            	}
	            	default:
	            		bmp = BitmapFactory.decodeResource(getResources(),
	    		                R.drawable.grofloor).copy(Bitmap.Config.ARGB_8888, true);
	    	    		text.setText("University Library\n"+"Ground Floor");
		        		message2=message2 + ": No results found.";
		        		text.setText("University Library\n"+"Ground Floor\n"+message2);

	            		break;
	            		
	            }        
	            
	            // draw bitmap:

	           	//bmp = BitmapFactory.decodeResource(getResources(), R.drawable.fivfloor).copy(Bitmap.Config.ARGB_8888, true);


				Log.d(TAG, "count"+ count);

	            Bitmap newBitmap = null;

	            newBitmap = Bitmap.createBitmap(bmp);
	            Canvas canvas = new Canvas(newBitmap);
	            Paint paint = new Paint();

	            double back_width = bmp.getWidth();
	            double back_height = bmp.getHeight();
	            paint.setColor(Color.WHITE);
	            paint.setAlpha(125);
	            canvas.drawRect(0, 0, bmp.getWidth(), bmp.getHeight(), paint);

                float Px=0, Py=0;
        	
                floorsize fs = new floorsize();
                fs.setData();

                Bitmap[] bitmapTag = new Bitmap[count];
                for(int i=0;i<count;i++)
                {
	          		bitmapTag[i] = ((BitmapDrawable) getResources().getDrawable(
	                 R.drawable.tag)).getBitmap();                
	                double tag_width = bitmapTag[i].getWidth();
	                double tag_height = bitmapTag[i].getHeight();	        		
	        		Px= (float)(lx[i]/fs.x[floor] * back_width - tag_width/2);
	        		Py= (float)(ly[i]/fs.y[floor] * back_height - tag_height);
	                paint = new Paint();
	                canvas.drawBitmap(bitmapTag[i],Px, Py, paint);
	            }


	     	// 	Bitmap bitmap2 = ((BitmapDrawable) getResources().getDrawable(
	      //        R.drawable.tag)).getBitmap();	            
	      //       tag_width = bitmap2.getWidth();
	      //       tag_height = bitmap2.getHeight();		    		
	    		// Px= (float)(lx[1]/fs.x[floor] * back_width - tag_width/2);
	    		// Py= (float)(ly[1]/fs.y[floor] * back_height - tag_height);	    		
	      //       paint = new Paint();
	      //       canvas.drawBitmap(bitmap2,Px, Py, paint);


	            canvas.save(Canvas.ALL_SAVE_FLAG);
	            canvas.restore();
	            view.setImageBitmap(newBitmap);
	        }
		}
	}
}