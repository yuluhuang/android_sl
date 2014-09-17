package com.web.android_sl;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class mydetail_fragment extends Fragment {
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	View mydetailLayout = inflater.inflate(R.layout.mydetail_fragment,
			container, false);
	return mydetailLayout;
}
}
