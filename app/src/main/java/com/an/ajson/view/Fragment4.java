package com.an.ajson.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aiitec.openapi.utils.AiiUtil;
import com.aiitec.openapi.view.ViewUtils;
import com.aiitec.openapi.view.annatation.ContentView;
import com.aiitec.openapi.view.annatation.Resource;
import com.aiitec.openapi.view.annatation.event.OnClick;
import com.aiitec.openapi.view.annatation.event.OnCompoundButtonCheckedChange;
import com.aiitec.openapi.view.annatation.event.OnRadioGroupCheckedChange;
import com.aiitec.openapi.view.annatation.event.OnTouch;
import com.an.ajson.R;

public class Fragment4 extends Fragment {

	@Resource(R.id.tv_log)
	private TextView tv_log;
	
	@Resource(R.id.tv_touch)
	private TextView tv_touch;
	
	@Resource(R.id.radioGroup)
	private RadioGroup radioGroup;
	@Resource(R.id.radioButton1)
	private RadioButton radioButton1;
	@Resource(R.id.radioButton2)
	private RadioButton radioButton2;
	@Resource(R.id.radioButton3)
	private RadioButton radioButton3;
	
	private int screenWidth ;
	private int topPading = 0;
	private int bottomPading = 1000;
	
	@ContentView(R.layout.fragment4)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		screenWidth = AiiUtil.getScreenWidth(getActivity());
//		int screenHeight = AiiUtil.getScreenHeight(getActivity());
		
//		topPading = (int) (screenHeight / 30 * 3);
//		bottomPading = (int) (screenHeight - topPading / 2);
		View view = ViewUtils.inject(this);
		ViewGroup parent = (ViewGroup) view.getParent();  
		if (parent != null) {  
			parent.removeView(view);  
		} 
		Log.e("aiitec", "onCreateView");
		return view ;
		
	}
	@OnCompoundButtonCheckedChange(ids={R.id.radioButton1,R.id.radioButton2, R.id.radioButton3})
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(buttonView == radioButton1){
			tv_log.append("选项1"+(isChecked?"开":"关")+"\n");
		} 
		else if(buttonView == radioButton2){
			tv_log.append("选项2"+(isChecked?"开":"关")+"\n");
		}
		else if(buttonView == radioButton3){
			tv_log.append("选项3"+(isChecked?"开":"关")+"\n");
		}
	}
	@OnRadioGroupCheckedChange(R.id.radioGroup)
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		tv_log.append("RadioGroup按下监听:"+checkedId+"\n");
	} 
	@OnClick(R.id.tv_log)
	public void doClear(View view) {
		tv_log.setText("");
	}
	private int lastX, lastY;
	@OnTouch(R.id.tv_touch)
	public boolean onTouch(View v, MotionEvent event) {
		Log.e("aiitec", "action:"+event.getAction()+"  x:"+event.getRawX()+"  y:"+event.getRawY());
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
//			startTime = System.currentTimeMillis();
			lastX = (int) event.getRawX();
			lastY = (int) event.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			int dx = (int) event.getRawX() - lastX;
			int dy = (int) event.getRawY() - lastY;

			int left = v.getLeft() + dx;
			int top = v.getTop() + dy;
			int right = v.getRight() + dx;
			int bottom = v.getBottom() + dy;

			if (left < 0) { // 防止靠左超出屏幕
				left = 0;
				right = left + v.getWidth();
			}

			if (right > screenWidth) { // 防止靠右超出屏幕
				right = screenWidth;
				left = right - v.getWidth();
			}

			if (top < topPading) { // 防止靠上超出屏幕
				top = topPading;
				bottom = top + v.getHeight();
			}

			if (bottom > bottomPading) { // 防止靠下超出规定范围
				bottom = bottomPading;
				top = bottom - v.getHeight();
			}

			v.layout(left, top, right, bottom);

			lastX = (int) event.getRawX();
			lastY = (int) event.getRawY();

			break;
		case MotionEvent.ACTION_UP:
//			endTime = System.currentTimeMillis();
//			if (endTime - startTime < 200) {
//				touchHandler.sendEmptyMessage(1);
//			}
			break;
		}
		return true;
	}
}
