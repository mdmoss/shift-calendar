package com.projects.shiftcalendar;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class DateSquare extends LinearLayout {
	
	LinearLayout rows[] = new LinearLayout[6];
	DayView dates[][] = new DayView[6][7];
	
	Context context;
	
	public DateSquare (Context context, AttributeSet attrs) {
		
		super (context, attrs);
		
		this.setOrientation(VERTICAL);
		this.setWeightSum(6);
		this.setBackgroundColor(0xFF444444);
		
		
		this.context = context;
		
		for (int i = 0; i < 6; i++) {
			
			rows[i] = new LinearLayout (context);
			
			LinearLayout.LayoutParams params = generateDefaultLayoutParams();
			params.weight = 1;
			rows[i].setLayoutParams (params);
			rows[i].setWeightSum(7);
			
			this.addView(rows[i]);
			
			for (int j = 0; j < 7; j++) {
			
				dates[i][j] = new DayView (context);
				LayoutParams dateParams = generateDefaultLayoutParams();
				dateParams.height = LayoutParams.FILL_PARENT;
				dateParams.weight = 1;
				
				dateParams.leftMargin = 1;
				if (j == 6) {
					dateParams.rightMargin = 1;
				}
				
				dateParams.topMargin = 1;
				if (i == 5) {
					dateParams.bottomMargin = 1;
				}
				
				dates[i][j].setLayoutParams(dateParams);
				dates[i][j].setBackgroundColor(0xFF000000);
				dates[i][j].setPadding(8, 0, 0, 0);
				
				rows[i].addView(dates[i][j]);
			}
		}	
	}
	
	public DayView getSquareByPos (int x, int y) {
	
		return dates[y][x];
	}
	
	public List<DayView> getAllSquares () {
		
		List<DayView> allDays = new ArrayList<DayView>();
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				// n^2, but whatev. Fixed length
				allDays.add (this.dates[i][j]);
			}
		}
		return allDays;
	}
}