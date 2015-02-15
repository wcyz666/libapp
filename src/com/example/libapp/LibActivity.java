package com.example.libapp;

import java.net.URL;
import java.text.DateFormat;
import java.util.Date;




public class LibActivity {

	    private String eventTitle;
	    private URL contentURL;
	    private Date date;
	    private String datestring;
	    
	    public LibActivity()
	    {
	        eventTitle = new String("");
	    }
	    
	    public void setTitle(String s)
	    {
	        eventTitle = s;
	    }
	    public String getTitle()
	    {
	        return eventTitle;
	    }
	    public void setURL(String url)
	    {
	        try
	        {  
	            contentURL = new URL(url);
	        }
	        catch (Exception e){}
	    }
	    public void setDate(String s)
	    {
	        try
	        {
	            datestring = new String(s);
	            date = DateFormat.getDateInstance().parse(s);
	        }
	        catch (Exception e)           
	        {
	            date = new Date();
	        }
	    }
	    public Date getDate()
	    {
	        return this.date;
	    }
	    public String getDateString()
	    {
	        return this.datestring;
	    }
	    public URL getURL()
	    {
	        return contentURL;
	    }
	    public String getURLString()
	    {
	        return contentURL.toString();
	    }
	}

