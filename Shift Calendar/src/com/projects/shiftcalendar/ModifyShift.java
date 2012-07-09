package com.projects.shiftcalendar;
/**
 * @author Matthew Moss
 *
 */
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ModifyShift extends Activity {

	private Date startTime;
	private Date endTime;

	static final int START_TIME_PICKER = 1;
	static final int END_TIME_PICKER = 2;

	private ShiftCalDB dbConnect = null;

	private OnClickListener save = new OnClickListener() {

		public void onClick(View v) {

			TextView nameField = (TextView) findViewById(R.id.modify_shift_name);
			TextView symbolField = (TextView) findViewById(R.id.modify_shift_symbol);
			TextView commentField = (TextView) findViewById(R.id.modify_shift_comment);

			if (nameField.getText().toString().length() > 0) {

				Shift toAdd = new Shift();
				toAdd.name = nameField.getText().toString();
				toAdd.symbol = symbolField.getText().toString();
				toAdd.comments = commentField.getText().toString();

				toAdd.startTime.minute = startTime.getMinutes();
				toAdd.startTime.hour = startTime.getHours();
				toAdd.endTime.minute = endTime.getMinutes();
				toAdd.endTime.hour = endTime.getHours();

				// Get the color displayed by the spinner
				Spinner colorChooser = (Spinner) findViewById(R.id.modify_shift_choose_color);
				String colorAsString = (String) colorChooser.getSelectedItem();

				if ("Red".equals(colorAsString)) {
					toAdd.color = Color.RED;
				} else if ("Yellow".equals(colorAsString)) {
					toAdd.color = Color.YELLOW;
				} else if ("Green".equals(colorAsString)) {
					toAdd.color = Color.GREEN;
				} else if ("Blue".equals(colorAsString)) {
					toAdd.color = Color.BLUE;
				} else if ("Purple".equals(colorAsString)) {
					toAdd.color = Color.MAGENTA;
				} else {
					toAdd.color = Color.WHITE;
				}

				if (dbConnect == null) {
					dbConnect = ((ShiftCalendar) getApplication()).getDB();
				}

				// If there's an old version, delete it.

				Intent i = getIntent();

				if (i.hasExtra("Modify")) {

					dbConnect.updateShift(i.getIntExtra("Modify", 0), toAdd);
				} else {

					dbConnect.insertShift(toAdd);

				}

				finish();

			} else {

				CharSequence helpText = "Please set a name for this shift";
				Toast popup = Toast.makeText(getApplicationContext(), helpText,
						Toast.LENGTH_SHORT);
				popup.show();

			}

		}
	};

	private OnClickListener cancel = new OnClickListener() {

		public void onClick(View v) {

			finish();

		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_shift);

		// Save button event listener
		Button saveButton = (Button) findViewById(R.id.modify_shift_save);
		saveButton.setOnClickListener(save);

		// Cancel button event listener
		Button cancelButton = (Button) findViewById(R.id.modify_shift_cancel);
		cancelButton.setOnClickListener(cancel);

		// Bind the color spinner
		Spinner colorChooser = (Spinner) findViewById(R.id.modify_shift_choose_color);
		ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter
				.createFromResource(this, R.array.colors_array,
						android.R.layout.simple_spinner_item);
		colorAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		colorChooser.setAdapter(colorAdapter);

		// Initialise the Time objects
		startTime = new Date();
		endTime = new Date();

		// Bind the set time buttons
		Button setStartTime = (Button) findViewById(R.id.modify_shift_start_time_button);
		Button setEndTime = (Button) findViewById(R.id.modify_shift_end_time_button);

		setStartTime.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				showDialog(START_TIME_PICKER);
			}
		});

		setEndTime.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				showDialog(END_TIME_PICKER);
			}
		});

		// Here's the fun part. Check for preexisting shift
		Intent i = getIntent();
		if (i.hasExtra("Modify")) {

			int oldId = i.getIntExtra("Modify", 0);
			dbConnect = ((ShiftCalendar) getApplication()).getDB();
			Shift sh = dbConnect.getShiftByID(oldId);

			EditText nameField = (EditText) findViewById(R.id.modify_shift_name);
			nameField.setText(sh.name);

			EditText symbolField = (EditText) findViewById(R.id.modify_shift_symbol);
			symbolField.setText(sh.name);

			if (sh.color == Color.RED) {
				colorChooser.setSelection(colorAdapter.getPosition("Red"));

			} else if (sh.color == Color.YELLOW) {
				colorChooser.setSelection(colorAdapter.getPosition("Yellow"));

			} else if (sh.color == Color.GREEN) {
				colorChooser.setSelection(colorAdapter.getPosition("Green"));

			} else if (sh.color == Color.BLUE) {
				colorChooser.setSelection(colorAdapter.getPosition("Blue"));

			} else if (sh.color == Color.MAGENTA) {
				colorChooser.setSelection(colorAdapter.getPosition("Purple"));
			}

			startTime.setMinutes(sh.startTime.minute);
			startTime.setHours(sh.startTime.hour);
			endTime.setMinutes(sh.endTime.minute);
			endTime.setHours(sh.endTime.hour);

			EditText commentField = (EditText) findViewById(R.id.modify_shift_comment);
			commentField.setText(sh.comments);

		}

		updateDisplay();
	}

	public void updateDisplay() {

		TextView startTimeText = (TextView) findViewById(R.id.modify_shift_start_time);
		TextView endTimeText = (TextView) findViewById(R.id.modify_shift_end_time);

		SimpleDateFormat df = new SimpleDateFormat("h:mm a");

		startTimeText.setText(df.format(startTime));
		endTimeText.setText(df.format(endTime));
	}

	private TimePickerDialog.OnTimeSetListener TimeSetListenerStart = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			startTime.setMinutes(minute);
			startTime.setHours(hourOfDay);
			updateDisplay();
		}
	};
	private TimePickerDialog.OnTimeSetListener TimeSetListenerEnd = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			endTime.setMinutes(minute);
			endTime.setHours(hourOfDay);
			updateDisplay();
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {

		case START_TIME_PICKER:
			return new TimePickerDialog(this, TimeSetListenerStart,
					startTime.getHours(), startTime.getMinutes(), false);
		case END_TIME_PICKER:
			return new TimePickerDialog(this, TimeSetListenerEnd,
					endTime.getHours(), endTime.getMinutes(), false);
		}
		return null;

	}
}