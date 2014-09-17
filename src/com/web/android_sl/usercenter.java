package com.web.android_sl;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class usercenter extends Activity  implements OnClickListener  {

//	private TextView textView1;
//	private TextView textView2;
//	private ImageView useIcon;
//	private ListView listView1;
//	private ProgressDialog dialog;
	
	/**
	 * 
	 */
	private myhome_fragment myhome_fragment;

	/**
	 * 
	 */
	private mycollect_fragment mycollect_fragment;

	/**
	 * 
	 */
	private myinfo_fragment myinfo_fragment;

	/**
	 * 
	 */
	private mydetail_fragment mydetail_fragment;

	/**
	 * 
	 */
	private View myhomeLayout;

	/**
	 * 
	 */
	private View mycollectLayout;

	/**
	 * 
	 */
	private View myinfoLayout;

	/**
	 * 
	 */
	private View mydetailLayout;

	/**
	 * 
	 */
	private ImageView myhomeImage;

	/**
	 * 
	 */
	private ImageView mycollectImage;

	/**
	 * 
	 */
	private ImageView myinfoImage;

	/**
	 *
	 */
	private ImageView mydetailImage;

	/**
	 *
	 */
	private TextView myhomeText;

	/**
	 * 
	 */
	private TextView mycollectText;

	/**
	 * 
	 */
	private TextView myinfoText;

	/**
	 * 
	 */
	private TextView mydetailText;

	/**
	 * 
	 */
	private FragmentManager fragmentManager;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		//重写注释  解决屏幕旋转时的覆盖bug
		//super.onSaveInstanceState(outState);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.usercenter);
		// 初始化布局元素
		initViews();
		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews() {
		myhomeLayout = findViewById(R.id.myhome_layout);
		mycollectLayout = findViewById(R.id.mycollect_layout);
		myinfoLayout = findViewById(R.id.myinfo_layout);
		mydetailLayout = findViewById(R.id.mydetail_layout);
		myhomeImage = (ImageView) findViewById(R.id.myhome_image);
		mycollectImage = (ImageView) findViewById(R.id.mycollect_image);
		myinfoImage = (ImageView) findViewById(R.id.myinfo_image);
		mydetailImage = (ImageView) findViewById(R.id.mydetail_image);
		myhomeText = (TextView) findViewById(R.id.myhome_text);
		mycollectText = (TextView) findViewById(R.id.mycollect_text);
		myinfoText = (TextView) findViewById(R.id.myinfo_text);
		mydetailText = (TextView) findViewById(R.id.mydetail_text);
		myhomeLayout.setOnClickListener(this);
		mycollectLayout.setOnClickListener(this);
		myinfoLayout.setOnClickListener(this);
		mydetailLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myhome_layout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.mycollect_layout:
			// 当点击了联系人tab时，选中第2个tab
			setTabSelection(1);
			break;
		case R.id.myinfo_layout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(2);
			break;
		case R.id.mydetail_layout:
			// 当点击了设置tab时，选中第4个tab
			setTabSelection(3);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		removeFragments(transaction);
		switch (index) {
		case 0:
			
			// 当点击了消息tab时，改变控件的图片和文字颜色
			myhomeImage.setImageResource(R.drawable.aa);
			myhomeText.setTextColor(Color.WHITE);
			if (myhome_fragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				myhome_fragment = new myhome_fragment();
				transaction.add(R.id.usercenter, myhome_fragment);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.replace(R.id.usercenter,new myhome_fragment());
			}
			break;
		case 1:
			// 当点击了联系人tab时，改变控件的图片和文字颜色
			mycollectImage.setImageResource(R.drawable.aa);
			mycollectText.setTextColor(Color.WHITE);
			if (mycollect_fragment == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				mycollect_fragment = new mycollect_fragment();
				transaction.add(R.id.usercenter, mycollect_fragment);
			} else {
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.replace(R.id.usercenter,new mycollect_fragment(),"contacts_tag");
			}
			break;
		case 2:
			// 当点击了动态tab时，改变控件的图片和文字颜色
			myinfoImage.setImageResource(R.drawable.aa);
			myinfoText.setTextColor(Color.WHITE);
			if (myinfo_fragment == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				myinfo_fragment = new myinfo_fragment();
				transaction.add(R.id.usercenter, myinfo_fragment);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.replace(R.id.usercenter,new myinfo_fragment());
			}
			break;
		case 3:
		default:
			// 当点击了设置tab时，改变控件的图片和文字颜色
			mydetailImage.setImageResource(R.drawable.aa);
			mydetailText.setTextColor(Color.WHITE);
			if (mydetail_fragment == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				mydetail_fragment = new mydetail_fragment();
				transaction.add(R.id.usercenter, mydetail_fragment);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.replace(R.id.usercenter,new mydetail_fragment());
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		myhomeImage.setImageResource(R.drawable.aa);
		myhomeText.setTextColor(Color.parseColor("#82858b"));
		mycollectImage.setImageResource(R.drawable.aa);
		mycollectText.setTextColor(Color.parseColor("#82858b"));
		myinfoImage.setImageResource(R.drawable.aa);
		myinfoText.setTextColor(Color.parseColor("#82858b"));
		mydetailImage.setImageResource(R.drawable.aa);
		mydetailText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 切换前移除碎片
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void removeFragments(FragmentTransaction transaction) {
		if (myhome_fragment != null) {
			transaction.remove(myhome_fragment);
		}
		if (mycollect_fragment != null) {
			transaction.remove(mycollect_fragment);
		}
		if (myinfo_fragment != null) {
			transaction.remove(myinfo_fragment);
		}
		if (mydetail_fragment != null) {
			transaction.remove(mydetail_fragment);
		}
	}
	
	

}
