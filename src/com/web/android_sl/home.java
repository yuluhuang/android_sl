package com.web.android_sl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.web.android_sl.domain.User;
import com.web.android_sl.http.HttpUtils;
import com.web.android_sl.json.JsonTools;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class home extends Activity {
	private TextView textView1;
	private TextView textView2;
	private ImageView imageView;
	private ProgressDialog dialog;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		imageView=(ImageView)findViewById(R.id.imageView1);
		dialog = new ProgressDialog(this);
		dialog.setTitle("提示");
		dialog.setMessage("正在加载，请稍后");
		dialog.setCancelable(false);// 点击屏幕也不消失
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
				// TODO Auto-generated method stub
				String info = HttpUtils.getUserInfo();
			    user = JsonTools.getUser(info);
				textView1.setText(user.getName());
				textView2.setText(user.getMotto());

				Log.i("string", info);
				
//			}
//		}).start();
		new MyTask().execute("");
	}

	public class MyTask extends AsyncTask<String, Integer, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			Bitmap bitmap = null;
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			InputStream inputStream = null;
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet("http://nsb-cdn.b0.upaiyun.com/avatars/16/5c/165c3edb63_medium.jpg");
			HttpResponse httpResponse;
			try {
				httpResponse = httpClient.execute(httpGet);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					inputStream = httpResponse.getEntity().getContent();
					long file_length=httpResponse.getEntity().getContentLength();
					int len=0;
					byte[] data =new byte[1024];
					int total_length=0;
					while((len=inputStream.read(data))!=-1){
						total_length+=len;
						int value=(int)((total_length/(float)file_length)*100);
						publishProgress(value);
						outputStream.write(data,0,len);
					}
					byte [] result=outputStream.toByteArray();
					bitmap=BitmapFactory.decodeByteArray(result, 0, result.length);
					
					
				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return bitmap;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			dialog.show();
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			imageView.setImageBitmap(result);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			dialog.setProgress(values[0]);
			
		}

	}

}
