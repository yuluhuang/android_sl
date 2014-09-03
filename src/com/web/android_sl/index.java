package com.web.android_sl;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class index extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		/*
         * 
         * add()方法的四个参数，依次是：
         * 
         * 1、组别，如果不分组的话就写Menu.NONE,
         * 
         * 2、Id，这个很重要，Android根据这个Id来确定不同的菜单
         * 
         * 3、顺序，那个菜单现在在前面由这个参数的大小决定
         * 
         * 4、文本，菜单的显示文本
         */
		menu.add(Menu.NONE, 1, 1, "用户中心");
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		int id=item.getItemId();
		switch (id) {
		case 1:
			Intent intent=new Intent(index.this,home.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	

}
