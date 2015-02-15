package com.example.libapp;


import java.util.Arrays;

public class bookList {
	public String[] name={"New Additions(GF)", "New Additions(4F)", "General Education Collection", "CUHK Repository", "CUHK Theses", "Open Reserve", "UL Law Book(3F)", "UL Law Book(4F)", "Special Collection", "Rare Book", "Chinese Overseas Collection", "Current Periodical", "Reference Collection", "Government Document", "Hong Kong Studies", "Hong Kong Literature"};
	public int number;
	public bookList()
	{
		Arrays.sort(name);
		number=name.length;
	}
}
