package com.projects.shiftcalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WeekViewBar extends FrameLayout {
	
	TextView title;
	TextView symbol;
	
	public WeekViewBar (Context context) {
	
		super (context);
		layout (context);
	
	}
	
	public WeekViewBar (Context context, AttributeSet attrs) {
		
		super (context, attrs);
		layout (context);
	}
	
	private void layout (Context context) {
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		
		RelativeLayout internal = (RelativeLayout)inflater.inflate(R.layout.week_view_bar, null);
		this.addView(internal);
		
		title = (TextView) findViewById(R.id.view_week_bar_title);
		symbol = (TextView) findViewById(R.id.view_week_bar_symbol);
	}
}