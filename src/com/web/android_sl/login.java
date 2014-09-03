package com.web.android_sl;

import javax.security.auth.PrivateCredentialPermission;

import com.web.android_sl.db.SessionService;
import com.web.android_sl.http.HttpUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class login extends Activity {

	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			//byte[]data=(byte[])msg.obj;
			//Bitmap bitmap=BitmapFactory.decodeByteArray(data, 0, data.length);
			//ImageView.setImageBitmap(bitmap);
			if(msg.what==0){
				Toast.makeText(getApplicationContext(), "登录成功", 1).show();

				Intent intent = new Intent(login.this, index.class);
				startActivity(intent);
			}
		};
		
	};
	
	private SessionService sessionService;
	private Button loginBtn = null;
	private EditText username = null;
	private EditText password = null;
	private CheckBox boxRem = null;

	private String name;
	private String pwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
		sessionService=new SessionService(getApplicationContext());
		init();
		loginBtn = (Button) findViewById(R.id.login);

		// TODO Auto-generated method stub
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				name = username.getText().toString();
				pwd = password.getText().toString();
				if (boxRem.isChecked()) {
					SharedPreferences sharedPreferences = getSharedPreferences(
							"login", MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString("name", name);
					editor.putString("pwd", pwd);
					editor.putBoolean("flag", boxRem.isChecked());
					editor.commit();
				}

				
				new Thread(new Runnable() {

					@Override
					public void run() {

						boolean a=HttpUtils.login(name, pwd);// session
						if (a) {
							Message message=Message.obtain();
							//message.obj=databaseList();
							message.what=0;
							handler.sendMessage(message);
						}
					}
				}).start();		
			}
		});
	}

	private void init() {
		isConnect();
		String name = "";
		String pwd = "";
		Boolean flag = false;
		SharedPreferences preferences = getSharedPreferences("login",
				MODE_PRIVATE);
		if (preferences != null) {
			name = preferences.getString("name", "");
			pwd = preferences.getString("pwd", "");
			flag = preferences.getBoolean("flag", false);

			boxRem = (CheckBox) findViewById(R.id.checkBox1);
			boxRem.setChecked(flag);
			username = (EditText) findViewById(R.id.username);
			username.setText(name);
			password = (EditText) findViewById(R.id.password);
			password.setText(pwd);
		}
	}

	public void isConnect() {
		// 获取当前的网络连接服务
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取活动的网络连接信息
		NetworkInfo info = connMgr.getActiveNetworkInfo();
		// 判断
		if (info != null || info.isAvailable()) {

		} else {
			Toast.makeText(getApplicationContext(), "网络不可用", 1).show();
		}

	}
}
