package com.projects.shiftcalendar;
/**
 * @author Matthew Moss
 *
 */
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ModifyPreferences extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

}