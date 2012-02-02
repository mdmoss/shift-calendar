package com.projects.shiftcalendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewMonth extends Activity {
	
	private OnClickListener monthRight = new OnClickListener() {

		public void onClick(View v) {
			
			CalendarView cv = (CalendarView) findViewById(R.id.view_month_calendar);
			cv.increaseMonth();
			
		}
	};
	
	private OnClickListener monthLeft = new OnClickListener() {

		public void onClick(View v) {
			
			CalendarView cv = (CalendarView) findViewById(R.id.view_month_calendar);
			cv.decreaseMonth();
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_month);
		
/*		View monthBarRight = findViewById(R.id.month_bar_right);
		View weekBarMon = findViewById(R.id.day_bar_mon);
		monthBarRight.setOnClickListener(monthRight);
		weekBarMon.setOnClickListener(monthLeft);
		
		View monthBarLeft = findViewById(R.id.month_bar_left);
		View weekBarSun = findViewById(R.id.day_bar_sun);
		monthBarLeft.setOnClickListener(monthLeft);
		weekBarSun.setOnClickListener(monthRight);*/

	}
	
}