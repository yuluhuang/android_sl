package com.web.android_sl.json;

import org.json.JSONException;
import org.json.JSONObject;

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
}
