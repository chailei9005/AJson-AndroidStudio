package com.an.ajson.view;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.an.ajson.R;
import com.an.ajson.adapter.CommonAdapter;
import com.an.ajson.adapter.ViewHolder;


public class ListAdapter extends CommonAdapter<String> {

	public ListAdapter(Context context, List<String> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, String item) {
//		ImageView iv = helper.getView(R.id.imageview);
		TextView tv = helper.getView(R.id.textview);
		tv.setText(item);
	}
	
}
