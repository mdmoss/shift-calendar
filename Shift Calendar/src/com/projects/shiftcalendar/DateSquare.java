package com.projects.shiftcalendar;
/**
 * @author Matthew Moss
 *
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableLayout;
import android.widget.TableRow;

public class DateSquare extends TableLayout {

	TableRow rows[] = new TableRow[6];
	DayView dates[][] = new DayView[6][7];

	public DateSquare(Context context, AttributeSet attrs) {

		super(context, attrs);

		this.setBackgroundColor(0xFF444444);
		this.setWeightSum(6);

		for (int i = 0; i < 6; i++) {

			rows[i] = new TableRow(context);
			rows[i].setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT, 1));
			rows[i].setWeightSum(7);
			this.addView(rows[i]);

			for (int j = 0; j < 7; j++) {

				dates[i][j] = new DayView(context);
				dates[i][j]
						.setLayoutParams(new TableRow.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT, 1));
				rows[i].addView(dates[i][j]);

			}
		}
	}

	public DayView getSquareByPos(int x, int y) {

		return dates[y][x];
	}

	public List<DayView> getAllSquares() {

		List<DayView> allDays = new ArrayList<DayView>();

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				// n^2, but whatev. Fixed length
				allDays.add(this.dates[i][j]);
			}
		}
		return allDays;
	}
}