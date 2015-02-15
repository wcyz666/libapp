package com.example.libapp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author lenovo
 */
public class RoomViewer {
    
    private securePageGrabber grabber;
    private postDataAssembler assembler;
    private List<RoomRecord> records;
    private String username;
    private String passwd;
    
    public RoomViewer()
    {
        grabber = new securePageGrabber();
        
        assembler = new postDataAssembler(this.grabber);
    }
    
    private void setURL(String s)
    {
        this.grabber.setURL(s);
    }
    private void setURL(URL u)
    {
        this.grabber.setURL(u);
    }
    public void setUsernameAndPassword(String username, String passwd)
    {
        this.username = username;
        this.passwd = passwd;
    }
    private RoomRecord createRecord(String page)
    {
        RoomRecord item = new RoomRecord(grabber.cookie);
        
         int pos = 0, pos1 = 0;
         
         pos = page.indexOf("__VIEWSTATE", 0);
         pos1 = page.indexOf("value", pos);
         pos = page.indexOf("\"", pos1+10);
         item.setView(page.substring(pos1 + 7, pos));
         
         pos = page.indexOf("__EVENTVALIDATION", pos);
         pos1 = page.indexOf("value", pos);
         pos = page.indexOf("\"", pos1+10);
         item.setVali(page.substring(pos1 + 7, pos));
         
         pos = page.indexOf("ctl00_main_fvRecord_lblDate", pos);
         pos1 = page.indexOf(">", pos);
         pos = page.indexOf("<", pos1+2);
         item.setStartDay(page.substring(pos1 + 1, pos));
         
         pos = page.indexOf("ctl00_main_fvRecord_lblTime", pos);
         pos1 = page.indexOf(">", pos);
         pos = page.indexOf("<", pos1+2);
         item.setEndDay(page.substring(pos1 + 1, pos));
         
         pos = page.indexOf("ctl00_main_fvRecord_lblDuration", pos);
         pos1 = page.indexOf(">", pos);
         pos = page.indexOf("<", pos1+2);
         item.setDuration(page.substring(pos1 + 1, pos)+ "min");
         
         pos = page.indexOf("ctl00_main_fvRecord_lblType", pos);
         pos1 = page.indexOf(">", pos);
         pos = page.indexOf("<", pos1+2);
         item.setLocation(page.substring(pos1 + 1, pos));
         
         pos = page.indexOf("ctl00_main_fvRecord_lblFacility", pos);
         pos1 = page.indexOf(">", pos);
         pos = page.indexOf("<", pos1+2);
         item.setRoom(page.substring(pos1 + 1, pos));
         
         pos = page.indexOf("ctl00_main_fvRecord_lblStatus", pos);
         pos1 = page.indexOf(">", pos);
         pos = page.indexOf("<", pos1+2);
         item.setStatus(page.substring(pos1 + 1, pos));
         
         return item;
    }
    public void viewingExec()
    {
        String page;
        int i = 0;
        String data = "";
        records = new ArrayList<RoomRecord>();
        
        grabber.setURL("https://rbs.lib.cuhk.edu.hk/Booking/LoginM.aspx");
        page = grabber.grab();
        assembler.setWebpage(page);
        grabber.flag = true;
        grabber.grab(assembler.assemble(username, passwd));
        grabber.flag = false;
        grabber.setURL("https://rbs.lib.cuhk.edu.hk/Booking/Secure/MyBookingRecordM.aspx ");
        page = grabber.grab();
        if (page.indexOf("No records found") != -1)
        {
            return;
        }
        assembler.setWebpage(page);
        records.add(createRecord(page));
        data = assembler.assemble(records.get(i));
        while (page.indexOf(">>") != -1)
        {
            page = grabber.grab(data);
            i++;
            assembler.setWebpage(page);
            records.add(createRecord(page));
            data = assembler.assemble(records.get(i));
        }
    }
    public List<RoomRecord> getRecord()
    {
        return this.records;
    }
        
}
