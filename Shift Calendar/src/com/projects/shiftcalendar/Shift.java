package com.projects.shiftcalendar;

import android.text.format.Time;

public class Shift {

	public int id;

	public String name;
	public String symbol;
	public int color = 0xFFFFFFFF;

	public Time startTime;
	public Time endTime;
	public String comments;

	public Shift() {

		this.startTime = new Time();
		this.endTime = new Time();
	}

	@Override
	public String toString() {

		return this.name;
	}
}