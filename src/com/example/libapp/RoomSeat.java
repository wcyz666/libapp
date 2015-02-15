package com.example.libapp;

import java.util.HashMap;
import java.util.Map;

public class RoomSeat {
	private static Map<String, String> seatMap = new HashMap<>();
	static{
		seatMap.put("Bubble Group Study Room 1 - LG/F"," (4 seats)");	
		seatMap.put("Bubble Group Study Room 2 - LG/F"," (4 seats)");
		seatMap.put("Bubble Group Study Room 3 - LG/F"," (6 seats)");
		seatMap.put("Bubble Group Study Room 4 - LG/F"," (6 seats)");
		seatMap.put("Bubble Group Study Room 5 - LG/F"," (4 seats)");	
		seatMap.put("Bubble Group Study Room 6 - LG/F"," (4 seats)");
		seatMap.put("Bubble Group Study Room 7 - LG/F"," (6 seats)");
		seatMap.put("Group Study Room 8 - G/F"," (6 seats)");
		seatMap.put("Group Study Room 9 - G/F"," (6 seats)");
		seatMap.put("Group Study Room 10 - 1/F"," (4 seats)");	
		seatMap.put("Group Study Room 11 - 1/F"," (6 seats)");	
		seatMap.put("Group Study Room 12 - 1/F"," (4 seats)");
		seatMap.put("Group Study Room 13 - 1/F"," (4 seats)");	
		seatMap.put("Group Study Room 14 - 1/F"," (4 seats)");	
		seatMap.put("Group Study Room 15 - 1/F"," (4 seats)");	
		seatMap.put("Group Study Room 16 - 1/F"," (6 seats)");	
		seatMap.put("Group Study Room 17 - 1/F"," (6 seats)");
		seatMap.put("Group Study Room 18 - 1/F"," (12 seats)");	
		seatMap.put("Group Study Room 19 - 4/F"," (8 seats)");						
	}
	public static String querySeat(String key){
		return seatMap.get(key);
	}
}
