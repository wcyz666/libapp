package com.example.libapp;


import java.net.URLEncoder;

/**
 *
 * @author lenovo
 */
public class RoomRecord {
    
    private String startday;
    private String endday;
    private String duration;
    private String location;
    private String room;
    private String status;
    private String viewState;
    private String Vali;
    private String[] cancelpara = new String[5];
    private securePageGrabber grabber;
    private postDataAssembler assembler;
    
    
    public RoomRecord setNull() {
    	this.location = "no booking";
    	this.duration = "";
    	this.startday = "";
    	return this;
    }
    
    public RoomRecord(String cookie)
    {
        grabber = new securePageGrabber();
        grabber.cookie = cookie;
        assembler = new postDataAssembler(this.grabber);
    }
    private void getParameters(String page)
    {
        try 
        {
            int pos = 0, pos1 = 0;
            pos = page.indexOf("__VIEWSTATE", 0);
            pos1 = page.indexOf("|", pos);
            pos = page.indexOf("|", pos1+10);
            viewState = page.substring(pos1 + 1, pos);
            pos = page.indexOf("__EVENTVALIDATION", pos1);
            pos1 = page.indexOf("|", pos);
            pos = page.indexOf("|", pos1+10);
            this.Vali = page.substring(pos1 + 1, pos);
        }
        catch (Exception e) { }
    }
    public void cancel()
    {
        try
        {
            String page;
            grabber.flag = true;
            grabber.setURL("https://rbs.lib.cuhk.edu.hk/Booking/Secure/MyBookingRecordM.aspx");
            String postData = "";
            postData += "ctl00%24main%24ToolkitScriptManager1=ctl00%24main%24upMain%7Cctl00%24main%24fvRecord%24btnDelete&__LASTFOCUS=&ctl00_main_ToolkitScriptManager1_HiddenField=&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=";
            postData += URLEncoder.encode(this.viewState,"UTF-8") + "&";
            postData += "__VIEWSTATEENCRYPTED=&__EVENTVALIDATION="+ URLEncoder.encode(this.Vali,"UTF-8") + "&";
            postData += "ctl00%24main%24fvRecord%24hNoCancel=0&ctl00%24main%24fvRecord%24hAwaitBooking=" + cancelpara[1] + "&ctl00%24main%24fvRecord%24hFacilityID=" + cancelpara[2] + "&ctl00%24main%24fvRecord%24hName=" + cancelpara[3] + "&ctl00%24main%24fvRecord%24hDisplay=" + URLEncoder.encode(cancelpara[4], "UTF-8").replace("+", "%20") + "&ctl00%24main%24fvRecord%24hSpecifiedTime=&__ASYNCPOST=true&ctl00%24main%24fvRecord%24btnDelete=Cancel";
            
            page = grabber.grab(postData);
            
            getParameters(page);
            postData = "";
            postData += "ctl00%24main%24ToolkitScriptManager1=ctl00%24main%24upMain%7Cctl00%24main%24btnDeleteYes&__LASTFOCUS=&ctl00_main_ToolkitScriptManager1_HiddenField=&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=";
            postData += URLEncoder.encode(this.viewState,"UTF-8") + "&";
            postData += "__VIEWSTATEENCRYPTED=&__EVENTVALIDATION="+ URLEncoder.encode(this.Vali,"UTF-8") + "&__ASYNCPOST=true&ctl00%24main%24btnDeleteYes=Yes";
            
            grabber.grab(postData);
        }
        catch (Exception e) {}
       
    }
    public void setCanPara(String[] para)
    {
        for (int i = 0; i < para.length; i++)
            this.cancelpara[i] = para[i];
    }
    public String getStartDay()
    {
        return this.startday;
    }
    public void setStartDay(String s)
    {
        this.startday = s;
    }
    public String getEndDay()
    {
        return this.endday;
    }
    public void setEndDay(String s)
    {
        this.endday = s;
    }
    public String getDuration()
    {
        return this.duration;
    }
    public void setDuration(String s)
    {
        this.duration = s;
    }
    public String getLocation()
    {
        return this.location;
    }
    public void setLocation(String s)
    {
        this.location = s;
    }
    public String getStatus()
    {
        return this.status;
    }
    public void setStatus(String s)
    {
        this.status = s;
    }
    public String getRoom()
    {
        return this.room;
    }
    public void setRoom(String s)
    {
        this.room = s;
    }
    public void setView(String s)
    {
        this.viewState = s;
    }
    public void setVali(String s)
    {
        this.Vali = s;
    }
}
