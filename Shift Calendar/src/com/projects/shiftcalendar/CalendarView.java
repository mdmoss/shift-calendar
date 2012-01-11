package com.projects.shiftcalendar;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalendarView extends RelativeLayout {
	
	int month;
	int year;
	
	DateSquare ds;
	ShiftCalDB db;
	
	public CalendarView (Context context) {
		
		super (context);
		loadCalendar(context);
	}
	
	public CalendarView (Context context, AttributeSet attrs) {
		
		super (context, attrs);
		loadCalendar(context);
	}
	
	public void loadCalendar (Context context) {
		
		View.inflate(context, R.layout.calendar_view, this);
		
		Calendar cal = Calendar.getInstance();
		this.ds = (DateSquare) findViewById(R.id.DateSquare);
		this.db = new ShiftCalDB(context);
		
		this.setCalendar(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
	}
	
	public void redrawCalendar () {
		
		// Here's the magic
		Calendar cal = Calendar.getInstance();
		cal.set (year, month, 1);
		
		// Find the square of the first day of the month by day
		int XCoord = getFirstXCoord(year, month);
		
		// Set the next n squares to ascending numbers
		
		int totalDays = cal.getActualMaximum(Calendar.DATE);
		int daysDrawn = 0;
		
		for (int y = 0; y < 6; y++) {
			
			for (int x = 0; x < 7; x++) {
				
				DayView currentDay = ds.getSquareByPos (x, y);
				
				MarginLayoutParams dayMargins = (MarginLayoutParams) currentDay.getLayoutParams();
				
				if (y == 0 && x < XCoord) {
					currentDay.setDate (DayView.NO_DATE);
					currentDay.setLabelText("");
					dayMargins.setMargins(0, 0, 0, 0);
					
				} else if (daysDrawn < totalDays) {
					currentDay.setDate (daysDrawn + 1);
					daysDrawn++;
					currentDay.setLabelText("");
					dayMargins.setMargins(1, 1, 1, 1);
					
				} else {
					currentDay.setDate (DayView.NO_DATE);
					currentDay.setLabelText("");
					dayMargins.setMargins(0, 0, 0, 0);
				}
				
				currentDay.setLayoutParams(dayMargins);
				
			}
		}
		
		// Update the calendar month bar
		
		TextView monthBar = (TextView) findViewById(R.id.month_bar);
		
		int currentMonth = (cal.get(Calendar.MONTH));
		
		String currentMonthName = "Borken";
		
		switch (currentMonth) {
		
			case Calendar.JANUARY:   currentMonthName = "January";   break;
			case Calendar.FEBRUARY:  currentMonthName = "February";  break;
			case Calendar.MARCH:     currentMonthName = "March";     break;
			case Calendar.APRIL:     currentMonthName = "April";     break;
			case Calendar.MAY:       currentMonthName = "May";       break;
			case Calendar.JUNE:      currentMonthName = "June";      break;
			case Calendar.JULY:      currentMonthName = "July";      break;
			case Calendar.AUGUST:    currentMonthName = "August";    break;
			case Calendar.SEPTEMBER: currentMonthName = "September"; break;
			case Calendar.OCTOBER:   currentMonthName = "October";   break;
			case Calendar.NOVEMBER:  currentMonthName = "November";  break;
			case Calendar.DECEMBER:  currentMonthName = "December";  break;
		}
		
		monthBar.setText(currentMonthName + " " + Integer.toString (year));
		
		// Draw the month's shifts
		
		Date current = new Date();
		current.setYear(cal.get(Calendar.YEAR));
		current.setMonth(cal.get(Calendar.MONTH));
		
		List<Day> shifts = db.getMonthShifts(current);
		Iterator<Day> d = shifts.iterator();
		
		while (d.hasNext()) {
			
			Day draw = d.next();
			DayView dv = (DayView) getDayView(draw.date);
			dv.setLabelText(draw.shiftSymbol);
			dv.setLabelColor(draw.shiftColor);
		}
	}
	
	private int getFirstXCoord (int year, int month) {
		
		Calendar cal = Calendar.getInstance();
		cal.set (year, month, 1);
		int firstDayRet = cal.get(Calendar.DAY_OF_WEEK);
		
		int XCoord = 0;
		
		switch (firstDayRet) {
		
			case Calendar.MONDAY:    XCoord = 0; break;
			case Calendar.TUESDAY:   XCoord = 1; break;
			case Calendar.WEDNESDAY: XCoord = 2; break;
			case Calendar.THURSDAY:  XCoord = 3; break;
			case Calendar.FRIDAY:    XCoord = 4; break;
			case Calendar.SATURDAY:  XCoord = 5; break;
			case Calendar.SUNDAY:    XCoord = 6; break;
		}
		
		return XCoord;
	}
	
	private DayView getDayView (Date d) {
		
		int firstXCoord = getFirstXCoord(d.getYear(), d.getMonth());
		int gap = d.getDate() - 1;
		
		int length = firstXCoord + gap;
		
		int xCoord = length % 7;
		int yCoord = (int) Math.floor((double) length / 7);
		
		return (DayView) ds.getSquareByPos(xCoord, yCoord);
	}
	
	public void setCalendar (int month, int year) {
		
		this.month = month;
		this.year = year;
		redrawCalendar();
		
	}
	
	public void increaseMonth() {
		
		int month = this.month;
		int year = this.year;
		
		month++;
		
		if (month > 11) {
			
			month = 0;
			year++;
		}
		
		this.setCalendar(month, year);
	}
	
	public void decreaseMonth() {
		
		int month = this.month;
		int year = this.year;
		
		month--;
		
		if (month < 0) {
			
			month = 11;
			year--;
		}
		
		this.setCalendar(month, year);
		
	}
	
	public int getMonth() {
		
		return this.month;
	}
	
	public int getYear() {
		
		return this.year;
	}
	
	public void setMonth (int newMonth) {
		
		this.setCalendar(newMonth, this.year);
	}
	
	public void setYear (int newYear) {
		
		this.setCalendar(this.month, newYear);
	}
}

