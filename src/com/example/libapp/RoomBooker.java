package com.example.libapp;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.*;
import java.util.regex.*;


/**
 *
 * @author lenovo
 */
public class RoomBooker implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final securePageGrabber grabber;
    private final postDataAssembler assembler;
    private String username;
    private String passwd;
    private String error;
    private String page;
    private String year;
    private String month;
    private String day;
    private String starttime;
    private String startmin;
    private String lasttime;
    private String lastmin;
    private String cyear;
    private String cmonth;
    private String cday;
    private String cstarttime;
    private String cstartmin;
    private String clasttime;
    private String clastmin;
    private String bookedroom;
    private String tel;
   
    public RoomBooker() 
    {
        grabber = new securePageGrabber();
        grabber.setURL("https://rbs.lib.cuhk.edu.hk/Booking/LoginM.aspx");
        assembler = new postDataAssembler(this.grabber);
    }

    private void getYMD()
    {
        int pos = 0, pos1;
        pos = page.indexOf("selected", pos);
        pos1 = page.indexOf(">", pos);
        pos = page.indexOf("<", pos1);
        this.cyear = page.substring(pos1 + 1, pos);
        pos = page.indexOf("selected", pos);
        pos1 = page.indexOf("value", pos);
        pos = page.indexOf("\">", pos1);
        this.cmonth = page.substring(pos1 + 7, pos);
        pos = page.indexOf("selected", pos);
        pos1 = page.indexOf(">", pos);
        pos = page.indexOf("<", pos1);
        this.cday = page.substring(pos1 + 1, pos);
    }
    private void getDefaultParameters() 
    {
        int pos = 0, pos1;
        pos = page.indexOf("selected", pos);
        pos1 = page.indexOf(">", pos);
        pos = page.indexOf("<", pos1);
        this.cyear = page.substring(pos1 + 1, pos);
        pos = page.indexOf("selected", pos);
        pos1 = page.indexOf("value", pos);
        pos = page.indexOf("\">", pos1);
        this.cmonth = page.substring(pos1 + 7, pos);
        pos = page.indexOf("selected", pos);
        pos1 = page.indexOf("value", pos);
        pos = page.indexOf("\">", pos1);
        this.cday = page.substring(pos1 + 7, pos);
        pos = page.indexOf("selected", pos);
        pos1 = page.indexOf("value", pos);
        pos = page.indexOf("\">", pos1);
        this.cstarttime = page.substring(pos1 + 7, pos);
        pos = page.indexOf("selected", pos);
        pos1 = page.indexOf("value", pos);
        pos = page.indexOf("\">", pos1);
        this.cstartmin = page.substring(pos1 + 7, pos);
        if (cstartmin.charAt(0) == '0')
            cstartmin = "0";
        pos = page.indexOf("selected", pos);
        pos1 = page.indexOf("value", pos);
        pos = page.indexOf("\">", pos1);
        this.clasttime = page.substring(pos1 + 7, pos);
        pos = page.indexOf("selected", pos);
        pos1 = page.indexOf("value", pos);
        pos = page.indexOf("\">", pos1);
        this.clastmin = page.substring(pos1 + 7, pos);  
        if (clastmin.charAt(0) == '0')
            clastmin = "0";
    }
    private Map<String, String> getBookedRoom()
    {
        int pos = page.indexOf("Available Facility", 0);
        int i = 0;
        Pattern r = Pattern.compile("^\\s*<option\\s+value=\"([0-9]*)\"\\s*>(.*)</option>\\s*$", Pattern.CASE_INSENSITIVE);
        Matcher m = null;
        Map<String, String> hash = new HashMap<>();
        String[] str = page.substring(pos, page.length()-1).split("\n");
        
        for (; i < str.length; i++){
            m = r.matcher(str[i]);
            if (m.find())
                break;
        }
        while (m != null){
            hash.put(m.group(2), m.group(1));
            i++;
            m = r.matcher(str[i]);
            if (!m.find())
                break;
        }
 
        return hash;
    }
    private void checkError() throws BookFailedException
    {
        int pos = page.indexOf("class=\"error\"", 0);
        int pos1 = page.indexOf(">", pos);
        pos = page.indexOf("<", pos1);
        if (pos - pos1 >= 10)
           throw new BookFailedException(page.substring(pos1 + 1, pos));
    }

    public Map<String, String> bookingExecFirstPhase()
    {
        try
        {
            assembler.setWebpage(page);
            if (Integer.parseInt(cyear) != Integer.parseInt(year))
            {
                page = grabber.grab(assembler.assemble("ctl00%24main%24ddlYear", year, cmonth, cday, "", cstarttime, cstartmin, clasttime, clastmin, ""));
                assembler.setWebpage(page);
            }
            if (Integer.parseInt(cmonth) != Integer.parseInt(month))
            {
                page = grabber.grab(assembler.assemble("ctl00%24main%24ddlMonth", year, month, cday, "", cstarttime, cstartmin, clasttime, clastmin, ""));
                assembler.setWebpage(page);
            }
            if (Integer.parseInt(cday) != Integer.parseInt(day))
            {
                page = grabber.grab(assembler.assemble("ctl00%24main%24ddlDay", year, month, day, "", cstarttime, cstartmin, clasttime, clastmin, ""));
                assembler.setWebpage(page);
            }
            if (Integer.parseInt(cstarttime) != Integer.parseInt(starttime))
            {
                page = grabber.grab(assembler.assemble("ctl00%24main%24ddlStartHour", year, month, day, "", starttime, cstartmin, clasttime, clastmin, ""));
                assembler.setWebpage(page);
            }
            if (Integer.parseInt(cstartmin) != Integer.parseInt(startmin))
            {
                page = grabber.grab(assembler.assemble("ctl00%24main%24ddlStartMinute", year, month, day, "", starttime, startmin, clasttime, clastmin, ""));
                assembler.setWebpage(page);
            }
            if (Integer.parseInt(clasttime) != Integer.parseInt(lasttime))
            {
                page = grabber.grab(assembler.assemble("ctl00%24main%24ddlEndHour", year, month, day, "", starttime, startmin, lasttime, clastmin, ""));
                assembler.setWebpage(page);
            }
            if (Integer.parseInt(clastmin) != Integer.parseInt(lastmin))
            {
                page = grabber.grab(assembler.assemble("ctl00%24main%24ddlEndMinute", year, month, day, "", starttime, startmin, lasttime, lastmin, ""));
            }
            checkError();
            return getBookedRoom();
        }
        catch (BookFailedException | NumberFormatException e1)
        {
            this.error = e1.getMessage();
            grabber.setURL("https://rbs.lib.cuhk.edu.hk/Booking/Logout.aspx");
            grabber.grab();
            grabber.setURL("https://rbs.lib.cuhk.edu.hk/Booking/LoginM.aspx");
            grabber.cookie = null;
        }
        return null;
    }
    public int bookingExecSecondPhase(String booknum)
    {
        try
        { 
            assembler.setWebpage(page);
            page = grabber.grab(assembler.assemble("", year, month, day, "", starttime, startmin, lasttime, lastmin, "") + "&ctl00%24main%24ddlFacility=" + booknum + "&ctl00%24main%24btnFrontView=View");
            assembler.setWebpage(page);
            if (month.length() == 1)
                month = "0" + month;
            if (day.length() == 1)
                day = "0" + day;
            if (starttime.length() == 1)
                starttime = "0" + starttime;
            if (lasttime.length() == 1)
                lasttime = "0" + lasttime;
            if (lastmin.length() == 1)
                lastmin = "0" + lastmin;
            if (startmin.length() == 1)
                startmin = "0" + startmin;  
            grabber.flag = true;
            String re = assembler.assemble(year + month + day, starttime, startmin, lasttime, lastmin, booknum, tel);
            
            if (re.indexOf("successful") != -1)  
                return 1;
            else
                return 0;
        }
        catch (BookFailedException e)
        {
            this.error = e.getMessage();
            grabber.setURL("https://rbs.lib.cuhk.edu.hk/Booking/Logout.aspx");
            grabber.grab();
            grabber.setURL("https://rbs.lib.cuhk.edu.hk/Booking/LoginM.aspx");
            grabber.cookie = null;
        }
        catch (NumberFormatException e)
        {
            return -1;
        } 
        return -1;
    }
    public int setParameters(String y, String m, String d, String st, String sm, String lt, String lm, String tel)
    {
        this.year =  y; 
        this.month =  m;
        this.day =  d;
        this.starttime = st;
        this.startmin =  sm;
        this.lasttime =  lt;
        this.lastmin = lm;
        this.tel = tel;
        //if (checkParameter() == -1)
         //   return -1;
        page = grabber.grab();
        assembler.setWebpage(page);
        grabber.flag = true;
        grabber.grab(assembler.assemble(username, passwd));
        grabber.flag = false;
        grabber.setURL("https://rbs.lib.cuhk.edu.hk/Booking/Secure/FacilityStatusM.aspx");
        page = grabber.grab();
        assembler.setWebpage(page);
        
        this.getYMD();
        page = grabber.grab(assembler.assemble("ctl00$main$cbAdvSearch", cyear, cmonth, cday, null, "", "", "", "", null));
        assembler.setWebpage(page);
        this.getDefaultParameters();
        
        page = grabber.grab(assembler.assemble("ctl00$main$ddlLibrary", cyear, cmonth, cday, "", cstarttime, cstartmin, clasttime, clastmin, null));
        assembler.setWebpage(page);
        page = grabber.grab(assembler.assemble("ctl00$main$ddlFacilityType", cyear, cmonth, cday, "", cstarttime, cstartmin, clasttime, clastmin, ""));
        return 0;
    }
    public String getRoom()
    {
        return this.bookedroom;
    }
    public String getError()
    {
        return this.error;
    }
    public void setUsernameAndPassword(String username, String passwd)
    {
        this.username = username;
        this.passwd = passwd;
    }

    
}
