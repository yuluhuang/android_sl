package com.web.android_sl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.web.android_sl.domain.Item;
import com.web.android_sl.domain.Task;
import com.web.android_sl.http.HttpUtils;
import com.web.android_sl.json.JsonTools;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SimpleAdapter;
import android.widget.VideoView;
import android.widget.AdapterView.OnItemClickListener;

public class item extends Activity {

	private ProgressDialog dialog;
	private String taskID;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item);
		Intent intent = getIntent();
		taskID = intent.getStringExtra("taskID");

		listView = (ListView) findViewById(R.id.listView1);
		dialog = new ProgressDialog(this);
		dialog.setTitle("提示");
		dialog.setMessage("正在加载，请稍后");
		dialog.setCancelable(false);// 点击屏幕也不消失

		new myTask().execute();
	}

	public class myTask extends AsyncTask<String, Void, List<Item>> {

		@Override
		protected List<Item> doInBackground(String... params) {
			// TODO Auto-generated method stub
			String info = HttpUtils.getItemInfo(taskID);
			List<Item> lists = JsonTools.getItemList(info);
			return lists;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected void onPostExecute(List<Item> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			List<HashMap<String, String>> iteMaps = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> hashMap = new HashMap<String, String>();
			for (Item item : result) {
				hashMap.put("oldName", item.getOldName());
				hashMap.put("path1", item.getPath());
				hashMap.put("title", item.getTitle());
				hashMap.put("remark", item.getRemark());
				iteMaps.add(hashMap);
			}

			SimpleAdapter simpleAdapter = new SimpleAdapter(item.this, iteMaps,
					R.layout.item_item, new String[] { "oldName", "path1" },
					new int[] { R.id.textView1 });
			listView.setAdapter(simpleAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					@SuppressWarnings("unchecked")
					HashMap<String, String> o = (HashMap<String, String>) parent
							.getItemAtPosition(position);

					String t = o.get("path1");

					Intent intent = new Intent(item.this, videoPlayer.class);
					intent.putExtra("url", t);
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
