package com.projects.shiftcalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class WeekView extends LinearLayout {
	
	public WeekView (Context context) {
		super (context);
		this.layout();
		
	}
	
	public WeekView (Context context, AttributeSet attrs) {
		super (context, attrs);
		this.layout();
	}
	
	private void layout() {
		
		this.setOrientation(VERTICAL);
		this.setWeightSum(7);
		
		// Here's the change - this will be troublesome
		Date startOfWeek = new Date();
		Date current = new Date();
		Calendar c = Calendar.getInstance();
		ShiftCalDB db = new ShiftCalDB(getContext());
		
		SimpleDateFormat df = new SimpleDateFormat();
		
		int todayDrawn = 0;
		
		for (int i = 0; i < 7; i++) {
			
			LayoutParams params = generateDefaultLayoutParams();
			params.weight = 1;
			
			WeekViewBar bar = new WeekViewBar(getContext());
			bar.setLayoutParams(params);
			
			this.addView(bar);
			
			/*if (current.getDate() == startOfWeek.getDate() && 
				current.getMonth() == startOfWeek.getMonth() &&
				current.getYear() == startOfWeek.getYear() &&
				todayDrawn == 0) {
				
				bar.title.setText("Today");
				todayDrawn = 1;
				current.setDate (current.getDate() + 1);
				
			} else if (current.getDate() == startOfWeek.getDate() && 
					current.getMonth() == startOfWeek.getMonth() &&
					current.getYear() == startOfWeek.getYear()){
				
				bar.title.setText("Tomorrow");
				
			} else {*/
				
				bar.title.setText(df.format(startOfWeek));
			//}
			
			
			// Draw the shift symbol
			Shift sh = db.getShiftByDate(startOfWeek);
			
			if (sh != null) {
				System.err.println("Shift Returned");
				bar.symbol.setText(sh.symbol);
				bar.symbol.setTextColor(sh.color);
			}
			
			startOfWeek.setDate(startOfWeek.getDate() + 1);
			
		}
		
	}
	
}