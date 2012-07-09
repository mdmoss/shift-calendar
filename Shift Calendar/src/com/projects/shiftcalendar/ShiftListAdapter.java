package com.projects.shiftcalendar;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShiftListAdapter extends BaseAdapter {

	private Context context;
	private ShiftCalDB db;
	private List<Shift> shiftList;

	// Add database connection on create?
	public ShiftListAdapter(Context context) {

		this.context = context;
		this.db = ((ShiftCalendar) context.getApplicationContext()).getDB();
		this.shiftList = db.getAllShifts();
	}

	public int getCount() {

		int entryCount = this.db.getEntryCount();

		return entryCount;
	}

	public Shift getItem(int id) {

		Shift ret;

		if (id >= 0 && id < shiftList.size()) {

			ret = shiftList.get(id);
		} else {
			ret = null;
		}

		return ret;
	}

	public long getItemId(int item) {
		// WTF alert :/
		return item;
	}

	public View getView(int position, View convertView, ViewGroup viewGroup) {

		if (convertView == null) {
			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflator.inflate(R.layout.shift_bar, null);
		}

		Shift result = getItem(position);

		if (result != null) {

			convertView.setVisibility(View.VISIBLE);

			TextView nameField = (TextView) convertView
					.findViewById(R.id.shift_name);
			nameField.setText(result.name);

			TextView symbolField = (TextView) convertView
					.findViewById(R.id.shift_symbol);
			symbolField.setText(result.symbol);
			symbolField.setTextColor(result.color);

		} else {

			convertView.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	public void notifyDataSetChanged() {

		this.shiftList = db.getAllShifts();
		super.notifyDataSetChanged();
	}
}