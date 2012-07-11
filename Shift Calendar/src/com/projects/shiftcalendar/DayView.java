package com.projects.shiftcalendar;
/**
 * @author Matthew Moss
 *
 */
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DayView extends RelativeLayout {

	public static final int NO_DATE = -1;
	public static final int NO_SHIFT = -1;

	private int date = NO_DATE;

	public int shiftId = NO_SHIFT;

	public DayView(Context context) {

		super(context);
		LayoutInflater.from(getContext())
				.inflate(R.layout.day_view, this, true);
	}

	public DayView(Context context, AttributeSet attrs) {

		super(context, attrs);
		LayoutInflater.from(getContext())
				.inflate(R.layout.day_view, this, true);
	}

	public void setDate(int newDate) {

		TextView t = (TextView) this.findViewById(R.id.day_view_date);
		date = newDate;

		if (date != NO_DATE) {
			t.setText(String.valueOf(newDate));
		} else {
			t.setText("");
		}
	}

	public void setDateColor(int newColor) {

		TextView t = (TextView) this.findViewById(R.id.day_view_date);
		t.setTextColor(newColor);
	}

	public void setLabelText(String newtext) {

		TextView t = (TextView) this.findViewById(R.id.day_view_label);
		t.setText(newtext);
	}

	public void setLabelColor(int newColor) {

		TextView t = (TextView) this.findViewById(R.id.day_view_label);
		t.setTextColor(newColor);
	}
	
	public void setLabelFontSize (int newSize) {
		TextView t = (TextView) this.findViewById(R.id.day_view_label);
		t.setTextSize(newSize);
	}

	public int getDate() {

		return date;
	}
}
