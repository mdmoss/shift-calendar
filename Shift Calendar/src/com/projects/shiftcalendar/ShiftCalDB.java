package com.projects.shiftcalendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class ShiftCalDB {
	
	int id = 0;
	public static final String KEY_ROWID = "_id";
	
	public static final String KEY_SHIFT_NAME = "Name";
	public static final String KEY_SHIFT_SYMBOL = "Symbol";
	public static final String KEY_SHIFT_COLOR = "Color";
	public static final String KEY_SHIFT_START_HOURS = "StartHours";
	public static final String KEY_SHIFT_START_MINUTES = "StartMinutes";
	public static final String KEY_SHIFT_END_HOURS = "EndHours";
	public static final String KEY_SHIFT_END_MINUTES = "EndMinutes";
	public static final String KEY_SHIFT_COMMENT = "Comment";
	
	public static final String KEY_DATES_YEAR = "Year";
	public static final String KEY_DATES_MONTH = "Month";
	public static final String KEY_DATES_DAY = "Day";
	public static final String KEY_DATES_SHIFTID = "ShiftID";
	
	private static final String DATABASE_NAME = "ShiftCalDB";
	
	private static final String DATABASE_SHIFT_TABLE= "tblShiftTypes";
	private static final String DATABASE_DATES_TABLE = "tblDates";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_TAG = "ShiftCalDB";
	
    private static final String TABLE_CREATE_SHIFT =
            "CREATE TABLE " + DATABASE_SHIFT_TABLE + 
            " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SHIFT_NAME + " TEXT, "
            + KEY_SHIFT_SYMBOL + " TEXT, " 
            + KEY_SHIFT_COLOR + " INT, "
            + KEY_SHIFT_COMMENT + " TEXT, "
            + KEY_SHIFT_START_HOURS + " INT, "
            + KEY_SHIFT_START_MINUTES + " INT, "
            + KEY_SHIFT_END_HOURS + " INT, "
            + KEY_SHIFT_END_MINUTES + " INT);";
            
    private static final String TABLE_CREATE_DATES =         
            "CREATE TABLE " + DATABASE_DATES_TABLE +
            " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_DATES_YEAR + " INT,"
            + KEY_DATES_MONTH + " INT,"
            + KEY_DATES_DAY + " INT,"
            + KEY_DATES_SHIFTID + " INT);";
    
    @SuppressWarnings("unused")
	private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
        
    public ShiftCalDB (Context context) {
    	
    	this.context = context;
    	this.DBHelper = new DatabaseHelper (context);
    }


	private static class DatabaseHelper extends SQLiteOpenHelper {
		
		DatabaseHelper (Context context) {
			
			super (context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate (SQLiteDatabase db) {

			db.execSQL(TABLE_CREATE_SHIFT);
			db.execSQL(TABLE_CREATE_DATES);
		}
		
		@Override
		public void onUpgrade (SQLiteDatabase db, int oldVersion, 
				int newVersion) {
			
			Log.w (DATABASE_TAG, "Upgrading database from version " + oldVersion
                   + " to "
                   + newVersion + ", which will destroy all old data");
			
			}
		
	}
	
	public ShiftCalDB open() throws SQLException {
		
		this.db = DBHelper.getWritableDatabase();
		return this;
	}
	
	public ShiftCalDB openRead() throws SQLException {
		
		this.db = DBHelper.getReadableDatabase();
		return this;
	}
	
	public void close() {
		
		DBHelper.close();
		
	}
	
	public void insertShift (Shift sh) {
		
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_SHIFT_NAME, sh.name);
		initialValues.put(KEY_SHIFT_SYMBOL, sh.symbol);
		initialValues.put(KEY_SHIFT_COLOR, sh.color);
		initialValues.put(KEY_SHIFT_COMMENT, sh.comments);
		initialValues.put(KEY_SHIFT_START_HOURS, sh.startTime.hour);
		initialValues.put(KEY_SHIFT_START_MINUTES, sh.startTime.minute);
		initialValues.put(KEY_SHIFT_END_HOURS, sh.endTime.hour);
		initialValues.put(KEY_SHIFT_END_MINUTES, sh.endTime.hour);
		
		this.open();
		db.insert (DATABASE_SHIFT_TABLE, null, initialValues);
		this.close();
	}

	public void deleteShift (int id) {
		
		String whereClause = KEY_ROWID + " = " + Integer.toString(id);
		
		this.open();
		db.delete(DATABASE_SHIFT_TABLE, whereClause, null);
		this.close();
		
	}
	
	public void updateShift (int oldId, Shift sh) {
		
		this.open();
		
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_SHIFT_NAME, sh.name);
		initialValues.put(KEY_SHIFT_SYMBOL, sh.symbol);
		initialValues.put(KEY_SHIFT_COLOR, sh.color);
		initialValues.put(KEY_SHIFT_COMMENT, sh.comments);
		initialValues.put(KEY_SHIFT_START_HOURS, sh.startTime.hour);
		initialValues.put(KEY_SHIFT_START_MINUTES, sh.startTime.minute);
		initialValues.put(KEY_SHIFT_END_HOURS, sh.endTime.hour);
		initialValues.put(KEY_SHIFT_END_MINUTES, sh.endTime.minute);
		
		String whereClause = KEY_ROWID + "=" + oldId;
		
		db.update(DATABASE_SHIFT_TABLE, initialValues,whereClause, null);
		
		this.close();
	}

	public int getEntryCount () {
		
		this.openRead();
		Cursor cursor = db.query (true, DATABASE_SHIFT_TABLE, null, "", null, "", "", "", "");
		int entries = cursor.getCount();
		cursor.close();
		this.close();
			
		return entries;
		
	}
	
	public Shift getShiftByID (int id) {
		
		String[] colList = {KEY_SHIFT_NAME, KEY_SHIFT_SYMBOL, KEY_SHIFT_COLOR, KEY_SHIFT_COMMENT, KEY_ROWID,
				            KEY_SHIFT_START_HOURS, KEY_SHIFT_START_MINUTES, KEY_SHIFT_END_HOURS, KEY_SHIFT_END_MINUTES};
		
		String whereClause = KEY_ROWID + " = " + Integer.toString(id);
		
		this.openRead();
		
		Cursor cursor = db.query(true, DATABASE_SHIFT_TABLE, colList, whereClause, null, null, null, null, null);
		
		Shift ret;
		
		if (cursor.moveToFirst()) {
			ret = new Shift();
			ret.name = cursor.getString(cursor.getColumnIndex(KEY_SHIFT_NAME));
			ret.symbol = cursor.getString(cursor.getColumnIndex(KEY_SHIFT_SYMBOL));
			ret.color = cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_COLOR));
			ret.comments = cursor.getString(cursor.getColumnIndex(KEY_SHIFT_COMMENT));
			ret.id = cursor.getInt(cursor.getColumnIndex(KEY_ROWID));
			ret.startTime.minute = cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_START_MINUTES));
			ret.startTime.hour = cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_START_HOURS));
			ret.endTime.minute = cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_END_MINUTES));
			ret.endTime.hour = cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_END_HOURS));
			
		} else {
			
			ret = null;
		}
		
		cursor.close();
		this.close();
		
		return ret;
	}
	
	public List<Shift> getAllShifts () {
		
		String[] colList = {KEY_SHIFT_NAME, KEY_SHIFT_SYMBOL, KEY_SHIFT_COLOR, KEY_SHIFT_COMMENT, KEY_ROWID,
	            KEY_SHIFT_START_HOURS, KEY_SHIFT_START_MINUTES, KEY_SHIFT_END_HOURS, KEY_SHIFT_END_MINUTES};
		
		this.openRead();
		
		List<Shift> allShiftsList = new ArrayList<Shift>();
		
		Cursor cursor = db.query(true, DATABASE_SHIFT_TABLE, colList, null, null, null, null, null, null);

		while (cursor.moveToNext()) {
			Shift sh = new Shift();
			sh.name = cursor.getString(cursor.getColumnIndex(KEY_SHIFT_NAME));
			sh.symbol = cursor.getString(cursor.getColumnIndex(KEY_SHIFT_SYMBOL));
			sh.color = cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_COLOR));
			sh.comments = cursor.getString(cursor.getColumnIndex(KEY_SHIFT_COMMENT));
			sh.id = cursor.getInt(cursor.getColumnIndex(KEY_ROWID));
			sh.startTime.minute = cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_START_MINUTES));
			sh.startTime.hour = cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_START_HOURS));
			sh.endTime.minute = cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_END_MINUTES));
			sh.endTime.hour = cursor.getInt(cursor.getColumnIndex(KEY_SHIFT_END_HOURS));
			
			allShiftsList.add(sh);
		}
		
		cursor.close();
		this.close();
		
		return allShiftsList;
	}
	
	public Shift getShiftByDate (Date d) {
		
		String[] colList = {KEY_DATES_SHIFTID};
		
		String selection = KEY_DATES_YEAR + " = " + Integer.toString(d.getYear() + 1900) + " AND " + KEY_DATES_MONTH + " = "
		                   + Integer.toString(d.getMonth()) + " AND " + KEY_DATES_DAY + " = " + Integer.toString(d.getDate());
		
		this.openRead();
		
		Cursor cursor = db.query(true, DATABASE_DATES_TABLE, colList, selection, null, null, null, null, null);
		
		Shift s;
		
		if (cursor.moveToFirst()) {
			s = getShiftByID(cursor.getInt(cursor.getColumnIndex(KEY_DATES_SHIFTID)));
		} else {
			s = null;
		}
		cursor.close();
		this.close();
		
		return s;
	}
	
	public List<Day> getMonthShifts (Date ym) {
		
		List<Day> daysList = new ArrayList<Day>();
	
		String[] colList = {KEY_DATES_DAY, KEY_DATES_SHIFTID};
		
		String selection = KEY_DATES_YEAR + " = " + Integer.toString(ym.getYear() + 1900) + " AND " + KEY_DATES_MONTH + " = " + Integer.toString(ym.getMonth());
		
		this.openRead();
		
		Cursor cursor = db.query(true, DATABASE_DATES_TABLE, colList, selection, null, null, null, null, null);
			
		while (cursor.moveToNext()) {
			
			Day d = new Day();
			d.date = (Date) ym.clone();
			d.date.setDate(cursor.getInt(cursor.getColumnIndex(KEY_DATES_DAY)));
			d.shiftId = cursor.getInt(cursor.getColumnIndex(KEY_DATES_SHIFTID));
			
			Shift s = this.getShiftByID(d.shiftId);
			
			if (s != null) {
			
				d.shiftSymbol = s.symbol;
				d.shiftColor = s.color;
			
				daysList.add (d);
			}
		}
		
		cursor.close();
		this.close();
		
		return daysList;
	}
	
	public void setDayShift (Day d) {
		
		ContentValues v = new ContentValues();
		v.put(KEY_DATES_DAY, d.date.getDate());
		v.put(KEY_DATES_MONTH, d.date.getMonth());
		v.put(KEY_DATES_YEAR, d.date.getYear() + 1900);
		v.put(KEY_DATES_SHIFTID, d.shiftId);
		
		this.open();
		db.insert(DATABASE_DATES_TABLE, null, v);
		this.close();
	}
	
	public void clearDay (Date d) {
		
		String whereClause = KEY_DATES_YEAR + " = " + Integer.toString(d.getYear() + 1900) + " AND " +
							 KEY_DATES_MONTH + " = " + Integer.toString(d.getMonth()) + " AND " +
							 KEY_DATES_DAY + " = " + Integer.toString(d.getDate());
		
		this.open();
		db.delete(DATABASE_DATES_TABLE, whereClause, null);
		this.close();
	}
}