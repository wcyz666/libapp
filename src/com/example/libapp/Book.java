package com.example.libapp;

import java.net.URL;

import android.graphics.Bitmap;

public class Book {
    protected String title;
    protected String author;
    protected URL bookurl;
    protected URL imgurl;
    private Bitmap img = null;
    protected URL commenturl;
    protected String type; 
    
    public Book(String author, String title, String bookurl, String imgurl, String commenturl)
    {
        this.author = author;
        this.title = title;
        try
        {
            this.bookurl = new URL(bookurl);
            this.imgurl = new URL(imgurl);
            this.commenturl = new URL(commenturl);
        }
        catch (Exception e) { }
    }
    public String getAuthor()
    {
        return author;
    }
    public String getTitle()
    {
        return title;
    }
    public URL getBookURL()
    {
        return this.bookurl;
    }
    public URL getimgURL()
    {
       return this.imgurl;
    }
    public URL getcommentURL()
    {
       return this.commenturl;
    }
    public void setBitmap(Bitmap img) {
    	this.img = img;
    }
    public Bitmap getBitmap() {
    	return this.img;
    }
}
