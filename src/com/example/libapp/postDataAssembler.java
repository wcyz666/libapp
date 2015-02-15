package com.example.libapp;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * @author lenovo
 */
public class postDataAssembler implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String webpage;
    private String viewState;
    private String stateValidation;
    private final securePageGrabber grabber;
    private String Email;
    
    public postDataAssembler(securePageGrabber s) 
    {
        grabber = s;
    }
    public void setWebpage(String data) 
    {
        webpage = data;
    }
    private void getParameters(String page)
    {
        try 
        {
            int pos = 0, pos1;
            pos = webpage.indexOf("__VIEWSTATE", 0);
            pos1 = webpage.indexOf("|", pos);
            pos = webpage.indexOf("|", pos1+10);
            viewState = webpage.substring(pos1 + 1, pos);
            pos = webpage.indexOf("__EVENTVALIDATION", pos1);
            pos1 = webpage.indexOf("|", pos);
            pos = webpage.indexOf("|", pos1+10);
            stateValidation = "";
            stateValidation = webpage.substring(pos1 + 1, pos);
        }
        catch (Exception e) { }
    }
    private void getParameters()
    {
        try 
        {
            int pos, pos1;
            pos = webpage.indexOf("__VIEWSTATE", 0);
            pos1 = webpage.indexOf("value", pos);
            pos = webpage.indexOf("\"", pos1+10);
            viewState = webpage.substring(pos1 + 7, pos);
            pos = webpage.indexOf("__EVENTVALIDATION", pos1);
            pos1 = webpage.indexOf("value", pos);
            pos = webpage.indexOf("\"", pos1+10);
            this.stateValidation = webpage.substring(pos1 + 7, pos);
        }
        catch (Exception e) { }
    }
    private void getParameters(int s)
    {
        try 
        {
            int pos, pos1;
            pos = webpage.indexOf("__VIEWSTATE", 0);
            pos1 = webpage.indexOf("value", pos);
            pos = webpage.indexOf("\"", pos1+10);
            viewState = webpage.substring(pos1 + 7, pos);
            pos = webpage.indexOf("__EVENTVALIDATION", pos1);
            pos1 = webpage.indexOf("value", pos);
            pos = webpage.indexOf("\"", pos1+10);
            this.stateValidation = webpage.substring(pos1 + 7, pos);
            pos = webpage.indexOf("txtEmail", 0);
            pos1 = webpage.indexOf("value", pos);
            pos = webpage.indexOf("\"", pos1+10);
            Email = webpage.substring(pos1 + 7, pos);
        }
        catch (Exception e) { }
    }
    public String assemble(RoomRecord r)
    {
        try
        {
            String postData = "";
            getParameters();
            String[] paras = new String[5];
            
            int pos, pos1;
            pos = webpage.indexOf("ctl00$main$fvRecord$hNoCancel", 0);
            pos1 = webpage.indexOf("value", pos);
            pos = webpage.indexOf("\"", pos1+7);
            paras[0] = webpage.substring(pos1 + 7, pos);
            
            pos = webpage.indexOf("ctl00$main$fvRecord$hAwaitBooking", pos);
            pos1 = webpage.indexOf("value", pos);
            pos = webpage.indexOf("\"", pos1+7);
            paras[1] = webpage.substring(pos1 + 7, pos);
            
            pos = webpage.indexOf("ctl00$main$fvRecord$hFacilityID", pos);
            pos1 = webpage.indexOf("value", pos);
            pos = webpage.indexOf("\"", pos1+7);
            paras[2] = webpage.substring(pos1 + 7, pos);
            
            pos = webpage.indexOf("ctl00$main$fvRecord$hName", pos);
            pos1 = webpage.indexOf("value", pos);
            pos = webpage.indexOf("\"", pos1+7);
            paras[3] = webpage.substring(pos1 + 7, pos);
            
            pos = webpage.indexOf("ctl00$main$fvRecord$hDisplay", pos);
            pos1 = webpage.indexOf("value", pos);
            pos = webpage.indexOf("\"", pos1+7);
            paras[4] = webpage.substring(pos1 + 7, pos);
            
            r.setCanPara(paras);
            
            postData += "ctl00%24main%24ToolkitScriptManager1=ctl00%24main%24upMain%7Cctl00%24main%24fvRecord%24ctl01%24NextButton&__LASTFOCUS=&ctl00_main_ToolkitScriptManager1_HiddenField=&";
            postData += "ctl00%24main%24fvRecord%24hNoCancel=" + paras[0] + "&ctl00%24main%24fvRecord%24hAwaitBooking=" + paras[1] + "&ctl00%24main%24fvRecord%24hFacilityID=" + paras[2] + "&";
            postData += "&ctl00%24main%24fvRecord%24hName=" + paras[3] + "&ctl00%24main%24fvRecord%24hDisplay=" + URLEncoder.encode(paras[4], "UTF-8").replace("+", "%20") + "&";
            postData += "ctl00%24main%24fvRecord%24hSpecifiedTime=&__EVENTTARGET=ctl00%24main%24fvRecord%24ctl01%24NextButton&__EVENTARGUMENT=&__VIEWSTATE=";
            postData += URLEncoder.encode(this.viewState,"UTF-8") + "&";
            postData += "__EVENTVALIDATION="+ URLEncoder.encode(this.stateValidation,"UTF-8") + "&";
            postData += "__VIEWSTATEENCRYPTED=&__ASYNCPOST=true&";
            
            return postData;
        }
        catch (UnsupportedEncodingException e) {}
        return null;
    }
    public String assemble(String username, String passwd)
    {
        try
        {
            String postData = "";
            getParameters();
            postData += "ctl00%24main%24ToolkitScriptManager1=ctl00%24main%24UpdatePanel1%7Cctl00%24main%24btnLogin&ctl00_main_ToolkitScriptManager1_HiddenField=&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=";
            postData += URLEncoder.encode(this.viewState,"UTF-8") + "&";
            postData += "__EVENTVALIDATION="+ URLEncoder.encode(this.stateValidation,"UTF-8") + "&";
            postData += "ctl00%24main%24txtName=" + username + "&ctl00%24main%24txtPassword=" + passwd + "&__ASYNCPOST=true&ctl00%24main%24btnLogin=Login";
            
            return postData;
        }
        catch (UnsupportedEncodingException e) {}
        return null;
    }
    public String assemble(String tel)
    {
        try
        {
            String postData = "";
            getParameters();
            postData += "ctl00%24main%24ToolkitScriptManager1=ctl00%24main%24upMain%7Cctl00%24main%24btnSubmit&__EVENTTARGET=ctl00%24main%24btnSubmit&__EVENTARGUMENT=&ctl00_main_ToolkitScriptManager1_HiddenField=&__VIEWSTATE=";
            postData += URLEncoder.encode(this.viewState,"UTF-8") + "&";
            postData += "__EVENTVALIDATION="+ URLEncoder.encode(this.stateValidation,"UTF-8") + "&";
            postData += "ctl00%24main%24hMaxUsername=2&ctl00%24main%24txtContactNum=" + tel + "&ctl00%24main%24txtEmail=" + this.Email + "&ctl00%24main%24txtNoOfUsers=3&ctl00%24main%24txtPurpose=study&ctl00%24main%24txtRemark=&__ASYNCPOST=true";
            
            return postData;
        }
        catch (UnsupportedEncodingException e) {}
        return null;
    }
    private void checkError() throws BookFailedException
    {
        int pos = webpage.indexOf("class=\"error\"", 0);
        int pos1 = webpage.indexOf(">", pos);
        pos = webpage.indexOf("<", pos1);
        if (pos - pos1 >= 10)
           throw new BookFailedException(webpage.substring(pos1 + 1, pos));
    }    
    public String assemble(String date, String sh, String sm, String lh, String lm, String booknum, String tel) throws BookFailedException
    {
        try
        {
            String slot;
            String postData;
            String sloton="";
            int sh1 = Integer.parseInt(sh);
            int sm1 = Integer.parseInt(sm);
            int lh1 = Integer.parseInt(lh);
            int lm1 = Integer.parseInt(lm);
            getParameters();
            
            while (lm1 != sm1 || lh1 != sh1)
            {
                int tmph = sh1;
                int tmpm = sm1;
                if (sm1 == 30)
                {
                    sm1 = 0;
                    ++sh1;
                }
                else
                    sm1 = 30;
                slot = "";
                if (tmph < 10)
                    slot = slot + "0" + tmph;
                else
                    slot = slot + tmph;
                if (tmpm == 0)
                    slot = slot + "00";
                else
                    slot = slot + "30";
                if (sh1 < 10)
                    slot = slot + "0" + sh1;
                else
                    slot = slot + sh1;
                if (sm1 == 0)
                    slot = slot + "00";
                else
                    slot = slot + "30";
                postData = "";
                sloton = sloton + "ctl00%24main%24cb" + date + "_" + slot + "_" + booknum + "=on&";
                postData += "ctl00%24main%24ToolkitScriptManager1=ctl00%24main%24updatePanel1%7Cctl00%24main%24cb" + date + "_" + slot + "_" + booknum + "&";
                postData += "ctl00_main_ToolkitScriptManager1_HiddenField=&__EVENTTARGET=" + "ctl00%24main%24cb" + date + "_" + slot + "_" + booknum + "&";
                postData += "__EVENTARGUMENT=&__LASTFOCUS=&__VIEWSTATE=" + URLEncoder.encode(this.viewState,"UTF-8") + "&";
                postData += "__EVENTVALIDATION="+ URLEncoder.encode(this.stateValidation,"UTF-8") + "&";  
                postData += sloton;
                postData += "__ASYNCPOST=true&";

                webpage = grabber.grab(postData);
                
                getParameters(webpage);
            }
            grabber.flag = false;
            postData = "ctl00_main_ToolkitScriptManager1_HiddenField=&";
            postData += sloton;
            postData += "ctl00%24main%24btnBook=Add+Selected+Booking&__EVENTTARGET=&__EVENTARGUMENT=&__LASTFOCUS=&__VIEWSTATE=" + URLEncoder.encode(this.viewState,"UTF-8") + "&__EVENTVALIDATION="+ URLEncoder.encode(this.stateValidation,"UTF-8");
            webpage = grabber.grab(postData);
            
                checkError();
            getParameters(0);
            grabber.flag = true;
            postData = assemble(tel);
            return grabber.grab(postData);
        }
    catch (BookFailedException e)
        {
                  throw new BookFailedException(e.getMessage());
        }
        catch (UnsupportedEncodingException | NumberFormatException e) {} 
        return null;
    }
    public String assemble(String target, String year, String month, String day, String lib, String sh, String sm, String lh, String lm, String fac)
    {
        try
        {
            String postData = "";
            getParameters();
            postData += "ctl00_main_ToolkitScriptManager1_HiddenField=&";
            postData += "__EVENTTARGET=" + URLEncoder.encode(target,"UTF-8") + "&";
            postData += "__EVENTARGUMENT=&__LASTFOCUS=&__VIEWSTATE=" + URLEncoder.encode(this.viewState,"UTF-8") + "&";
            postData += "__EVENTVALIDATION="+ URLEncoder.encode(this.stateValidation,"UTF-8") + "&";
            postData += "ctl00%24main%24ddlYear=" + year + "&";
            postData += "ctl00%24main%24ddlMonth=" + month + "&";
            postData += "ctl00%24main%24ddlDay=" + day + "&";
            if (lib == null)
                postData += "ctl00%24main%24cbAdvSearch=on&ctl00%24main%24ddlLibrary="; 
            else
            {
                postData += "ctl00%24main%24cbAdvSearch=on&";
                postData += "ctl00%24main%24ddlStartHour=" + sh + "&";
                postData += "ctl00%24main%24ddlStartMinute=" + sm + "&";
                postData += "ctl00%24main%24ddlEndHour=" + lh + "&";
                postData += "ctl00%24main%24ddlEndMinute=" + lm + "&";
                postData += "ctl00%24main%24ddlLibrary=2"; 
            }
            if (fac != null)
                postData += "&ctl00%24main%24ddlFacilityType=26";
 
            return postData;
        }
        catch (UnsupportedEncodingException e) {}
        return null;
    }
    public String getViewState()
    {
        return this.viewState;
    }
    public String getValidation()
    {
        return this.stateValidation;
    }
    
}
