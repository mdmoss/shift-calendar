package com.projects.shiftcalendar;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ViewMonth extends Activity {
	
	private static final String lastRunVersion = "Version";
	
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
		
		// Check for version first run
		SharedPreferences sp = getPreferences(MODE_PRIVATE);
		if (sp.getFloat(lastRunVersion, 0) == 0) {
			
			// Store the latest run version
			SharedPreferences.Editor edit = sp.edit();
			edit.putFloat(lastRunVersion, (float) 1.1);
			edit.commit();
			
			Toast.makeText(this, "Welcome to Shift Calendar. Select \"Change Shifts\" from the menu to add shifts to the calendar.", Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.month_view_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent i = new Intent ();
		ComponentName actName;
		
		switch (item.getItemId()) {
		case (R.id.menu_edit_shifts):
			actName = new ComponentName("com.projects.shiftcalendar", "com.projects.shiftcalendar.AssignShifts");
			i.setComponent(actName);
			startActivity(i);
			break;
		case (R.id.menu_modify_preferences):
			actName = new ComponentName("com.projects.shiftcalendar", "com.projects.shiftcalendar.ModifyPreferences");
			i.setComponent(actName);
			startActivity(i);
		}
		return true;
	}
	
	@Override
	public void onResume() {
		
		super.onResume();
		CalendarView cv = (CalendarView) findViewById(R.id.view_month_calendar);
		cv.redrawCalendar();
		
	}
}