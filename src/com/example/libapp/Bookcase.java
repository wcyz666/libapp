package com.example.libapp;


public class Bookcase {
	public int floor;
	public String start;
	public String end;
	public double x;
	public double y;
	public Bookcase()
	{
		floor=0;
		start="";
		end="";
		x=0;
		y=0;
	}
	public void setData(int f, String str1, String str2,double heng, double zong)
	{
		floor=f;
		start=str1;
		end=str2;
		x=heng;
		y=zong;
	}
}