package com.an.ajson.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.aiitec.openapi.view.ViewUtils;
import com.aiitec.openapi.view.annatation.ContentView;
import com.aiitec.openapi.view.annatation.Resource;
import com.aiitec.openapi.view.annatation.event.OnCompoundButtonCheckedChange;
import com.aiitec.openapi.view.annatation.event.OnFocusChange;
import com.aiitec.openapi.view.annatation.event.OnSeekBarChange;
import com.aiitec.openapi.view.annatation.event.OnTextChanged;
import com.an.ajson.R;

public class Fragment3 extends Fragment {

	@Resource(R.id.seekbar)
	private SeekBar seekbar;
	@Resource(R.id.tv_value)
	private TextView tv_value;
	@Resource(R.id.checkbox1)
	private CheckBox checkbox1;
	@Resource(R.id.checkbox2)
	private CheckBox checkbox2;
	@Resource(R.id.checkbox3)
	private CheckBox checkbox3;
	@Resource(R.id.toggleButton1)
	private ToggleButton toggleButton1;
	@Resource(R.id.et1)
	private EditText et1;
	@Resource(R.id.et2)
	private EditText et2;
	
	@ContentView(R.layout.fragment3)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	    View view = ViewUtils.inject(this);
	    ViewGroup parent = (ViewGroup) view.getParent();  
		if (parent != null) {  
			parent.removeView(view);  
		} 
		return view;
		
	}
	@OnCompoundButtonCheckedChange(ids={R.id.checkbox1,R.id.checkbox2, R.id.checkbox3, R.id.toggleButton1})
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(buttonView == checkbox1){
			tv_value.setText("点击了选项1"+(isChecked?"开":"关"));
		} 
		else if(buttonView == checkbox2){
			tv_value.setText("点击了选项2"+(isChecked?"开":"关"));
		}
		else if(buttonView == checkbox3){
			tv_value.setText("点击了选项3"+(isChecked?"开":"关"));
		}
		else if(buttonView == toggleButton1){
			tv_value.setText("开关按钮"+(isChecked?"开":"关"));
		}
	}
	//自定义与系统监听方法名不一致的方法名
	public void startTrack(SeekBar seekBar) {
		Log.e("aiitec", "startTrack(onStartTrackingTouch):  "+seekBar.getProgress());
	}
	//这里不用写onSeekBarChange或者onStartTrackingTouch之类的注解，onSeekBarChange有三个但回调方法，只要在 onProgressChanged注解就行了
	public void onStopTrackingTouch(SeekBar seekBar) {
		Log.e("aiitec", "onStopTrackingTouch:  "+seekBar.getProgress());
	}
	//---------------------------------------------startTrack 的名字对应上面写的方法名，默认可以不写，与系统监听方法名一致
	@OnSeekBarChange(value=R.id.seekbar, startTrackingTouchMethodName="startTrack")
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		tv_value.setText("进度是："+progress+"");
	}
	@OnFocusChange(ids={R.id.et1, R.id.et2})
	public void onFocusChange(View v, boolean hasFocus) {
		if(v.getId() == R.id.et1){
			tv_value.setText("输入框一"+(hasFocus?"获取到焦点":"焦点丢失"));
		} else {
			tv_value.setText("输入框二"+(hasFocus?"获取到焦点":"焦点丢失"));
		}
	}
//	private void showToast(String text) {
//		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
//	}
	@OnTextChanged(R.id.et1)
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		Log.e("aiitec", "onTextChanged:"+s+ "start:"+start+"  before:"+before+"   count:"+count);
	}
	//一般我们实现监听会有三个回调方法，实际上我们只需要一个，那么，现在我们就只要上面一个方法就够了
//	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//		Log.e("aiitec", "beforeTextChanged:"+s+ "start:"+start+"   count:"+count+"  after:"+after);
//	}
//	public void afterTextChanged(Editable s) {
//		Log.e("aiitec", "afterTextChanged:"+s);
//	}
	
	
}
