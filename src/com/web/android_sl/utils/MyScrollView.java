package com.web.android_sl.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;
import com.web.android_sl.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

@SuppressLint("NewApi")
public class MyScrollView extends ScrollView implements OnTouchListener {

	/**
	 * 每页加载图片数量
	 */
	public static final int PAGE_SIZE = 15;

	/**
	 * 记录当前加载到第几页
	 */
	private int page;

	/**
	 * 每一列的宽度
	 */
	private int columWidth;

	/**
	 * 当前第一列的高度
	 */
	private int firstColumHeight;

	/**
	 * 当前第二列的高度
	 */
	private int secondColumHeight;

	/**
	 * 当前第三列的高度
	 */
	private int thirdColumHeight;

	/**
	 * 是否已加载过一次layout，这里onlayout中的初始化只需加载一次
	 */
	private boolean loadOnce;

	/**
	 * 对图片进行管理的工具类
	 */
	private ImageLoader imageLoader;

	/**
	 * 第一列布局
	 */
	private LinearLayout firstColumn;

	/**
	 * 第二列布局
	 */
	private LinearLayout secondColumn;

	/**
	 * 第三列布局
	 */
	private LinearLayout thirdColumn;

	/**
	 * 记录所有正在下载或等待下载的任务
	 */
	private static Set<LoadImageTask> taskCollection;

	/**
	 * 
	 */
	private static View scrollLayout;

	/**
	 * 
	 */
	private static int scrollViewHeight;

	/**
	 * 
	 */
	private static int lastScrollY = -1;

	private List<ImageView> imageViewList = new ArrayList<ImageView>();

	private static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			MyScrollView myScrollView = (MyScrollView) msg.obj;
			int scrollY = myScrollView.getScrollY();
			if (scrollY == lastScrollY) {
				if (scrollViewHeight + scrollY >= scrollLayout.getHeight()
						&& taskCollection.isEmpty()) {
					myScrollView.loadMoreImages();

				}
				myScrollView.checkVisibility();
			} else {
				lastScrollY = scrollY;
				Message message = new Message();
				message.obj = myScrollView;
				handler.sendMessageDelayed(message, 5);
			}

		};
	};

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		imageLoader = ImageLoader.getInstance();
		taskCollection = new HashSet<LoadImageTask>();
		setOnTouchListener(this);
		
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if (changed && !loadOnce) {
			scrollViewHeight = getHeight();
			scrollLayout = getChildAt(0);
			firstColumn = (LinearLayout) findViewById(R.id.first_column);
			secondColumn = (LinearLayout) findViewById(R.id.second_column);
			thirdColumn = (LinearLayout) findViewById(R.id.third_column);
			columWidth = firstColumn.getWidth();
			loadOnce = true;
			loadMoreImages();
		}
	}

	public boolean onTouch(View v, MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP) {
			Message message = new Message();
			message.obj = this;
			handler.sendMessageDelayed(message, 5);

		}
		return false;
	}

	public void loadMoreImages() {
		if (hasSDCard()) {
			int startIndex = page * PAGE_SIZE;
			int endIndex = page * PAGE_SIZE + PAGE_SIZE;
			if (startIndex < _Images.imageUrls.length) {
				Toast.makeText(getContext(), "正在加载...", Toast.LENGTH_SHORT)
						.show();
				if (endIndex > _Images.imageUrls.length) {
					endIndex = _Images.imageUrls.length;
				}
				for (int i = startIndex; i < endIndex; i++) {
					LoadImageTask task = new LoadImageTask();
					taskCollection.add(task);
					task.execute(_Images.imageUrls[i]);
				}
				page++;
			} else {
				Toast.makeText(getContext(), "已没有更多图片", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			Toast.makeText(getContext(), "未发现SD卡", Toast.LENGTH_SHORT).show();
		}
	}

	@SuppressLint("NewApi")
	public void checkVisibility() {
		for (int i = 0; i < imageViewList.size(); i++) {
			ImageView imageView = imageViewList.get(i);
			int borderTop = (Integer) imageView.getTag(R.string.border_top);

			int borderBottom = (Integer) imageView
					.getTag(R.string.border_bottom);
			if (borderBottom > getScaleY()
					&& borderTop < getScaleY() + scrollViewHeight) {
				String imageUrl = (String) imageView.getTag(R.string.image_url);
				Bitmap bitmap = imageLoader.getBitmaoFromMemoryCache(imageUrl);
				if (bitmap != null) {
					imageView.setImageBitmap(bitmap);
				} else {
					LoadImageTask task = new LoadImageTask(imageView);
					task.execute(imageUrl);
				}
			} else {
				imageView.setImageResource(R.drawable.aa);
			}

		}
	}

	private boolean hasSDCard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());

	}

	class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

		private String mImageUrl;

		private ImageView mImageView;

		public LoadImageTask() {
			// TODO Auto-generated constructor stub
		}

		public LoadImageTask(ImageView imageView) {
			this.mImageView = imageView;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			mImageUrl = params[0];
			Bitmap imageBitmap = imageLoader
					.getBitmaoFromMemoryCache(mImageUrl);
			if (imageBitmap == null) {
				imageBitmap = loadImage(mImageUrl);
			}
			return imageBitmap;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			// TODO Auto-generated method stub
			super.onPostExecute(bitmap);
			if (bitmap != null) {
				double ratio = bitmap.getWidth() / (columWidth * 1.0);
				int scaleHeight = (int) (bitmap.getHeight() / ratio);
				addImage(bitmap, columWidth, scaleHeight);
			}
			taskCollection.remove(this);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		private Bitmap loadImage(String imageUrl) {
			File imageFile = new File(getImagePath(imageUrl));
			if (!imageFile.exists()) {
				downloadImage(imageUrl);
			}
			if (imageUrl != null) {
				Bitmap bitmap = ImageLoader.decodeSampledBitmapFromResource(
						imageFile.getPath(), columWidth);
				if (bitmap != null) {
					imageLoader.addBitmapToMemoryCache(imageUrl, bitmap);
					return bitmap;
				}
			}
			return null;
		}

		private void addImage(Bitmap bitmap, int imageWidth,
				int imageHeight) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					imageWidth, imageHeight);
			if (mImageView != null) {
				mImageView.setImageBitmap(bitmap);
			} else {
				ImageView imageView = new ImageView(getContext());
				imageView.setLayoutParams(params);
				imageView.setImageBitmap(bitmap);
				imageView.setScaleType(ScaleType.FIT_XY);
				imageView.setPadding(5, 5, 5, 5);
				imageView.setTag(R.string.image_url, mImageUrl);
				findColumnToAdd(imageView, imageHeight).addView(imageView);
				imageViewList.add(imageView);
			}
		}

		private LinearLayout findColumnToAdd(ImageView imageView,
				int imageHeight) {
			// TODO Auto-generated method stub
			if(firstColumHeight<=secondColumHeight){
				if(firstColumHeight<thirdColumHeight){
					imageView.setTag(R.string.border_top,firstColumHeight);
					firstColumHeight+=imageHeight;
					imageView.setTag(R.string.border_bottom,firstColumHeight);
					return firstColumn;
				}
				imageView.setTag(R.string.border_top,thirdColumHeight);
				thirdColumHeight+=imageHeight;
				imageView.setTag(R.string.border_bottom,thirdColumHeight);
				return thirdColumn;
			}else{
				if(secondColumHeight<=thirdColumHeight){
					imageView.setTag(R.string.border_top,secondColumHeight);
					secondColumHeight+=imageHeight;
					imageView.setTag(R.string.border_bottom,secondColumHeight);
					return secondColumn;
				}
				imageView.setTag(R.string.border_top,thirdColumHeight);
				thirdColumHeight+=imageHeight;
				imageView.setTag(R.string.border_bottom,thirdColumHeight);
				return thirdColumn;
			}
		}
		
		private void downloadImage(String imageUrl){
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				Log.i("string","monted sdcard");
				
			}else {
				Log.i("string","has no sdcard");
			}
			
			HttpsURLConnection connection=null;
			FileOutputStream fos=null;
			BufferedOutputStream bos=null;
			BufferedInputStream bis=null;
			File imageFile=null;
			try{
				URL url=new URL(imageUrl);
				connection=(HttpsURLConnection)url.openConnection();
				connection.setConnectTimeout(5*1000);
				connection.setReadTimeout(15*1000);
				connection.setDoInput(true);
				connection.setDoOutput(true);
				bis=new BufferedInputStream(connection.getInputStream());
				imageFile=new File(getImagePath(imageUrl));
				fos=new FileOutputStream(imageFile);
				bos=new BufferedOutputStream(fos);
				byte [] b=new byte[1024];
				int length;
				while((length=bis.read(b))!=-1){
					bos.write(b,0,length);
					bos.flush();
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
					if(bis!=null){
						bis.close();
					}
					if(bos!=null){
						bos.close();
					}
					if(connection!=null){
						connection.disconnect();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if(imageFile!=null){
				Bitmap bitmap=imageLoader.decodeSampledBitmapFromResource(imageFile.getPath(), columWidth);
				if(bitmap!=null){
					imageLoader.addBitmapToMemoryCache(imageUrl, bitmap);
				}
			}
		}
		
		
		private String getImagePath(String imageUrl){
			int lastSlashIndex=imageUrl.lastIndexOf("/");
			String imageName=imageUrl.substring(lastSlashIndex+1);
			String imageDir=Environment.getExternalStorageDirectory().getPath()+"/sl/fall";
			File file=new File(imageDir);
			if(!file.exists()){
				file.mkdir();
			}
			String imagePath=imageDir+imageName;
			return imagePath;
		}
		
	}
}
