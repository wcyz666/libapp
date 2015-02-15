package com.example.libapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookPageGrabber {
    private URL url;
    private HttpURLConnection httpcon;
    
    public BookPageGrabber() {}
    public void setURL(URL url)
    {
        this.url = url;
    }
    public void setURL(String s)
    {
        try
        {
            this.url = new URL(s);
        }
        catch (Exception e){}
    }
    
    private void httpconnect()
    {
        try
        {
            httpcon = (HttpURLConnection)url.openConnection();
            httpcon.setDoInput(true);
            httpcon.setUseCaches(false);
            httpcon.setInstanceFollowRedirects(true);
            httpcon.connect(); 
        }
        catch (Exception e) { } 
    }
    public String grab()
    {

        String str = "";
        String line;
        try 
        {
            httpconnect();
            BufferedReader outStrm = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            while ((line = outStrm.readLine()) != null)
                str = str + line + "\n"; 
      
        }
        catch (Exception e) 
        { 
            System.out.println(e);
        }
        
        return str;
    }
}
