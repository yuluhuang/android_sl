package com.web.android_sl.db;

import java.net.ContentHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SessionService {
	private DbOpenHelper dbOpenHelper;

	public SessionService(Context context) {
		this.dbOpenHelper = new DbOpenHelper(context);
	}

	public long save(String session) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		// db.execSQL("insert into sessionTable(session) values(?)",
		// new String[] { session });
		// db.close();
		ContentValues values = new ContentValues();
		values.put("session", session);
		long a=db.insert("sessionTable", null, values);
		db.close();
		return a;
	}

	public long update(String session, String id) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
//		db.execSQL("update sessionTable set session=? where ID=?",
//				new Object[] { session, id });
		ContentValues values = new ContentValues();
		values.put("session", session);
		long a=db.update("sessionTable", values, "ID=?", new String[]{id});
		db.close();
		return a;
	}
}
