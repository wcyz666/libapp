package com.example.libapp;

import java.net.URL;

public class BookDetailParser {
    private BookPageGrabber grabber;
    private NormalBook book;
    private URL url;
    
    public BookDetailParser(){}
    
    public BookDetailParser(Book book)
    {
        grabber = new BookPageGrabber();
        grabber.setURL(book.getBookURL());
        this.book = (NormalBook)book;
    }
    public void getHTML(String head1, String tail1, String head2, String tail2, String head3, String tail3, String head4, String tail4)
    {
        String webpage;
        int pos = 0, pos1 = 0, pos2 = 0;
        this.book.bookNumber = "NOT AVAILABLE";
        this.book.state = "NOT AVAILABLE";
        this.book.press = "NOT AVAILABLE";
        this.book.location = "NOT AVAILABLE";
        try
        {         
            webpage = grabber.grab();
            pos = webpage.indexOf(head4, pos) + head4.length();
            if (pos > 0)
            {
                while (webpage.charAt(pos) < 'A' || webpage.charAt(pos) > 'Z')
                    pos++;
                pos1 = webpage.indexOf(tail4, pos);
                if (pos1 - pos <= 30)
                     this.book.location = webpage.substring(pos, pos1).replace('\n', '\0');     
            }
            pos = webpage.indexOf(head1, pos) + head1.length();
            if (pos > 0)
            {
                while (webpage.charAt(pos) < 'A' || webpage.charAt(pos) > 'Z')
                    pos++;
                pos1 = webpage.indexOf(tail1, pos);
                if (pos1 - pos <= 40)
                     this.book.bookNumber = webpage.substring(pos, pos1);     
            }
            pos = webpage.indexOf(head2, pos) + head2.length();
            if (pos > 0)
            {
                while (webpage.charAt(pos) < 'A' || webpage.charAt(pos) > 'Z')
                    pos++;
                pos1 = webpage.indexOf(tail2, pos);
                if (pos1 - pos <= 30)
                    this.book.state = webpage.substring(pos, pos1);
            }
            pos = webpage.indexOf(head3, pos) + head3.length();
            if (pos > 0)
            {
                while (webpage.charAt(pos) < 'A' || webpage.charAt(pos) > 'Z')
                    pos++;
                pos1 = webpage.indexOf(tail3, pos);
                if (pos1 - pos <= 60)
                    this.book.press = webpage.substring(pos, pos1);
            }
           
        }
        catch (Exception e) {}

    }
    public NormalBook getDetail()
    {
        return this.book;
    }
}
