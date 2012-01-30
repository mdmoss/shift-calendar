package com.projects.shiftcalendar;

import android.app.Application;

public class ShiftCalendar extends Application {
	
	private ShiftCalDB db = null;
	
	public ShiftCalDB getDB () {
		
		if (db == null) {
			db = new ShiftCalDB(getApplicationContext());
		}
		return db;
	}
	
}