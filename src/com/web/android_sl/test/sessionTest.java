package com.web.android_sl.test;

import com.web.android_sl.db.DbOpenHelper;
import com.web.android_sl.db.SessionService;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import junit.framework.TestCase;

public class sessionTest extends AndroidTestCase {

	public void testSave() throws Exception{
		SessionService service=new SessionService(getContext());
		DbOpenHelper dbOpenHelper=new DbOpenHelper(getContext());
		dbOpenHelper.getWritableDatabase();
		service.save("123456789");
		
	}
}
