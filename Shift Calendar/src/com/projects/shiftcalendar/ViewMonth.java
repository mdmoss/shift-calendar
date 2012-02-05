package com.projects.shiftcalendar;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
		
		View monthBarRight = findViewById(R.id.month_bar_right);
		monthBarRight.setOnClickListener(monthRight);
		
		View monthBarLeft = findViewById(R.id.month_bar_left);
		monthBarLeft.setOnClickListener(monthLeft);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.month_view_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case (R.id.menu_assign_shifts):
			Intent i = new Intent ();
			ComponentName actName = new ComponentName("com.projects.shiftcalendar", "com.projects.shiftcalendar.AssignShifts");
			i.setComponent(actName);
			
			CalendarView cv = (CalendarView) findViewById(R.id.view_month_calendar);
			i.putExtra(AssignShifts.intentMonthField, cv.getMonth());
			i.putExtra(AssignShifts.intentYearField, cv.getYear());
			startActivity(i);
		
		}
		return true;
	}
}