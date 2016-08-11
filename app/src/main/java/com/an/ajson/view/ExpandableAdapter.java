package com.an.ajson.view;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.an.ajson.R;


public class ExpandableAdapter extends BaseExpandableListAdapter {

	private List<List<String>> datas ;
	private Context context;
	private String[] parentDatas;
	public ExpandableAdapter(Context context, List<List<String>> datas) {
		this.datas = datas;
		this.context = context;
		parentDatas = context.getResources().getStringArray(R.array.book);
	}
	@Override
	public String getChild(int groupPosition, int childPosition) {
		
		String child = datas.get(groupPosition).get(childPosition);
		return child;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.child, null);
		
		TextView tv = (TextView) view.findViewById(R.id.tv_child);
		tv.setText("子选项：  "+datas.get(groupPosition).get(childPosition));
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return datas.get(groupPosition).size();
	}

	@Override
	public List<String> getGroup(int groupPosition) {
		return datas.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return datas.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView tv = new TextView(context);
		String text = parentDatas[groupPosition];
		tv.setText("父选项：  "+text);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		tv.setTextColor(Color.RED);
		tv.setPadding(24, 24, 24, 24);
		return tv;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	
	

  
}
