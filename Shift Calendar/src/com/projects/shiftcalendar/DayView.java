package com.projects.shiftcalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DayView extends RelativeLayout {
	
	TextView date;
	TextView label;
	
	public DayView (Context context) {
		
		super (context);
		LayoutInflater.from(getContext()).inflate(R.layout.day_view, this, true);
	}
	
	public DayView (Context context, AttributeSet attrs) {
		
		super (context, attrs);
		LayoutInflater.from(getContext()).inflate(R.layout.day_view, this, true);

	}
	
	public void setDateText (String newtext) {
		
		TextView t = (TextView) this.findViewById(R.id.day_view_date);
		t.setText(newtext);
	}
	
	public void setDateColor (int newColor) {

		TextView t = (TextView) this.findViewById(R.id.day_view_date);
		t.setTextColor(newColor);
	}
	
	public void setLabelText (String newtext) {
		
		TextView t = (TextView) this.findViewById(R.id.day_view_label);
		t.setText(newtext);
	}
	
	public void setLabelColor (int newColor) {
		
		TextView t = (TextView) this.findViewById(R.id.day_view_label);
		t.setTextColor(newColor);
	}
	
	public int getDate () {
		
		int ret;
		
		try {
			ret = Integer.parseInt((String) this.date.getText());
		} catch (java.lang.NumberFormatException e) {
			ret = 0;
		} 
		
		return ret;
	
	}
}

