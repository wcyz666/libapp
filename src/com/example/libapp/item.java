package com.example.libapp;


public class item {

	public int floor;
	public String name;
	public double x;
	public double y;
	public item()
	{
		floor=0;
		name="";
		x=0;
		y=0;
	}
	public void setData(int f, String str,double heng, double zong)
	{
		floor=f;
		name = str;
		x=heng;
		y=zong;
	}
}