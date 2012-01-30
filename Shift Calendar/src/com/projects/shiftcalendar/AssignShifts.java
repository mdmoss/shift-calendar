package com.projects.shiftcalendar;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AssignShifts extends Activity {
	
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
				
				Spinner shiftSelector = (Spinner) findViewById(R.id.assign_shifts_shift_selector);
				Shift selected = (Shift) shiftSelector.getSelectedItem();
				
				if (selected != null) {
				
					if (selected.id != dV.shiftId) {
						// A new type of shift is selected, update UI
						dV.setLabelText(selected.symbol);
						dV.setLabelColor(selected.color);
						dV.shiftId = selected.id;
						
					} else if (selected.id == dV.shiftId){
						// Same shift is selected. Clear square
						dV.setLabelText("");
						dV.shiftId = DayView.NO_SHIFT;
	
					}
					
					// Here's where the ASYNC will be, eventually.
					
					CalendarView cv = (CalendarView) findViewById(R.id.assign_shifts_calendar);
					
					ShiftCalDB db = ((ShiftCalendar) getApplication()).getDB();
					Date focus = new Date (cv.year, cv.month, date);
					Shift sh = db.getShiftByDate(focus);
					
					if (selected != null) {
						
						// UI update first
	
						Day newDay = new Day(cv.year, cv.month, date, selected.id, selected.symbol);
						
						if (sh == null) {
							db.clearDay(focus);
							db.setDayShift (newDay);
						
						} else if (sh.id != selected.id) {
							db.clearDay(focus);
							db.setDayShift (newDay);
							
						} else {
							// Shifts are the same
							db.clearDay(focus);
						}
						
					}
				} else {
					
					Toast.makeText(getApplicationContext(), "You havn't set any shift types yet. Try the 'Manage Shift Types' button on the main menu", Toast.LENGTH_LONG).show();
					
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
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// Chill, bro
			
		}
	};
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.assign_shifts);
		
		View monthBarRight = findViewById(R.id.month_bar_right);
		View weekBarMon = findViewById(R.id.day_bar_mon);
		monthBarRight.setOnClickListener(monthRight);
		weekBarMon.setOnClickListener(monthLeft);
		
		View monthBarLeft = findViewById(R.id.month_bar_left);
		View weekBarSun = findViewById(R.id.day_bar_sun);
		monthBarLeft.setOnClickListener(monthLeft);
		weekBarSun.setOnClickListener(monthRight);
		
		Spinner shiftSelector = (Spinner) findViewById(R.id.assign_shifts_shift_selector);
		
		ShiftCalDB db = new ShiftCalDB(getApplicationContext());
		List<Shift> shifts = db.getAllShifts();
		ArrayAdapter<Shift> adapter = new ArrayAdapter<Shift> (getApplicationContext(), android.R.layout.simple_spinner_item, shifts);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		shiftSelector.setAdapter(adapter);
		shiftSelector.setOnItemSelectedListener(new ShiftSelectionListener());
		
		CalendarView cv = (CalendarView) findViewById(R.id.assign_shifts_calendar);
		DateSquare dS = cv.ds;
		List <DayView> allDays = dS.getAllSquares();
		Iterator<DayView> iterDays = allDays.iterator();
		while (iterDays.hasNext()){
			
			iterDays.next().setOnClickListener(DayPressListener);
		}
		
	}
}