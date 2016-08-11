package com.an.ajson.view;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.aiitec.openapi.view.ViewUtils;
import com.aiitec.openapi.view.annatation.ContentView;
import com.aiitec.openapi.view.annatation.Resource;
import com.aiitec.openapi.view.annatation.event.OnItemClick;
import com.aiitec.openapi.view.annatation.event.OnItemLongClick;
import com.aiitec.openapi.view.annatation.event.OnLongClick;
import com.aiitec.openapi.view.annatation.event.OnScroll;
import com.an.ajson.R;

public class Fragment1 extends Fragment {

	@Resource(R.id.lv1)
	private ListView lv;
	private ListAdapter adapter;
	@Resource(stringArrayId= R.array.listdata)
	private List<String> mDatas;
	@Resource(R.id.btn_top)
	private Button btn_top;
	@ContentView(R.layout.fragment1)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = ViewUtils.inject(this) ;
		ViewGroup parent = (ViewGroup) view.getParent();  
		if (parent != null) {  
			parent.removeView(view);  
		} 
		 
		adapter = new ListAdapter(getActivity(), mDatas, R.layout.item_fragment_view);
		lv.setAdapter(adapter);
		return view;
		
	}

	@OnItemClick(R.id.lv1)
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
		showToast("点击了item"+arg2+":  "+adapter.getItem(arg2));
	}
	@OnItemLongClick(R.id.lv1)
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
			int arg2, long arg3) {

		showToast("长按了item"+arg2+":--------  "+adapter.getItem(arg2));
		return false;
	}
	@OnLongClick(R.id.btn_top)
	public void onLongClick(View v) {
		showToast("长按了按钮回到顶部");
		lv.setSelection(0);
		
	}
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		Log.e("aiitec", "onScrollStateChanged   AbsListView:"+view+"   scrollState："+scrollState);
	}
	@OnScroll(R.id.lv1)
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		Log.e("aiitec", "onScroll   AbsListView:"+view+"   firstVisibleItem："+firstVisibleItem+"   visibleItemCount："+visibleItemCount+"   totalItemCount："+totalItemCount);
		
	}
	
	private void showToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}
//	@OnItemSelected(R.id.lv1)
//	public void onItemSelected(AdapterView<?> arg0, View arg1,
//			int arg2, long arg3) {
//		showToast("选中了"+arg2+"   "+adapter.getItem(arg2));
//	}
//	public void onNothingSelected(AdapterView<?> arg0) {
//		showToast("onNothingSelected");
//	}
}
