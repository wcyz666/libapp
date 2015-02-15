package com.example.libapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author lenovo
 */
public class securePageGrabber implements Serializable{
    private URL url;
    private HttpsURLConnection httpcon;
    String cookie;
    protected boolean flag;
    
    public securePageGrabber() 
    {
        flag = false;
        cookie = null;
    }
    public void setURL(URL url)
    {
        this.url = url;
    }
    public void setCookie(String c)
    {
        this.cookie = c;
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
            httpcon = (HttpsURLConnection)url.openConnection();
            httpcon.setRequestProperty("Connection", "keep-alive");
            httpcon.setRequestProperty("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4");
            httpcon.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
            httpcon.setRequestProperty("Referer", "https://rbs.lib.cuhk.edu.hk/Booking/Secure/FacilityStatusM.aspx");
            httpcon.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpcon.setRequestProperty("Cache-Control", "max-age=0");
            httpcon.setRequestProperty("Host","rbs.lib.cuhk.edu.hk");
            httpcon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
            httpcon.setDoInput(true);
            if (cookie != null)
                httpcon.setRequestProperty("Cookie",cookie);      
            httpcon.setUseCaches(false);
            httpcon.setInstanceFollowRedirects(false);
            httpcon.connect(); 
        }
        catch (Exception e) { } 
    }
    private void httpconnect(String data)
    {
        try
        {
            httpcon = (HttpsURLConnection)url.openConnection();
            httpcon.setDoInput(true);
            httpcon.setDoOutput(true);
            httpcon.setRequestMethod("POST");
            httpcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8" );
            httpcon.setRequestProperty("Content-Length", String.valueOf(data.length()));
            httpcon.setRequestProperty("Connection", "keep-alive");
            httpcon.setRequestProperty("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4");
            httpcon.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
            
            
            httpcon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
            if (null != cookie)
            {
                httpcon.setRequestProperty("Cookie",cookie);   
                httpcon.setRequestProperty("Referer", "https://rbs.lib.cuhk.edu.hk/Booking/Secure/FacilityStatusM.aspx");
                httpcon.setRequestProperty("Origin", "https://rbs.lib.cuhk.edu.hk");
                httpcon.setRequestProperty("Host","rbs.lib.cuhk.edu.hk");
            }
            else
            {
                httpcon.setRequestProperty("Referer", "https://rbs.lib.cuhk.edu.hk/Booking/LoginM.aspx");
                httpcon.setRequestProperty("Origin", "https://rbs.lib.cuhk.edu.hk");
                httpcon.setRequestProperty("Host","rbs.lib.cuhk.edu.hk");
            }
            if (flag == true)
            {
                httpcon.setRequestProperty("Accept","*/*");
                httpcon.setRequestProperty("X-MicrosoftAjax", "Delta=true");
                httpcon.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                httpcon.setRequestProperty("Cache-Control", "no-cache");
            }
            else
            {
                httpcon.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                httpcon.setRequestProperty("Cache-Control", "max-age=0");
            }          
            httpcon.setInstanceFollowRedirects(false);
            httpcon.connect(); 
            DataOutputStream out = new DataOutputStream(httpcon.getOutputStream());     
            out.writeBytes(data);
            out.flush();
            out.close();
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
            int a = httpcon.getResponseCode();
            if (302 == a)
                return null;
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
    public String grab(String data)
    {

        String str = "";
        String line;
        try 
        {
            httpconnect(data);
            int a = httpcon.getResponseCode();
            if (null == cookie)
            {
                Map<String, List<String>> map = httpcon.getHeaderFields();
                for (String key : map.keySet()) 
                    if (key != null && key.equals("Set-Cookie")) 
                    { 
                        List<String> list = map.get(key); 
                        StringBuilder builder = new StringBuilder(); 
                        for (String s : list) 
                            builder.append(s).toString();  
                       int tmp = builder.toString().indexOf(';');
                       cookie = builder.toString().substring(0, tmp + 1); 
                    }
            }
            if (a == 302)
            {
                this.setURL("https://rbs.lib.cuhk.edu.hk/Booking/Secure/NewBookingM.aspx");
                httpconnect();
            }
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

