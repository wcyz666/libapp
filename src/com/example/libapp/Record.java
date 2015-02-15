package com.example.libapp;


import java.net.*;
import javax.net.ssl.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author maktf
 */
public class Record {
    private String template;
    private URL url;
    private HttpsURLConnection httpscon;
    private String cookie;
    private ArrayList<CKBook> books;
    public ArrayList<CKBook> getBooks(){
        return this.books;
    };
    

    public String getCookie() {
        return this.cookie;
    }

    private void setURL(String s)
    {
        try
        {
            this.url = new URL(s);
            httpscon = (HttpsURLConnection)url.openConnection();
            httpscon.setDoInput(true);
            httpscon.setUseCaches(false);
        }
        catch (Exception e)
        {
            System.out.println("HTTPs connection failed!!!!!!!!!");
            e.printStackTrace();
        }
    }
    
    public void Log(String sid, String passwd){
        String webpage;
        this.setURL("https://m.library.cuhk.edu.hk/patroninfo");
        try
        {
            this.httpscon.setRequestMethod("POST");
            this.httpscon.setDoOutput(true);
            this.httpscon.setDoInput(true);
            this.httpscon.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            this.httpscon.setInstanceFollowRedirects(false);
            OutputStreamWriter osw = new OutputStreamWriter(httpscon.getOutputStream(),"UTF-8");
            osw.write(this.prepareParam(sid, passwd));
            osw.flush();
            osw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
            int response = httpscon.getResponseCode();
            if(response == 302){
                Map<String, List<String>> map = httpscon.getHeaderFields();
                List<String> temp = map.get("Set-Cookie");
                if(temp == null)
                {
                    System.out.println("No cookie to be set!");
                }
                else{
                    int cnt=0;
                    for(String value : temp){
                        if(value != null){
                            if(cnt ==0){
                                this.cookie = value;
                            }
                            else{
                                this.cookie += value;
                            }
                            cnt++;
                        }
                    }
                }
                temp = map.get("Location");
                if(temp == null){
                    System.out.println("Not 302");
                }
                else{
                     for(String value : temp){
                        if(value != null){
                            this.template = "https://m.library.cuhk.edu.hk"+value;
                            this.setURL(this.template);
                        }
                    }
                }
            
                this.httpscon.setRequestMethod("GET");
                this.httpscon.setRequestProperty("Cookie", this.cookie);
                this.httpscon.connect();
            }
            else{
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(httpscon.getInputStream()));
            String line;
            webpage = "";
            while((line = br.readLine())!= null){
                webpage += line;
                //System.out.println(line);
            }
        }
        catch (Exception e) 
        {
           e.printStackTrace();
        }
        this.getItems();
        return;
    }
    
    private String prepareParam(String sid, String passwd){
        return new StringBuffer().append("code=").append(sid).append("&pin=").append(passwd)
        .append("&submit.x=56&submit.y=24&submit=submit").toString();
    }
    /**
     * @param args the command line arguments
     */
     public void getItems(){
        this.books = new ArrayList<CKBook>();
        String webpage;
        this.template = this.template.replace("top","items");
        this.setURL(this.template);
        try{
            this.httpscon.setRequestMethod("GET");
            this.httpscon.setRequestProperty("Cookie", this.cookie);
            this.httpscon.connect();
            
            BufferedReader br = new BufferedReader(new InputStreamReader(httpscon.getInputStream()));
            String line;
            webpage = "";
            while((line = br.readLine())!= null){
                webpage += line;
            }
            
          
            int flag=0;
            while(true){

                int idx=0;
                int start=0;
                int end=0;

                String proc;
                char[] name = new char[1024];
                char[] date = new char[1024];
                CKBook tempbook = new CKBook();
                //System.out.println();
                idx =webpage.substring(flag).indexOf("<a href=\"/record=");
                if(idx!= -1){
                    start = webpage.substring(flag).indexOf(">", idx)+1;
                    end = webpage.substring(flag).indexOf("/",start)-1;
                    webpage.substring(flag).getChars(start, end+1, name, 0);
                    //items.add((new String(name))+"!");
                    proc = new String(name);
                    idx = proc.indexOf('\0');
                    proc = proc.substring(0, idx-1);
                    tempbook.name = proc;
                    idx =webpage.substring(flag).indexOf("<td align=\"left\" class=\"patFuncStatus\"");
                    start = webpage.substring(flag).indexOf(">", idx)+1;
                    end = webpage.substring(flag).indexOf("<",start)-1;
                    webpage.substring(flag).getChars(start, end+1, date, 0);
                    //items.add((new String(name))+"!");
                    proc = new String(date);
                    idx = proc.indexOf('\0');
                    proc = proc.substring(0, idx-1);
                    tempbook.due = proc;
                    this.books.add(tempbook);
                    flag += end;
                   // System.out.println(name);
                    
   
                    /*for(int cnt=start;cnt<=end;cnt++){
                        ;
                        
                    }*/
                }
                else
                    break;
            }
            flag =0;
            int cnt =0;
            while(true){
                int idx=0;
                int start=0;
                int end=0;

                String proc;
                char[] id = new char[32];
                char[] value = new char[32];

                idx =webpage.substring(flag).indexOf("<input type=\"checkbox\"");
                if(idx!= -1){
                    CKBook tempbook = this.books.get(cnt);
                    start = webpage.substring(flag).indexOf("id=\"", idx)+4;
                    end = webpage.substring(flag).indexOf("\"",start);
                    webpage.substring(flag).getChars(start, end+1, id, 0);
                    //items.add((new String(name))+"!");
                    proc = new String(id);
                    idx = proc.indexOf('\0');
                    proc = proc.substring(0, idx-1);
                    tempbook.boxid = proc;
                    idx =end;
                    start = webpage.substring(flag).indexOf("value=\"", idx)+7;
                    end = webpage.substring(flag).indexOf("\"",start);
                    webpage.substring(flag).getChars(start, end+1, value, 0);
                    proc = new String(value);
                    idx = proc.indexOf('\0');
                    proc = proc.substring(0, idx-1);
                    tempbook.boxvalue = proc;
                    cnt++;
                    flag += end;

                }
                else
                    break;
            }
        }
        catch (Exception e) 
        {
           e.printStackTrace();
        }
        
        
        
        return;
    }
        private String prepareRenewSome(ArrayList<CKBook> target){
        int cnt=0;
        StringBuffer sb = new StringBuffer();
        for(CKBook livre : target){
            sb.append(livre.boxid);
            sb.append("=");
            sb.append(livre.boxvalue);
            sb.append("&");
        }
        sb.append("currentsortorder");
        sb.append("=");
        sb.append("current_checkout");
        sb.append("&");
        sb.append("currentsortorder");
        sb.append("=");
        sb.append("current_checkout");
        sb.append("&");
        sb.append("renewsome");
        sb.append("=");
        sb.append("YES");
        return sb.toString();
    }
        
    public boolean RenewAll(){
        String webpage;
        try
        {
            //this.httpscon.disconnect();
            this.setURL(this.template);
            this.httpscon.setRequestMethod("POST");
            //this.httpscon.setRequestProperty("Cookie", this.cookie);
            this.httpscon.setDoOutput(true);
            this.httpscon.setDoInput(true);
            this.httpscon.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            this.httpscon.setInstanceFollowRedirects(false);
            this.httpscon.setRequestProperty("Cookie", this.cookie);
            OutputStreamWriter osw = new OutputStreamWriter(httpscon.getOutputStream(),"UTF-8");
            String check = this.prepareConfirm();
            System.out.println(check);
            osw.write(this.prepareConfirm());
            osw.flush();
            osw.close();
            this.httpscon.connect();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
        BufferedReader br = new BufferedReader(new InputStreamReader(httpscon.getInputStream()));
        String line;
        webpage = "";
            while((line = br.readLine())!= null){
                webpage += line;
            }

            int flag=0;

                int idx=0;
                idx =webpage.substring(flag).indexOf("<font color=\"red\"");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
        
    private String prepareConfirm(){
        StringBuffer sb = new StringBuffer();

        sb.append("currentsortorder");
        sb.append("=");
        sb.append("current_checkout");
        sb.append("&");
        sb.append("currentsortorder");
        sb.append("=");
        sb.append("current_checkout");
        sb.append("&");
        sb.append("renewall");
        sb.append("=");
        sb.append("YES");

        return sb.toString();
    }   
        public boolean RenewSome(ArrayList<CKBook> target){
        String webpage;
        try
        {
            this.setURL(this.template);
            this.httpscon.setRequestMethod("POST");
            this.httpscon.setDoOutput(true);
            this.httpscon.setDoInput(true);
            this.httpscon.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            this.httpscon.setInstanceFollowRedirects(false);
            this.httpscon.setRequestProperty("Cookie", this.cookie);
            OutputStreamWriter osw = new OutputStreamWriter(httpscon.getOutputStream(),"UTF-8");
            String check = this.prepareRenewSome(target);
            System.out.println(check);
            osw.write(this.prepareRenewSome(target));
            osw.flush();
            osw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
        BufferedReader br = new BufferedReader(new InputStreamReader(httpscon.getInputStream()));
        String line;
        webpage = "";
        while((line = br.readLine())!= null){
                webpage += line;
            }
            System.out.println(webpage);
            
            int flag=0;

            int idx=0;
            idx =webpage.substring(flag).indexOf("<font color=\"red\"");
            if(idx!= -1){
                  return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    
}
