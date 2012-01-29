package com.projects.shiftcalendar;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ShiftCalendar extends Activity {
	
	private OnClickListener manageShiftsButtonClick = new OnClickListener () {

		public void onClick(View v) {
			
			// Change to the manage shifts activity
			Intent i = new Intent ();
			ComponentName actName = new ComponentName("com.projects.shiftcalendar", "com.projects.shiftcalendar.ManageShifts");
			i.setComponent(actName);
			startActivity (i);
		}
	};
	
	private OnClickListener assignShiftsButtonClick = new OnClickListener () {

		public void onClick(View v) {
			
			// Change to the manage shifts activity
			Intent i = new Intent ();
			ComponentName actName = new ComponentName("com.projects.shiftcalendar", "com.projects.shiftcalendar.AssignShifts");
			i.setComponent(actName);
			startActivity (i);
		}
	};
	
	private OnClickListener viewMonthButtonClick = new OnClickListener () {

		public void onClick(View v) {
			
			// Change to the manage shifts activity
			Intent i = new Intent ();
			ComponentName actName = new ComponentName("com.projects.shiftcalendar", "com.projects.shiftcalendar.ViewMonth");
			i.setComponent(actName);
			startActivity (i);
		}
	};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		// Grab the three buttons
		
		View manageShiftsButton = findViewById(R.id.home_manage_shifts);
		manageShiftsButton.setOnClickListener(manageShiftsButtonClick);
		
		View assignShiftsButton = findViewById(R.id.home_assign_shifts);
		assignShiftsButton.setOnClickListener(assignShiftsButtonClick);
		
		View viewMonthButton = findViewById(R.id.home_view_month);
		viewMonthButton.setOnClickListener(viewMonthButtonClick);
		
		// Check for first run
		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		if (settings.getBoolean("firstRun", true)) {
			
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("firstRun", false);
			editor.commit();
			
			Toast.makeText(getApplicationContext(), R.string.first_run_help_toast, Toast.LENGTH_LONG).show();
		}
	}
}
