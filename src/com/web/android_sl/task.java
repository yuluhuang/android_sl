package com.web.android_sl;

import java.util.List;
import com.web.android_sl.domain.Task;
import com.web.android_sl.http.HttpUtils;
import com.web.android_sl.json.JsonTools;
import com.web.android_sl.myAdapter.myAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class task extends Activity {
	private String themeID;
	private ListView listView;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task);
		Intent intent = getIntent();
		themeID = intent.getStringExtra("themeID");
		Log.i("string", themeID);
		listView = (ListView) findViewById(R.id.listView1);
		// 提示 设置
		dialog = new ProgressDialog(this);
		dialog.setTitle("提示");
		dialog.setMessage("正在加载，请稍后");
		dialog.setCancelable(false);// 点击屏幕也不消失
		new MyTask().execute();
	}

	public class MyTask extends AsyncTask<String, Void, List<Task>> {

		@Override
		protected List<Task> doInBackground(String... params) {
			// TODO Auto-generated method stub
			String taskinfo = HttpUtils.getTaskInfo(themeID);// 从服务器获取数据
			List<Task> taskList = JsonTools.getTask(taskinfo);// task对象数组
			return taskList;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected void onPostExecute(final List<Task> taskList) {
			// TODO Auto-generated method stub
			super.onPostExecute(taskList);
			myAdapter adapter = new myAdapter(task.this, taskList,
					R.layout.home_bottom);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Task t=taskList.get(position);
					String taskID=t.getTaskID();
					Intent intent = new Intent(task.this,fallpic.class);
					startActivity(intent);
				}
				
			});
			dialog.dismiss();

		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
	}
}
