package com.an.ajson.view;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.aiitec.openapi.view.ViewUtils;
import com.aiitec.openapi.view.annatation.ContentView;
import com.aiitec.openapi.view.annatation.Resource;
import com.aiitec.openapi.view.annatation.event.OnChildClick;
import com.aiitec.openapi.view.annatation.event.OnGroupCollapse;
import com.aiitec.openapi.view.annatation.event.OnGroupExpand;
import com.an.ajson.R;

public class Fragment2 extends Fragment {

	@Resource(R.id.exlv)
	private ExpandableListView exlv;
	
	private List<List<String>> allDatas;
	@Resource(stringArrayId=R.array.sanguo)
	private List<String> child1Datas;
	@Resource(stringArrayId=R.array.xiyouji)
	private List<String> child2Datas;
	@Resource(stringArrayId=R.array.shuihuzhuan)
	private List<String> child3Datas;
	@Resource(stringArrayId=R.array.hongloumeng)
	private List<String> child4Datas;
	private ExpandableAdapter adapter ;
	@ContentView(R.layout.fragment2)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = ViewUtils.inject(this);
		ViewGroup parent = (ViewGroup) view.getParent();  
		if (parent != null) {  
			parent.removeView(view);  
		} 
		allDatas = new ArrayList<List<String>>();
		allDatas.add(child1Datas);
		allDatas.add(child2Datas);
		allDatas.add(child3Datas);
		allDatas.add(child4Datas);
		
		adapter = new ExpandableAdapter(getActivity(), allDatas);
		exlv.setAdapter(adapter);
		return view;
	}
	@OnGroupCollapse(R.id.exlv)
	public void onGroupCollapse(int groupPosition) {
	    showToast("父item收缩了>>>"+groupPosition+":  "+adapter.getGroup(groupPosition));
	    
	}
	@OnGroupExpand(R.id.exlv)
	public void onGroupExpand(int groupPosition) {
		showToast("父item展开了>>>"+groupPosition+":  "+adapter.getGroup(groupPosition));
	}
//	@OnGroupClick(R.id.exlv)
//	public void onGroupClick(ExpandableListView parent, View v,
//			int groupPosition, long id) {
//		showToast("点击了父---item"+groupPosition+":  "+adapter.getGroup(groupPosition));
//	}
	@OnChildClick(R.id.exlv)
	public void onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		
		showToast("点击了子item"+childPosition+":  "+adapter.getGroup(groupPosition).get(childPosition));
	}
	private void showToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}
}
