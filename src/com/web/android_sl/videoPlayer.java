package com.web.android_sl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.web.android_sl.task.MyTask;
import com.web.android_sl.domain.Item;
import com.web.android_sl.http.HttpUtils;
import com.web.android_sl.json.JsonTools;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.VideoView;

public class videoPlayer extends Activity implements
		MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
	// http://yuluhuang.qiniudn.com/MIRROR.mp4
	public static final String TAG = "play";
	private VideoView mVideoView;
	private Uri mUri;
	private int mPositionEhrnPaused = -1;
	private ProgressDialog dialog;
	private String taskID;
	private MediaController mMediaController;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item);
		Intent intent = getIntent();
		taskID = intent.getStringExtra("taskID");
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		mVideoView = (VideoView) findViewById(R.id.videoView1);
		listView = (ListView) findViewById(R.id.listView1);
		dialog = new ProgressDialog(this);
		dialog.setTitle("提示");
		dialog.setMessage("正在加载，请稍后");
		dialog.setCancelable(false);// 点击屏幕也不消失

		// mUri = Uri.parse(intent.getStringExtra("taskID"));

		// String filename="";
		// File videoFile=new
		// File(Environment.getExternalStorageDirectory(),filename);
		// String path=videoFile.getAbsolutePath();
		// mUri = Uri.parse("file:///sdcard/romanticbreaker");

		mMediaController = new MediaController(this);
		mVideoView.setMediaController(mMediaController);
		mVideoView.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				videoPlayer.this.finish();
			}
		});
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		videoPlayer.this.finish();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onStart() {
		if (mVideoView != null && mUri != null) {
			mVideoView.setVideoURI(mUri);
			mVideoView.start();
		} else {
			Toast.makeText(videoPlayer.this, "发生错误", Toast.LENGTH_SHORT).show();
		}
		super.onStart();
	}

	@Override
	public void onPause() {
		mPositionEhrnPaused = mVideoView.getCurrentPosition();
		mVideoView.stopPlayback();
		super.onPause();

	}

	@Override
	public void onResume() {
		if (mPositionEhrnPaused >= 0) {
			mVideoView.seekTo(mPositionEhrnPaused);
			mPositionEhrnPaused = -1;

		}
		super.onResume();
	}

	
}
