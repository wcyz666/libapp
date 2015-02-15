package com.example.libapp;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author lenovo
 */
public class LibActvityParser{

    private List<LibActivity> content;
    private URL url;
    private HttpURLConnection httpcon;
    
    public LibActvityParser(){
        content = new ArrayList<LibActivity>();
    }
    public void setURL(String s)
    {
        try
        {
            this.url = new URL(s);
            httpcon = (HttpURLConnection)url.openConnection();
            httpcon.setDoInput(true);
            httpcon.setUseCaches(false);
        }
        catch (Exception e){}
    }
    public List<LibActivity> getLibActvity()
    {
        return this.content;
    }
    public void getHTML(String pat)
    {
        String webpage = "";        
        try
        {
            httpcon.connect();
            BufferedReader outStrm = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            String line;
            while ((line = outStrm.readLine()) != null)
                webpage = webpage + line + "\n"; 
            Pattern p = Pattern.compile(pat, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(webpage);
            while (m != null && m.find()){
                LibActivity contentitem = new LibActivity();
                contentitem.setDate(m.group(5) == null ? "" : m.group(5));
                contentitem.setURL(m.group(1) == null ? "" : "http://lib.cuhk.edu.hk" + m.group(1).replaceAll("amp;", ""));
                contentitem.setTitle(m.group(3) == null ? "" : m.group(3));
                content.add(contentitem);
            }
        }
        catch (IOException e) 
        {
            System.out.println();
        }
    }
    public List<String> getAllTitle()
    {
        List<String> list = new ArrayList<String>();
        Iterator i = content.iterator();
        int n = 0;
        while (i.hasNext())
            list.add(((LibActivity)i.next()).getTitle());
        return list;
    }
    public List<URL> getAllURL()
    {
        List<URL> list = new ArrayList<URL>();
        Iterator i = content.iterator();
        int n = 0;
        while (i.hasNext())
            list.add(((LibActivity)i.next()).getURL());
        return list;
    }
    
    /**
     * @param args the command line arguments
     */
    
}
