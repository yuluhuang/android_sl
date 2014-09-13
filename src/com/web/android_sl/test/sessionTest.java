package com.web.android_sl.test;

import java.util.List;

import com.web.android_sl.task;
import com.web.android_sl.db.DbOpenHelper;
import com.web.android_sl.db.SessionService;
import com.web.android_sl.domain.Task;
import com.web.android_sl.http.HttpUtils;
import com.web.android_sl.json.JsonTools;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;
import junit.framework.TestCase;

public class sessionTest extends AndroidTestCase {

	public void testSave() throws Exception{
		SessionService service=new SessionService(getContext());
		DbOpenHelper dbOpenHelper=new DbOpenHelper(getContext());
		dbOpenHelper.getWritableDatabase();
		service.save("123456789");
		
	}
	
	public void task() {
		String taskinfo = HttpUtils.getTaskInfo("1");// 从服务器获取数据
		List<Task> taskList = JsonTools.getTask(taskinfo);// task对象数组
	}
	

}
