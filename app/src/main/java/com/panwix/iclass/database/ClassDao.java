package com.panwix.iclass.database;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import android.R.id;
import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ClassDao implements DBService {
	private DBhelper helper;
	private SQLiteDatabase db;

	public ClassDao(Context context) {
		helper = new DBhelper(context);
		db = helper.getWritableDatabase();
	}

	@Override
	public boolean addClass(ContentValues values) {
		boolean flag = false;
		SQLiteDatabase database = null;
		long id = -1;
		try {
			database = helper.getWritableDatabase();
			id = database.insert("Class", null, values);
			flag = (id != -1 ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	

	@Override
	public boolean deleteClass(String whereClause, String[] whereArgs) {
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			count = database.delete("Class", whereClause, whereArgs);
			flag = (count != 0 ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	@Override
	public boolean updateClass(ContentValues values, String whereClause,
			String[] whereArgs) {
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			count = database
					.update("Class", values, whereClause, whereArgs);
			flag = (count > 0 ? true : false);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	
	@Override
	public Map<String, String> selectClass(String selection,
			String[] whereArgs) {
		Map<String, String> map = new HashMap<String, String>();

		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {
			database = helper.getWritableDatabase();
			cursor = database.query(true,"Class", null, selection, whereArgs,
					null, null, null,null);
			int col_len = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				for (int i = 0; i < col_len; i++) {
					String cols_name = cursor.getColumnName(i);
					String cols_values = cursor.getString(cursor.getColumnIndex(cols_name));
					if (cols_values == null) {
						cols_values = "";
					}
					map.put(cols_name, cols_values);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return map;
	}

	
}
