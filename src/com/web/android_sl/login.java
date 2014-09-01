package com.web.android_sl;

import javax.security.auth.PrivateCredentialPermission;

import com.web.android_sl.http.HttpUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.test.PerformanceTestCase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Activity {

	private Button loginBtn = null;
	private EditText username = null;
	private EditText password = null;
	private CheckBox boxRem = null;

	private String name;
	private String pwd;
	private final static String path = "http://www.yuluhuang.com/ashx/LoginHandler.ashx?flag=login";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
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
					Toast.makeText(login.this, "保存成功", Toast.LENGTH_SHORT)
							.show();
				}
				new Thread(new Runnable() {

					@Override
					public void run() {
						HttpUtils.login(path, name, pwd);
					}
				}).start();
			}
		});

	}

	private void init() {
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
}
