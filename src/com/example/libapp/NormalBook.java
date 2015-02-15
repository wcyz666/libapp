package com.example.libapp;


public class NormalBook extends Book{
    
    protected String bookNumber;
    protected int area;
    protected int stateID;
    protected String state;
    protected String press;
    protected String edition;
    protected String location;
    
    public NormalBook(String author, String title, String bookNumber, String state, String press, String bookurl, String imgurl, String commenturl, String edition)
    {
        super(author, title, bookurl, imgurl, commenturl);
        this.bookNumber = bookNumber;
        this.press = press;
        this.type = "NormalBook";       
        this.state = state;
        this.edition = edition;
        if(state == "NOT CHECKED OUT") {
            this.stateID = 0;
        } else {
            this.stateID = 1;
        }
    }
    
    
    public String getBookNumber()
    {
        return this.bookNumber;
    }
    public int getArea()
    {
        return this.area;
    }
    public void setArea(int s)
    {
        this.area = s;
    }
    public String getStatus()
    {
        return this.state;
    }
    public int getStatusID()
    {
        return this.stateID;
    }
    public String getPress()
    {
        return this.press;
    }
    public String getEdition()
    {
        return this.edition;
    }
    public String getLocation()
    {
        return this.location;
    }
}

