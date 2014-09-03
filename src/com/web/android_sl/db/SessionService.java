package com.web.android_sl.db;

import java.net.ContentHandler;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SessionService {
	private DbOpenHelper dbOpenHelper;
	private SQLiteDatabase db;

	public SessionService(Context context) {
		this.dbOpenHelper = new DbOpenHelper(context);
		
	}

	/**
	 * 保存session
	 * 
	 * @param session
	 * @return
	 */
	public long save(String session) {
		db = dbOpenHelper.getWritableDatabase();
		// db.execSQL("insert into sessionTable(session) values(?)",
		// new String[] { session });
		// db.close();
		ContentValues values = new ContentValues();
		values.put("session", session);
		long a = db.insert("sessionTable", null, values);
		db.close();

		return a;
	}

	/**
	 * 更新session
	 * 
	 * @param session
	 * @param id
	 * @return
	 */
	public long update(String session, String id) {
		db = dbOpenHelper.getWritableDatabase();
		// db.execSQL("update sessionTable set session=? where ID=?",
		// new Object[] { session, id });
		ContentValues values = new ContentValues();
		values.put("session", session);
		long a = db.update("sessionTable", values, "ID=?", new String[] { id });
		db.close();

		return a;
	}

	public String getSession() {
		db = dbOpenHelper.getWritableDatabase();
		String session="";
		Cursor cursor = db.rawQuery(
				"select session from sessionTable where ID=?",
				new String[] { "1" });
		if(cursor.moveToFirst()){
			
			session=cursor.getString(0);
		}
		cursor.close();
		db.close();
		return session;
	}

	/**
	 * 判断数据库中是否已有session 只在登录时使用
	 * 
	 * @param id
	 * @return
	 */
	public Boolean hasSession(String id) {
		db = dbOpenHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from sessionTable where ID=?",
				new String[] { id });

		// db.close(); 不关闭数据库 之后还有操作

		if (cursor.moveToFirst()) {// 判断游标是否为空
			cursor.close();
			db.close();
			return true;
		} else {
			cursor.close();
			db.close();
			return false;
		}

	}
}
