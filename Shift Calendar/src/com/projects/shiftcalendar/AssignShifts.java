package com.projects.shiftcalendar;
/**
 * @author Matthew Moss
 *
 */
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AssignShifts extends Activity {

	ShiftCalDB db;

	private static final String lastRunVersion = "Version";

	static final String intentMonthField = "launchMonth";
	static final String intentYearField = "launchYear";
	static final int intentNoMonth = -1;
	
	Shift selectedShift;

	private OnClickListener monthRight = new OnClickListener() {

		public void onClick(View v) {

			CalendarView cv = (CalendarView) findViewById(R.id.assign_shifts_calendar);
			cv.increaseMonth();
		}
	};

	private OnClickListener monthLeft = new OnClickListener() {

		public void onClick(View v) {

			CalendarView cv = (CalendarView) findViewById(R.id.assign_shifts_calendar);
			cv.decreaseMonth();
		}
	};

	private OnClickListener DayPressListener = new OnClickListener() {

		public void onClick(View v) {

			// THere must be a better way
			DayView dV = (DayView) v;

			// If the day is legit ie. actually a square
			int date = dV.getDate();
			if (date != DayView.NO_DATE) {

				if (selectedShift != null) {

					if (selectedShift.id != dV.shiftId) {
						// A new type of shift is selected, update UI
						dV.setLabelText(selectedShift.symbol);
						dV.setLabelColor(selectedShift.color);
						dV.shiftId = selectedShift.id;

					} else if (selectedShift.id == dV.shiftId) {
						// Same shift is selected. Clear square
						dV.setLabelText("");
						dV.shiftId = DayView.NO_SHIFT;

					}

					// Here's where the ASYNC will be, eventually.

					CalendarView cv = (CalendarView) findViewById(R.id.assign_shifts_calendar);

					ShiftCalDB db = ((ShiftCalendar) getApplication()).getDB();

					Date focus = new Date();
					focus.setYear(cv.year - 1900);
					focus.setMonth(cv.month);
					focus.setDate(date);

					Shift sh = db.getShiftByDate(focus);

					Day newDay = new Day(cv.year, cv.month, date,
							selectedShift.id, selectedShift.symbol);

					db.clearDay(focus);

					if (sh == null) {
						db.setDayShift(newDay);

					} else if (sh.id != selectedShift.id) {
						db.setDayShift(newDay);

					}

				} else {

					Toast.makeText(
							getApplicationContext(),
							"You havn't set any shift types yet. Try 'Edit Shift Types' in the menu.",
							Toast.LENGTH_LONG).show();

				}
			}

		}
	};

	private class ShiftSelectionListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			TextView symbolSpot = (TextView) findViewById(R.id.assign_shifts_symbol);

			Shift selected = (Shift) arg0.getItemAtPosition(arg2);

			symbolSpot.setText(selected.symbol);
			symbolSpot.setTextColor(selected.color);
			symbolSpot.setVisibility(View.VISIBLE);

			selectedShift = selected;
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// Chill, bro

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.assign_shifts);

		db = ((ShiftCalendar) getApplication()).getDB();

		View monthBarRight = findViewById(R.id.month_bar_right);
		monthBarRight.setOnClickListener(monthRight);

		View monthBarLeft = findViewById(R.id.month_bar_left);
		monthBarLeft.setOnClickListener(monthLeft);

		Spinner shiftSelector = (Spinner) findViewById(R.id.assign_shifts_shift_selector);
		shiftSelector.setOnItemSelectedListener(new ShiftSelectionListener());

		CalendarView cv = (CalendarView) findViewById(R.id.assign_shifts_calendar);

		Intent i = getIntent();
		int loaderMonth = i.getIntExtra(intentMonthField, intentNoMonth);
		if (loaderMonth != intentNoMonth) {

			int loaderYear = i.getIntExtra(intentYearField, intentNoMonth);
			cv.setCalendar(loaderMonth, loaderYear);
		}

		DateSquare dS = cv.ds;
		List<DayView> allDays = dS.getAllSquares();
		Iterator<DayView> iterDays = allDays.iterator();
		while (iterDays.hasNext()) {

			iterDays.next().setOnClickListener(DayPressListener);
		}

		// Check for version first run
		SharedPreferences sp = getPreferences(MODE_PRIVATE);
		if (sp.getFloat(lastRunVersion, 0) == 0) {

			// Store the latest run version
			SharedPreferences.Editor edit = sp.edit();
			edit.putFloat(lastRunVersion, (float) 1.1);
			edit.commit();

			Toast.makeText(
					this,
					"Select 'Edit Shift Types' from the menu to add new types of shift.",
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public void onResume() {

		super.onResume();

		Spinner shiftSelector = (Spinner) findViewById(R.id.assign_shifts_shift_selector);
		List<Shift> shifts = db.getAllShifts();
		ArrayAdapter<Shift> adapter = new ArrayAdapter<Shift>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				shifts);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		shiftSelector.setAdapter(adapter);
		CalendarView cv = (CalendarView) findViewById(R.id.assign_shifts_calendar);
		cv.redrawCalendar();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.assign_shifts_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent i = new Intent();
		ComponentName actName;

		switch (item.getItemId()) {
		case (R.id.menu_edit_shifts):
			actName = new ComponentName("com.projects.shiftcalendar",
					"com.projects.shiftcalendar.ManageShifts");
			i.setComponent(actName);
			startActivity(i);
			break;
		case (R.id.menu_modify_preferences):
			actName = new ComponentName("com.projects.shiftcalendar",
					"com.projects.shiftcalendar.ModifyPreferences");
			i.setComponent(actName);
			startActivity(i);
			break;
		}
		return true;
	}
}