package com.web.android_sl;

import java.util.List;

import com.web.android_sl.domain.Theme;
import com.web.android_sl.domain.User;
import com.web.android_sl.home.MyTask;
import com.web.android_sl.http.HttpUtils;
import com.web.android_sl.json.JsonTools;
import com.web.android_sl.myAdapter.themeAdapter;
import com.web.android_sl.utils.ContextUtil;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class myhome_fragment extends Fragment {

	private TextView textView1;
	private TextView textView2;
	private ImageView useIcon;
	private ListView listView1;
	private ProgressDialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myhomeLayout = inflater.inflate(R.layout.myhome_fragment,
				container, false);
		// 初始化个人信息
		textView1 = (TextView) myhomeLayout.findViewById(R.id.textView1);
		textView2 = (TextView) myhomeLayout.findViewById(R.id.textView2);
		useIcon = (ImageView) myhomeLayout.findViewById(R.id.useIcon);
		listView1 = (ListView) myhomeLayout.findViewById(R.id.listView1);
		// 提示 设置
		dialog = new ProgressDialog(getActivity());
		dialog.setTitle("提示");
		dialog.setMessage("正在加载，请稍后");
		dialog.setCancelable(false);// 点击屏幕也不消失
		new MyTask().execute();

		return myhomeLayout;
	}

	public class MyTask extends AsyncTask<String, Void, User> {

		@Override
		protected User doInBackground(String... params) {
			String info = HttpUtils.getUserInfo();// 获得用户信息
			User user = JsonTools.getUser(info);// 转成对象

			return user;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected void onPostExecute(User user) {
			// TODO Auto-generated method stub
			super.onPostExecute(user);

			// 填充用户信息
			textView1.setText(user.getName().toString());
			textView2.setText(user.getMotto().toString());

			// 获取适配器数据 用户创建的theme 列表
			final List<Theme> themeList = user.getTheme();
			themeAdapter adapter = new themeAdapter(getActivity(), themeList,
					R.layout.home_bottom);

			listView1.setAdapter(adapter);

			listView1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub

					Theme theme = themeList.get(position);
					String title = theme.getThemeID();
					Intent intent = new Intent(getActivity(), task.class);
					intent.putExtra("themeID", title);
					// Toast.makeText(getApplicationContext(), title,3).show();
					startActivity(intent);

				}
			});

			useIcon.setImageBitmap(user.getIcon());
			dialog.dismiss();
		}
	}
}
