package com.web.android_sl.json;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.web.android_sl.domain.Task;
import com.web.android_sl.domain.Theme;
import com.web.android_sl.domain.User;
import com.web.android_sl.http.HttpUtils;
import android.graphics.Bitmap;

public class JsonTools {

	public static String getKey(String key, String jsonObject) {

		String value = "";
		try {
			JSONObject object = new JSONObject(jsonObject);
			value = object.getString(key);
			// Log.i("key", value);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * home.java 将用于信息转为对象
	 * 
	 * @param info
	 * @return
	 */
	public static User getUser(String info) {
		// TODO Auto-generated method stub
		User user = new User();
		try {
			JSONObject jsonObject1 = new JSONObject(info);
			String data1 = jsonObject1.getString("data");
			// Log.i("string", data1);
			JSONObject jsonObject2 = new JSONObject(data1);
			String data2 = jsonObject2.getString("user");
			// Log.i("string", data2);

			JSONArray jsonArray = new JSONArray(data2);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject3 = jsonArray.getJSONObject(i);
				user.setName(jsonObject3.getString("name"));
				String icon = jsonObject3.getString("icon");
				Bitmap bitmap = null;
				//是否有图片
				if (icon.length() > 0) {
					String iconPath = HttpUtils.getIconPath(icon);// 获得下载token
					bitmap = HttpUtils.getBitmapForIconPath(iconPath);// 下载
				}
				user.setIcon(bitmap);
				user.setIntroduction(jsonObject3.getString("introduction"));
				user.setMotto(jsonObject3.getString("motto"));
				user.setIndentity(jsonObject3.getString("indentity"));
				user.setPhone(jsonObject3.getString("phone"));
				user.setQQ(jsonObject3.getString("QQ"));

				user.setTheme(getThemeList(jsonObject3.getString("theme")));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * 将theme转为对象 返回list数组
	 * 
	 * @param themeString
	 * @return
	 */
	public static List<Theme> getThemeList(String themeString) {
		JSONArray jsonArray;
		List<Theme> themelistList = new ArrayList<>();
		try {

			jsonArray = new JSONArray(themeString);
			for (int i = 0; i < jsonArray.length(); i++) {
				Theme theme = new Theme();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String icon = jsonObject.getString("icon");
				Bitmap bitmap = null;
				if (icon.length() > 0) {
					String iconPath = HttpUtils.getIconPath(icon);// 获得下载token
					bitmap = HttpUtils.getBitmapForIconPath(iconPath);// 下载
				}
				theme.setIcon(bitmap);
				theme.setThemeName(jsonObject.getString("themeName"));
				theme.setRemark(jsonObject.getString("remark"));
				theme.setThemeID(jsonObject.getString("themeID"));
				themelistList.add(theme);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return themelistList;

	}

	/**
	 * home.java 获取适配器数据源
	 * 
	 * @param themeList
	 * @return
	 */
	// public static List<HashMap<String, Object>> getListThemes(List<Theme>
	// themeList) {
	//
	// List<HashMap<String, Object>> themelist = new ArrayList<HashMap<String,
	// Object>>();
	// HashMap<String, Object> themeMap = new HashMap<String, Object>();
	//
	// try {
	// for(Theme theme :themeList){
	// themeMap.put("icon", theme.getIcon());
	// themeMap.put("themeName", theme.getThemeName());
	// themeMap.put("remark", theme.getRemark());
	// themeMap.put("themeID", theme.getThemeID());
	// themelist.add(themeMap);
	//
	// }
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return themelist;
	// }

	/**
	 * 将task字符串转为对象 返回list数组
	 * 
	 * @param taskinfo
	 * @return
	 */
	public static List<Task> getTask(String taskinfo) {
		// TODO Auto-generated method stub
		JSONArray jsonArray;
		List<Task> taskList = new ArrayList<>();
		try {

			jsonArray = new JSONArray(taskinfo);
			for (int i = 0; i < jsonArray.length(); i++) {
				Task task = new Task();
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				String icon = jsonObject.getString("icon");
				Bitmap bitmap = null;

				if (icon.length() > 0) {
					String iconPath = HttpUtils.getIconPath(icon);// 获得下载token

					bitmap = HttpUtils.getBitmapForIconPath(iconPath);// 下载

				}
				task.setIcon(bitmap);
				task.setTaskName(jsonObject.getString("taskName"));
				task.setRemark(jsonObject.getString("remark"));
				task.setTaskID(jsonObject.getString("taskID"));
				taskList.add(task);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return taskList;
	}

	// public static List<HashMap<String, Object>> getTaskList(List<Task>
	// taskList) {
	// // TODO Auto-generated method stub
	// List<HashMap<String , Object>> list=new
	// ArrayList<HashMap<String,Object>>();
	// HashMap<String , Object> taskMap=new HashMap<String ,Object>();
	//
	// for(Task task: taskList){
	// taskMap.put("icon", task.getIcon());
	// taskMap.put("taskName", task.getTaskName());
	// taskMap.put("remark", task.getRemark());
	// taskMap.put("taskID", task.getTaskID());
	// list.add(taskMap);
	// }
	// return list;
	// }
}
