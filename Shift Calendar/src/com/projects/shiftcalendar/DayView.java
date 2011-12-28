package com.projects.shiftcalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DayView extends RelativeLayout {
	
	TextView date;
	TextView label;
	
	public DayView (Context context) {
		
		super (context);
		this.setup(context);
	}
	
	public DayView (Context context, AttributeSet attrs) {
		
		super (context, attrs);
		this.setup(context);

	}
	
	private void setup (Context context) {
		
		this.date = new TextView (context);
		this.label = new TextView (context);
		
		RelativeLayout.LayoutParams dateLayout = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dateLayout.addRule(ALIGN_PARENT_TOP);
		dateLayout.addRule(ALIGN_PARENT_LEFT);
		
		RelativeLayout.LayoutParams labelLayout = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		labelLayout.addRule(ALIGN_PARENT_RIGHT);
		labelLayout.addRule(ALIGN_PARENT_BOTTOM);
		
		this.addView (this.date, dateLayout);
		this.addView (this.label, labelLayout);
		
		this.setBackgroundColor(0xFF999999);
		
		this.label.setPadding(0, 0, 16, 8);
	}
	
	public void setDateText (String newtext) {
		
		this.date.setText (newtext);
	}
	
	public void setDateColor (int newColor) {
		
		this.date.setTextColor(newColor);
	}
	
	public void setLabelText (String newtext) {
		
		this.label.setText (newtext);
	}
	
	public void setLabelColor (int newColor) {
		
		this.label.setTextColor(newColor);
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

