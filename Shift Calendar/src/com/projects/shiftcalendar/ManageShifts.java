package com.projects.shiftcalendar;
/**
 * @author Matthew Moss
 *
 */
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ManageShifts extends Activity {

	ShiftListAdapter listContent;

	private OnClickListener addShiftListener = new OnClickListener() {

		public void onClick(View v) {

			launchModifyShiftActivity(null);

		}
	};

	private OnItemClickListener shiftClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Shift toModify = listContent.getItem((int) arg3);
			launchModifyShiftActivity(toModify);
		}

	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shifts_list);

		// Add new shift button event listener
		Button addShiftButton = (Button) findViewById(R.id.new_shift_button);
		addShiftButton.setOnClickListener(addShiftListener);

		// Fill in the list
		listContent = new ShiftListAdapter(getApplicationContext());

		ListView lv = (ListView) findViewById(R.id.shifts_list_listView);
		lv.setAdapter(listContent);
		registerForContextMenu(lv);
		lv.setOnItemClickListener(shiftClickListener);
	}

	@Override
	public void onResume() {

		// Force an update of the list view. Will do for now
		listContent.notifyDataSetChanged();
		super.onResume();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		System.err.println("Long click triggered");

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		ListView lV = (ListView) v;

		Shift selectedItem = (Shift) lV.getAdapter().getItem(info.position);
		menu.setHeaderTitle(selectedItem.name);

		menu.add("Delete");

		super.onCreateContextMenu(menu, v, menuInfo);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		Shift attatchedShift = listContent.getItem((int) info.id);

		if (item.getTitle().equals("Delete")) {

			ShiftCalDB db = ((ShiftCalendar) getApplication()).getDB();
			db.deleteShift(attatchedShift.id);
			listContent.notifyDataSetChanged();
		} else if (item.getTitle().equals("Modify")) { // Relic of an older
														// system

			launchModifyShiftActivity(attatchedShift);

		}

		return true;

	}

	private void launchModifyShiftActivity(Shift input) {

		Intent change = new Intent();

		ComponentName actName = new ComponentName("com.projects.shiftcalendar",
				"com.projects.shiftcalendar.ModifyShift");

		change.setComponent(actName);

		if (input != null) {

			change.putExtra("Modify", input.id);
		}

		startActivity(change);
	}
}