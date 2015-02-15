package com.example.libapp;


public class floorsize {
	public double[] y= new double[6];
	public double[] x= new double[6];
	public floorsize()
	{
		for (int i=0;i<6;i++)
		{
			y[i]=0;
			x[i]=0;
		}
	}
	public void setData()
	{
		y[0]=19.6;
		x[0]=14.9;
		y[1]=19.6;
		x[1]=14.9;
		y[2]=19.6;
		x[2]=14.9;
		y[3]=19.6;
		x[3]=14.9;
		y[4]=19.6;
		x[4]=14.9;
		y[5]=14;
		x[5]=15.1;		
	}

}
