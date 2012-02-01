package com.projects.shiftcalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class WeekView extends LinearLayout {
	
	ShiftCalDB db;
	
	public WeekView (Context context, AttributeSet attrs) {
		super (context, attrs);
		this.db =((ShiftCalendar)context.getApplicationContext()).getDB();
		this.layout();
	}
	
	private void layout() {
		
		this.setOrientation(VERTICAL);
		this.setWeightSum(7);
		
		Calendar rollingDate = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.roll(Calendar.DATE, 1);
		
		SimpleDateFormat date = new SimpleDateFormat("dd");
		SimpleDateFormat dayName = new SimpleDateFormat("EEEE");
		
		for (int i = 0; i < 7; i++) {
			
			LayoutParams params = generateDefaultLayoutParams();
			params.weight = 1;
			
			WeekViewBar bar = new WeekViewBar(getContext());
			bar.setLayoutParams(params);
			
			this.addView(bar);
			
			bar.date.setText(date.format(rollingDate.getTime()));
			
			if (rollingDate.get(Calendar.DATE) == today.get(Calendar.DATE)) {
				bar.title.setText("Today");
			} else if (rollingDate.get(Calendar.DATE) == tomorrow.get(Calendar.DATE)) {
				bar.title.setText("Tomorrow");
			} else {
				bar.title.setText(dayName.format(rollingDate.getTime()));
			}
				
			// Draw the shift symbol
			Date query = rollingDate.getTime();
			query.setYear(rollingDate.get(Calendar.YEAR));
			Shift sh = db.getShiftByDate(query);
			
			if (sh != null) {
				bar.symbol.setText(sh.symbol);
				bar.symbol.setTextColor(sh.color);
			}
			
			rollingDate.roll(Calendar.DATE, 1);
		}
		
	}
	
}