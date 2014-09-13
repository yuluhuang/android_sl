package com.web.android_sl.myAdapter;

import java.util.List;

import com.web.android_sl.R;
import com.web.android_sl.domain.Task;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class myAdapter extends BaseAdapter {

	private List<Task> taskList;
	private int resource;
	private LayoutInflater mInflater = null;

	public myAdapter(Context context, List<Task> taskList, int resource) {
		this.taskList = taskList;
		this.resource = resource;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return taskList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return taskList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		// 如果缓存convertView为空，则需要创建View
		if (convertView == null) {
			holder = new ViewHolder();
			// 根据自定义的Item布局加载布局
			convertView = mInflater.inflate(resource, null);
			holder.img = (ImageView) convertView.findViewById(R.id.imageView1);
			holder.title = (TextView) convertView.findViewById(R.id.textView2);
			holder.content = (TextView) convertView
					.findViewById(R.id.textView1);
			// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Task task = taskList.get(position);

		holder.img.setImageBitmap(task.getIcon());

		holder.title.setText(task.getTaskName());
		holder.content.setText(task.getRemark());
		return convertView;
	}

	private final class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView content;
	}

}
