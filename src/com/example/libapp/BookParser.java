package com.example.libapp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookParser {
    private List<NormalBook> booklist;
    private BookPageGrabber grabber;
    private URL[] nextpage;
    private boolean flag;
    private int currentbook;
    private int pagenum;
    private String ebookurl;
    

    public BookParser(){
        booklist = new ArrayList<NormalBook>();
        grabber = new BookPageGrabber();
        flag = true;
        currentbook = 0;
        pagenum = -1;
        nextpage = new URL[10];
    }
    public void setEBookURL(String s)
    {
        this.ebookurl = s;
    }
            
    public void setKeyWords(String s)
    {
        String key = "";
        String[] tmp = s.split(" ");
        for (int i = 0; i < tmp.length; i++)
            if (tmp[i].length() > 0)
                key = key + tmp[i] + "+";
        key = key.substring(0, key.length() - 1);
        key = "http://m.library.cuhk.edu.hk/search/Y?SEARCH=" + key + "&searchscope=15";
        setURL(key);
    }
    private void setURL(String s)
    {
        
        grabber.setURL(s);
    }
     private void setURL(URL s)
    {
        
        grabber.setURL(s);
    }
    public List<NormalBook> getBookList()
    {
        return this.booklist;
    }
  /*  public void getCookieAndLocation()
    {
        HttpURLConnection conn;
        try
        {
            conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.connect();
            Map<String,List<String>> map=conn.getHeaderFields(); 

            for (String key : map.keySet()) 
            { 
                if (key != null && key.equals("Set-Cookie")) 
                { 
                    List<String> list = map.get(key); 
                    StringBuilder builder = new StringBuilder(); 
                    for (String str : list) 
                        builder.append(str).toString();  
                    cookie = builder.toString(); 
                    httpcon.setRequestProperty("Cookie", cookie);
            }
            else
                if (key != null && key.equals("Location"))
                {
                     List<String> list = map.get(key); 
                     StringBuilder builder = new StringBuilder(); 
                     for (String str : list) 
                        builder.append(str).toString(); 
                     this.httpsurl = new URL(builder.toString());
                }
            }
        }
        catch (Exception e)
        {
        ;
        }

        
    }
    private void httpsauth(String pat)
    {
        int pos, pos1;
        try
        {
            httpscon = (HttpsURLConnection)httpsurl.openConnection();
            httpscon.setDoInput(true);
            httpscon.setUseCaches(false);
            httpscon.setInstanceFollowRedirects(true);
            httpscon.setRequestProperty("Cookie", cookie);
            httpscon.connect(); 
            String webpage = getwholepage(httpscon);
            pos = webpage.indexOf(pat, 0);
            pos1 = webpage.indexOf("\'", pos);
            this.url = new URL(webpage.substring(pos + 6, pos1));
        }
        catch (Exception e) { }
    }*/
    private void getPager(String webpage, String pagerdeli)
   {
       int pos = 0, pos1 = 0;
       if (flag == true)
       {
           String[] pager = new String[10];
            pos1 = webpage.indexOf(pagerdeli, pos);
            for (int i= 0; i < 10; i++)
            {
                pos = webpage.indexOf("href=\"", pos1);
                pos1 = webpage.indexOf("\">", pos);
                pager[i] = "http://m.library.cuhk.edu.hk" + webpage.substring(pos + 6, pos1);
            }
            pagertoURL(pager);
            this.flag = false;
       }
       else
           return;
       
   }
    private void pagertoURL(String[] pager)
    {
        for (int i = 0; i < pager.length; i++)
        {
            try
            {
                this.nextpage[i] = new URL(pager[i]);
            }
            catch (Exception e){}
        }
    }
    
    public void getNextPage(String pat, String deli1, String deli2, int preamble)
    {
        this.setURL(this.nextpage[pagenum]);
        this.getHTML(pat, deli1, deli2, preamble);
        return;
        
    }
    public void getHTML(String pat, String deli1, String deli2, int preamble)
    {
        String webpage;
        int pos = 0, pos1 = 0, pos2 = 0, opos = 0, totalbook = 0, tmp = 0;
        String bookimgurl;
        String bookurl;
        String title;
        String author = "";
        String edition = "";
        String status;
        ++this.pagenum;
        try
        {         
            webpage = grabber.grab();
            getPager(webpage.substring(0, 8000), "browsePager");

            while (webpage.indexOf(pat, opos) != -1)
            {
                opos = webpage.indexOf(pat, opos);
                tmp = webpage.indexOf("</div>", opos);
            
                if (tmp - opos < 40)
                {
                    pos = opos;
                    pos1 = opos;
                    bookimgurl = this.ebookurl;
                }
                else
                {
                    pos = webpage.indexOf("href=", opos);
                    pos1= webpage.indexOf("\" ", pos + 7);
                    bookimgurl = webpage.substring(pos + 6, pos1).replace("\n", "");
                }
                pos = webpage.indexOf("href=", pos1);
                pos1= webpage.indexOf("\">", pos + 7);
                bookurl = "http://m.library.cuhk.edu.hk" + webpage.substring(pos + 6, pos1);          
                pos = webpage.indexOf("</a>", pos1);
                title = webpage.substring(pos1 + 2, pos).replace("\n", ""); 
                int pos3 = title.indexOf(" / ");            
                if (pos3 != -1)
                      title = title.substring(0, pos3);
                pos = webpage.indexOf("<p>", pos) + 3;
                while (webpage.charAt(pos) == '\n' || webpage.charAt(pos) == ' ')
                    ++pos;
                pos1 = webpage.indexOf("</p>", pos);
                if (pos + 6 < pos1)
                {
                    author = webpage.substring(pos, pos1).replace("\n", "");

                }
                pos = webpage.indexOf("<p>", pos1) + 3;
                while (webpage.charAt(pos) == '\n' || webpage.charAt(pos) == ' ')
                    ++pos;
                pos1 = webpage.indexOf("</p>", pos);
                if (pos + 6 < pos1)
                    edition = webpage.substring(pos, pos1).replace("\n", "");
                pos = webpage.indexOf("\">", pos1) + 2;
                while (webpage.charAt(pos) == '\n' || webpage.charAt(pos) == ' ')
                    ++pos;
                pos1 = webpage.indexOf("</p>", pos);
                if (pos + 6 > pos1)
                    status = "NOT CHECKED OUT";
                else
                    status = "NOT AVAILABLE";
                opos = pos;
                booklist.add(new NormalBook(author, title, "", status, "", bookurl, bookimgurl, "", edition));
                ++totalbook;
                ++this.currentbook;
                
            }
            currentbook -= totalbook;
        }
        catch (Exception e) 
        {
            System.out.println();
        }
    }
    /**
     * @param args the command line arguments
     */
}
