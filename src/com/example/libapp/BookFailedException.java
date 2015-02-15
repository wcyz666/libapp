package com.example.libapp;


/**
 *
 * @author lenovo
 */
public class BookFailedException extends Exception{
    public BookFailedException() 
    {
        super();
    }
    public BookFailedException(String msg) 
    {
        super(msg);
    }
}
