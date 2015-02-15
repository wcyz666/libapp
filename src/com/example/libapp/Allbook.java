package com.example.libapp;


public class Allbook {
	public int booknum;
	public Bookcase[] bc = new Bookcase[100];	
	public Allbook()
	{
		for(int i = 0; i<100; i++) {
			bc[i]=new Bookcase();
		}
	}
	
	public void setData()
	{
		booknum = 19;
		//bc[0].setData(0, "start", "end", 0, 0);
		bc[0].setData(2, "DS", "DS", 12.5, 8.5);
		bc[1].setData(2, "DS", "DK", 8.5, 8.5);
		bc[2].setData(2, "E", "G", 6.5, 8.5);
		bc[3].setData(2, "HD", "HE", 12.5, 17.5);
		bc[4].setData(2, "H", "HC", 7, 17.5);
		bc[5].setData(2, "DG", "DS", 1.5, 3);
		bc[6].setData(2, "D", "DG", 1.5, 9);
		bc[7].setData(2, "BF", "CT", 1.5, 12);
		bc[8].setData(2, "B", "BF", 1.5, 18);
		bc[9].setData(3, "HF", "HZ", 8.5, 2.9);
		bc[10].setData(3, "J", "J", 13, 2.2);
		bc[11].setData(3, "PN", "PZ", 7.5, 9);
		bc[12].setData(3, "P", "PN", 12.5, 9);
		bc[14].setData(4, "QB", "QZ", 8, 3);
		bc[15].setData(4, "S", "T", 12.5, 3);
		bc[16].setData(4, "U", "Z", 12.5, 9);
		bc[17].setData(4, "QA", "QA", 1.5, 3);
		bc[18].setData(4, "QA", "QA", 1.5, 10.5);
		bc[19].setData(4, "Q", "Q", 1.5, 18);
	}

}
