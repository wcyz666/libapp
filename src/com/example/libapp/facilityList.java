package com.example.libapp;


import java.util.Arrays;

public class facilityList {
	public String[] name={"Book","Bubble Group Study Room","Circulation Counter",
			"Computers","Exhibition Area","Faculty Study Room","Gentlemen's Room",
			"Group Study Room","Ladies' Room","Multipurpose Room","Printers",
			"Research Consultation Room","Staircase","Lift","Self-Checkout Machine", "Photocopier", "Doctoral Study Room"};
	public int number;
	public facilityList()
	{
		Arrays.sort(name);
		number=name.length;
	}
}
