package com.panwix.iclass.database;

import java.util.Map;

import android.content.ContentValues;

public interface DBService {
	public boolean addClass(ContentValues values);
	
	
	public boolean deleteClass(String whereClause, String[] whereArgs);
	
	
	public boolean updateClass(ContentValues values, String whereClause, String[] whereArgs);
	
	
	public Map<String, String> selectClass(String whereClause, String[] whereArgs);
	
}
