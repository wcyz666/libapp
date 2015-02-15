package com.example.libapp;

public class itemList {
	public int number;
	public item[] item = new item[200];
	public itemList()
	{
		for(int i = 0; i<200; i++) {
			item[i]=new item();
		}
	}
	public void setData()
	{
		//books:
		item[0].setData(0, "New Additions(GF)", 7,12 );
		item[1].setData(4, "New Additions(4F)", 10.8, 15.5);
		item[2].setData(0, "General Education Collection", 9.5, 13);
		item[3].setData(0, "CUHK Repository", 13.5, 14.5);
		item[4].setData(0, "Open Reserve", 4, 6);
		item[5].setData(4, "UL Law Book(4F)", 13, 15);
		item[6].setData(4, "UL Law Book(4F)", 8, 17.5);
		item[7].setData(4, "UL Law Book(4F)", 6.5, 16.5);
		item[8].setData(3, "UL Law Book(3F)", 12.5, 17);
		item[9].setData(3, "Special Collection", 1.8, 8.5);
		item[10].setData(3, "Special Collection", 8.3, 16);
		item[11].setData(3, "Rare Book", 6.5, 16);
		item[12].setData(3, "Chinese Overseas Collection", 1.5, 3);
		item[13].setData(2, "Current Periodical", 7, 12);
		item[14].setData(2, "Current Periodical", 13, 12);
		item[15].setData(1, "Reference Collection", 6.1, 2);
		item[16].setData(1, "Government Document", 5.6, 2);
		item[17].setData(1, "Hong Kong Studies", 8, 15);
		item[18].setData(1, "Hong Kong Literature", 13, 18.5);
		item[19].setData(0, "CUHK Theses", 13.5, 16);
		
		//Bubble Group Study Room:
		item[20].setData(5, "Bubble Group Study Room 1", 6.8, 12.8);
		item[21].setData(5, "Bubble Group Study Room 2", 7.2, 12.8);
		item[22].setData(5, "Bubble Group Study Room 3", 4.3, 12.3);
		item[23].setData(5, "Bubble Group Study Room 4", 1, 12.7);
		item[24].setData(5, "Bubble Group Study Room 5", 3, 10.1);
		item[25].setData(5, "Bubble Group Study Room 6", 2.6, 10);
		item[26].setData(5, "Bubble Group Study Room 7", 5.2, 4.5);

		item[27].setData(0, "Circulation Counter", 7.2, 4);

		//PhotoCopier:		
		item[28].setData(0, "Photocopier(GF)", 0, 0);
		//0f:
		item[28].setData(0, "Photocopier(GF)", 5, 7);
		item[29].setData(0, "Photocopier(GF)", 11.1, 16);
		//1f:
		item[30].setData(1, "Photocopier(1F)", 5.8, 13.5);
		item[31].setData(1, "Photocopier(1F)", 5, 13.8);
		//2f:
		item[32].setData(2, "Photocopier(2F)", 9.3, 6.5);
		item[33].setData(2, "Photocopier(2F)", 1, 7.2);
		//3f:
		item[34].setData(3, "Photocopier(3F)", 9.3, 6.8);
		item[35].setData(3, "Photocopier(3F)", 3, 10.8);
		//4f:
		item[36].setData(4, "Photocopier(4F)", 9.1, 6.0);

		//Exhibition 
		item[37].setData(0, "Exhibition Area", 3, 12);

		//Faculty Study Room
		item[38].setData(3, "Faculty Study Room(3F)", 4.9, 2);
		item[39].setData(3, "Faculty Study Room(3F)", 4.9, 8);
		item[40].setData(3, "Faculty Study Room(3F)", 14.5,0.8);
		item[41].setData(3, "Faculty Study Room(4F)", 4.9, 2);
		item[42].setData(3, "Faculty Study Room(4F)", 4.9, 8);
		item[43].setData(3, "Faculty Study Room(4F)", 14.5,0.8);
		item[44].setData(3, "Faculty Study Room(4F)", 14.5,10.5);	
		
		//Gentlemen's room
		//Ladies' room
		item[45].setData(0, "Gentlemen's Room(GF)", 0.7,7.9);	
		item[46].setData(0, "Gentlemen's Room(GF)", 0.7,15.3);	
		item[47].setData(0, "Gentlemen's Room(GF)", 10.5,15);	
		item[48].setData(0, "Gentlemen's Room(GF)", 11.5,6.4);	
		item[49].setData(0, "Ladies' Room(GF)", 0.7,7.1);	
		item[50].setData(0, "Ladies' Room(GF)", 0.7,14.3);	
		item[51].setData(0, "Ladies' Room(GF)", 11.5,6.4);	
		item[52].setData(0, "Ladies' Room(GF)", 9.5,15);	

		item[53].setData(4, "Gentlemen's Room(4F)", 10.5,6.7);	
		item[54].setData(4, "Gentlemen's Room(4F)", 1,15.3);	
		item[55].setData(4, "Gentlemen's Room(4F)", 9.8,15);		
		item[56].setData(4, "Ladies' Room(4F)", 8.5,6.7);	
		item[57].setData(4, "Ladies' Room(4F)", 1,14.3);	
		item[58].setData(4, "Ladies' Room(4F)", 9.0,15);	

		item[59].setData(1, "Gentlemen's Room(1F)", 11.5,6.3);	
		item[60].setData(1, "Gentlemen's Room(1F)", 9.5,15.2);	
		item[61].setData(1, "Gentlemen's Room(1F)", 0.8,15.4);		
		item[62].setData(1, "Ladies' Room(1F)", 8.6,6.3);	
		item[63].setData(1, "Ladies' Room(1F)", 9.5,15.2);	
		item[64].setData(1, "Ladies' Room(1F)", 0.8,14.2);	


		item[65].setData(5, "Gentlemen's Room(LG)", 12.6,12.9);	
		item[66].setData(5, "Ladies' Room(LG)", 12.9,6.5);	

		item[67].setData(2, "Gentlemen's Room(2F)", 1.1,15.4);	
		item[68].setData(2, "Gentlemen's Room(2F)", 10.5,15.3);	
		item[69].setData(2, "Ladies' Room(2F)", 1.1, 14.4);	
		item[70].setData(2, "Ladies' Room(2F)", 9.5,15.3);

		item[71].setData(3, "Gentlemen's Room(3F)", 11.2,7.3);	
		item[72].setData(3, "Gentlemen's Room(3F)", 10.1,15.1);	
		item[73].setData(3, "Ladies' Room(3F)", 8.5,7.3);	
		item[74].setData(3, "Ladies' Room(3F)", 9.2,15.1);	

		//computers
		item[75].setData(0, "Computers(GF)", 5,9.3);	
		item[76].setData(0, "Computers(GF)", 3.6,19.3);	
		item[77].setData(4, "Computers(4F)", 1.7,7.3);	
		item[78].setData(1, "Computers(1F)", 9,8.5);	
		item[79].setData(1, "Computers(1F)", 6.5,11.3);	
		item[80].setData(5, "Computers(LG)", 5.5,3);	
		item[81].setData(3, "Computers(3F)", 2.8,13);

		//group study rooms
		item[82].setData(0, "Group Study Room 8", 11.4, 8);	
		item[83].setData(0, "Group Study Room 9", 11.4,9.5);
		item[84].setData(4, "Group Study Room 1", 8.4,13.8);
		item[85].setData(4, "Group Study Room 2", 11.1,13.8);
		item[86].setData(1, "Group Study Room 18", 1.1,9.6);
		item[87].setData(1, "Group Study Room 17", 0.6,10.4);
		item[88].setData(1, "Group Study Room 16", 1.7,10.4);
		item[89].setData(1, "Group Study Room 15", 0.6,12.3);
		item[90].setData(1, "Group Study Room 14", 1.7,12.3);
		item[91].setData(1, "Group Study Room 13", 0.6,12.7);
		item[92].setData(1, "Group Study Room 12", 1.7,12.7);
		item[93].setData(1, "Group Study Room 11", 0.6,13.3);
		item[94].setData(1, "Group Study Room 10", 1.7,13.3);

		//multipurpose room
		item[95].setData(5, "Multipurpose Room 2", 5.5,1.6);
		item[96].setData(1, "Multipurpose Room 1", 1.1,8);

		//printers
		item[97].setData(1, "Printers(1F)", 5.4,10.1);
		item[98].setData(1, "Printers(1F)", 5.4,11.9);
		item[99].setData(1, "Printers(1F)", 1.7,13.3);
		item[100].setData(1, "Printers(1F)", 7,6.6);
		item[101].setData(1, "Printers(1F)", 7.4,6.9);
		item[102].setData(1, "Printers(1F)", 8,6.9);
		item[103].setData(1, "Printers(1F)", 8.6,6.9);
		item[104].setData(1, "Printers(1F)", 6.3,9.3);
		item[105].setData(5, "Printers(LG)", 6.5,0.1);
		item[106].setData(5, "Printers(LG)", 4.3,1.6);
		item[107].setData(5, "Printers(LG)", 7.1,0.6);
		item[108].setData(5, "Printers(LG)", 6,5.4);
		item[109].setData(4, "Printers(4F)", 11.1,19.3);
		item[110].setData(4, "Printers(4F)", 11.4,19.3);

		//research consultant room
		item[111].setData(1, "Research Consultation Room", 5.8,6.2);

		//staircase
		item[112].setData(0, "Staircase(GF)", 1,5.5);
		item[113].setData(0, "Staircase(GF)", 1.2,16.2);
		item[114].setData(0, "Staircase(GF)", 4,1);
		item[115].setData(0, "Staircase(GF)", 8,5.5);
		item[116].setData(0, "Staircase(GF)", 10,13.6);
		item[117].setData(4, "Staircase(4F)", 1,4.5);
		item[118].setData(4, "Staircase(4F)", 3.8,6.5);
		item[119].setData(4, "Staircase(4F)", 8,6);
		item[120].setData(4, "Staircase(4F)", 11.5,6.5);
		item[121].setData(4, "Staircase(4F)", 4.7,14.1);
		item[122].setData(4, "Staircase(4F)", 9.8,13.6);
		item[123].setData(4, "Staircase(4F)", 9.8,17.8);
		item[124].setData(1, "Staircase(1F)", 0.6,4.5);
		item[125].setData(1, "Staircase(1F)", 4.5,1);
		item[126].setData(1, "Staircase(1F)", 1,16.3);
		item[127].setData(1, "Staircase(1F)", 8,5.5);
		item[128].setData(1, "Staircase(1F)", 11.5,6.3);
		item[129].setData(1, "Staircase(1F)", 8.3,11);
		item[130].setData(1, "Staircase(1F)", 10,13.7);
		item[131].setData(1, "Staircase(1F)", 10,18);
		item[132].setData(5, "Staircase(LG)", 8.2,7.4);
		item[133].setData(5, "Staircase(LG)", 9.3,11);
		item[134].setData(5, "Staircase(LG)", 12.2,9);
		item[135].setData(2, "Staircase(2F)", 8,5.7);
		item[136].setData(2, "Staircase(2F)", 12,5.7);
		item[137].setData(2, "Staircase(2F)", 10,13.8);
		item[138].setData(2, "Staircase(2F)", 10,17.8);
		item[139].setData(3, "Staircase(3F)", 0.9,5);
		item[140].setData(3, "Staircase(3F)", 0.9,16.3);
		item[141].setData(3, "Staircase(3F)", 3.8,6.5);
		item[142].setData(3, "Staircase(3F)", 3.8,9);
		item[143].setData(3, "Staircase(3F)", 10,13.8);
		item[144].setData(3, "Staircase(3F)", 10,18);
		item[145].setData(3, "Staircase(3F)", 8,6);
		item[146].setData(3, "Staircase(3F)", 12.5,6.5);

		//lift
		item[147].setData(0, "Lift(GF)", 1.5,3.8);
		item[148].setData(0, "Lift(GF)", 1.5,3.9);
		item[149].setData(0, "Lift(GF)", 11.5,5.5);
		item[150].setData(0, "Lift(GF)", 10.5,16.2);
		item[151].setData(1, "Lift(1F)", 1.5,3.8);
		item[152].setData(1, "Lift(1F)", 1.5,3.9);
		item[153].setData(1, "Lift(1F)", 11.5,5.5);
		item[154].setData(1, "Lift(1F)", 10.5,16.2);
		item[155].setData(2, "Lift(2F)", 1.8,4.3);
		item[156].setData(2, "Lift(2F)", 1.8,5.2);
		item[157].setData(2, "Lift(2F)", 12,5.7);
		item[158].setData(2, "Lift(2F)", 10.4,16.4);
		item[159].setData(3, "Lift(3F)", 1.8,4.3);
		item[160].setData(3, "Lift(3F)", 1.8,5.2);
		item[161].setData(3, "Lift(3F)", 12,5.7);
		item[162].setData(3, "Lift(3F)", 10.4,16.4);
		item[163].setData(4, "Lift(4F)", 1.8,4.3);
		item[164].setData(4, "Lift(4F)", 1.8,5.2);
		item[165].setData(4, "Lift(4F)", 12,5.7);
		item[166].setData(4, "Lift(4F)", 10.4,16.4);
		item[167].setData(5, "Lift(4F)", 12.2,6.5);
		item[168].setData(5, "Lift(4F)", 11.4,13);

		//self-check out Machine
		item[169].setData(0, "Self-Checkout Machine", 5,5.5);
		item[170].setData(0, "Self-Checkout Machine", 8,7.8);
		item[171].setData(0, "Self-Checkout Machine", 8.1,11.4);

		//Doctoral Study Room
		item[172].setData(1, "Doctoral Study Room" , 5.7, 16.5);

		item[173].setData(2,"Computers(2F)", 1.8, 7.2);

		item[174].setData(4, "Group Study Room 3", 14.2, 15.2);
		item[175].setData(4, "Group Study Room 4", 10.3, 19);
		item[176].setData(4, "Group Study Room 5", 9.3, 19);
		item[177].setData(4, "Group Study Room 6", 8.3, 19);
		item[178].setData(3, "Group Study Room 7", 12.8, 19);

		number=179;
	}
}