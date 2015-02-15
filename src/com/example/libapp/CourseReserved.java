package com.example.libapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class CourseReserved {
    private URL url;
    private HttpURLConnection httpcon;
    public ArrayList<Course> Courses;
    // private ArrayList<Detail> Details;
    // should be private...
    public ArrayList<Detail> Details;
    
//    public ArrayList<Detail> getCourseList {
//    	return this.Details;
//    }
    public void GetDetails(String s){
        String webpage;
        
        this.Details = new ArrayList<Detail>();
        this.setURL("http://library.cuhk.edu.hk"+s);
        try
        {
            this.httpcon.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            String line;
            webpage = "";
            while((line = br.readLine())!= null){
                webpage += line;
                //System.out.println(line);
            }
            int flag=0;
            flag =webpage.substring(flag).indexOf("<table width=\"100%\" border=\"3\" cellspacing=\"1\" class=\"reserveBibs\">");
            while(true){
               // p = Pattern.compile("<a href=\"/record=");
               // m = p.matcher(webpage.substring(flag));
                
               // if(m.find()){
                int idx=0;
                int start=0;
                int end=0;

                String proc;
                char[] title = new char[1024];
                char[] author = new char[1024];
                char[] info = new char[1024];
                char[] href = new char[1024];
                Detail tempdetail = new Detail();
                //System.out.println();
                idx =webpage.substring(flag).indexOf("<tr>");
                if(idx!= -1){
                    start = webpage.substring(flag).indexOf("<a href=\"", idx)+9;
                    end = webpage.substring(flag).indexOf("\"",start);
                    webpage.substring(flag).getChars(start, end+1, href, 0);
                    //items.add((new String(name))+"!");
                    proc = new String(href);
                    idx = proc.indexOf('\0');
                    proc = proc.substring(0, idx-1);
                    tempdetail.Href = proc;
                    idx = end;
                    start = webpage.substring(flag).indexOf(">", idx)+1;
                    end = webpage.substring(flag).indexOf("/",start);
                    webpage.substring(flag).getChars(start, end+1, title, 0);
                    //items.add((new String(name))+"!");
                    proc = new String(title);
                    idx = proc.indexOf('\0');
                    proc = proc.substring(0, idx-1);
                    tempdetail.Title = proc;
                    //idx =end;
                    idx = webpage.substring(flag).indexOf("<td", end);
                    start = webpage.substring(flag).indexOf(">", idx)+1;
                    end = webpage.substring(flag).indexOf("<",start)-1;
                    webpage.substring(flag).getChars(start, end+1, author, 0);
                    //items.add((new String(name))+"!");
                    proc = new String(author);
                    idx = proc.indexOf('\0');
                    proc = proc.substring(0, idx);
                    tempdetail.Author = proc;
                    idx = webpage.substring(flag).indexOf("<td", end);
                    start = webpage.substring(flag).indexOf(">", idx)+1;
                    end = webpage.substring(flag).indexOf("<",start)-1;
                    webpage.substring(flag).getChars(start, end+1, info, 0);
                    //items.add((new String(name))+"!");
                    proc = new String(info);
                    idx = proc.indexOf('\0');
                    proc = proc.substring(0, idx);
                    tempdetail.Info = proc;
                    this.Details.add(tempdetail);
                    
                    flag += end;
                   // System.out.println(name);
                    
   
                    /*for(int cnt=start;cnt<=end;cnt++){
                        ;
                        
                    }*/
                }
                else
                    break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void setURL(String s)
    {
        try
        {
            this.url = new URL(s);
            httpcon = (HttpURLConnection)url.openConnection();
            httpcon.setDoInput(true);
            httpcon.setUseCaches(false);
        }
        catch (Exception e)
        {
            System.out.println("HTTPs connection failed!!!!!!!!!");
            e.printStackTrace();
        }
    }
     
     public void GetCourses(String s){
        String webpage;
         
        this.Courses = new ArrayList<Course>();
        this.setURL("http://library.cuhk.edu.hk/search/r?SEARCH="+s);
        try{
            this.httpcon.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            String line;
            webpage = "";
            while((line = br.readLine())!= null){
                webpage += line;
                //System.out.println(line);
            }
            int flag=0;
            while(true){
               // p = Pattern.compile("<a href=\"/record=");
               // m = p.matcher(webpage.substring(flag));
                
               // if(m.find()){
                int idx=0;
                int start=0;
                int end=0;

                String proc;
                char[] href = new char[1024];
                char[] name = new char[1024];
                char[] quantity = new char[4];
                Course tempcourse = new Course();
                //System.out.println();
                idx =webpage.substring(flag).indexOf("<td class=\"browseEntryData\">");
                if(idx!= -1){
                    start = webpage.substring(flag).indexOf("<a href=\"", idx)+9;
                    end = webpage.substring(flag).indexOf("\"",start);
                    webpage.substring(flag).getChars(start, end+1, href, 0);
                    //items.add((new String(name))+"!");
                    proc = new String(href);
                    idx = proc.indexOf('\0');
                    proc = proc.substring(0, idx-1);
                    tempcourse.href = proc;
                    idx = end;
                    start = webpage.substring(flag).indexOf(">", idx)+1;
                    end = webpage.substring(flag).indexOf("<",start);
                    webpage.substring(flag).getChars(start, end+1, name, 0);
                    //items.add((new String(name))+"!");
                    proc = new String(name);
                    idx = proc.indexOf('\0');
                    proc = proc.substring(0, idx-1);
                    tempcourse.name = proc;
                    idx =webpage.substring(flag).indexOf("<td align=\"right\" class=\"browseEntryEntries\"");
                    start = webpage.substring(flag).indexOf(">", idx)+1;
                    end = webpage.substring(flag).indexOf("<",start)-1;
                    webpage.substring(flag).getChars(start, end+1, quantity, 0);
                    //items.add((new String(name))+"!");
                    proc = new String(quantity);
                    idx = proc.indexOf('\0');
                    proc = proc.substring(0, idx);
                    tempcourse.quantity = Integer.parseInt(proc);
                    this.Courses.add(tempcourse);
                    flag += end;
                   // System.out.println(name);
                    
   
                    /*for(int cnt=start;cnt<=end;cnt++){
                        ;
                        
                    }*/
                }
                else
                    break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
         return;
     }
}
