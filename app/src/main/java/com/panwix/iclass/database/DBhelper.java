package com.panwix.iclass.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
	private static final String NAME = "ClassTable.db";
	private static final int VERSION = 1;

	public DBhelper(Context context) {
		super(context, NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE Class(id INTEGER PRIMARY KEY AUTOINCREMENT,class VARCHAR(64),classroom VARCHAR(64),teacher VARCHAR(64),tel VARCHAR(64),phone VARCHAR(64),email VARCHAR(64),office VARCHAR(64),week VARCHAR(64),classStart VARCHAR(64),classEnd VARCHAR(64),classTime VARCHAR(64))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("CREATE TABLE Class(id INTEGER PRIMARY KEY AUTOINCREMENT,class VARCHAR(64),classroom VARCHAR(64),teacher VARCHAR(64),tel VARCHAR(64),phone VARCHAR(64),email VARCHAR(64),office VARCHAR(64),week VARCHAR(64),classStart VARCHAR(64),classEnd VARCHAR(64),classTime VARCHAR(64))");

	}

	public Cursor query(String sql, String[] args) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, args);
		return cursor;
	}

}
