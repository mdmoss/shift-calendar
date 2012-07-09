package com.projects.shiftcalendar;

import java.util.Date;

public class Day {

	Date date;
	int shiftId;
	String shiftSymbol;
	int shiftColor;

	public Day(int year, int month, int day, int shiftId, String shiftSymbol) {

		date = new Date();
		date.setYear(year - 1900);
		date.setMonth(month);
		date.setDate(day);
		this.shiftId = shiftId;
		this.shiftSymbol = shiftSymbol;
	}

	public Day() {

		date = new Date();
	}
}