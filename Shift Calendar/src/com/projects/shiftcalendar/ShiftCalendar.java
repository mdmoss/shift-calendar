package com.projects.shiftcalendar;

import android.app.Application;

public class ShiftCalendar extends Application {
	
	private ShiftCalDB db = null;
	
	@Override
	public void onCreate() {
		
		super.onCreate();
		db = new ShiftCalDB(getApplicationContext());
	}
	
	public ShiftCalDB getDB () {
		
		return db;
	}
}