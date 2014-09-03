package com.web.android_sl.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.web.android_sl.login;
import com.web.android_sl.domain.User;

import android.R.string;
import android.util.Log;

public class JsonTools {

	public static String getKey(String key, String jsonObject) {

		String value = "";
		try {
			JSONObject object = new JSONObject(jsonObject);
			value = object.getString(key);
			Log.i("key", value);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public static User getUser(String info) {
		// TODO Auto-generated method stub
		User user = new User();
		try {
			JSONObject jsonObject1 = new JSONObject(info);
			String data1 = jsonObject1.getString("data");
			Log.i("string", data1);
			JSONObject jsonObject2 = new JSONObject(data1);
			String data2 = jsonObject2.getString("user");
			Log.i("string", data2);
			
			JSONArray jsonArray=new JSONArray(data2);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject3 = jsonArray.getJSONObject(i);
				user.setName(jsonObject3.getString("name"));
				user.setIcon(jsonObject3.getString("icon"));
				user.setIntroduction(jsonObject3.getString("introduction"));
				user.setMotto(jsonObject3.getString("motto"));
				user.setIndentity(jsonObject3.getString("indentity"));
				user.setPhone(jsonObject3.getString("phone"));
				user.setQQ(jsonObject3.getString("QQ"));
			}
		
			
		
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
}
