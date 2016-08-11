package com.an.ajson.view;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.aiitec.openapi.view.ViewUtils;
import com.aiitec.openapi.view.annatation.ContentView;
import com.aiitec.openapi.view.annatation.Resource;
import com.aiitec.openapi.view.annatation.event.OnPageChange;
import com.aiitec.openapi.view.annatation.event.OnRadioGroupCheckedChange;
import com.an.ajson.R;

public class Fragment5 extends Fragment {

	
	@Resource(R.id.viewpager)
	private ViewPager viewpager;
	
	@Resource(R.id.radioGroup5)
	private RadioGroup radioGroup;
	@Resource(R.id.radioButton5)
	private RadioButton radioButton1;
	@Resource(R.id.radioButton6)
	private RadioButton radioButton2;
	@Resource(R.id.radioButton7)
	private RadioButton radioButton3;
	private List<Fragment> fragments ;
	
	private ViewPagerAdapter adapter;
	
	@ContentView(R.layout.fragment5)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = ViewUtils.inject(this);
		ViewGroup parent = (ViewGroup) view.getParent();  
		if (parent != null) {  
			parent.removeView(view);  
		} 
		fragments = new ArrayList<Fragment>();
		for (int i = 0; i < 3; i++) {
		    Fragment6 fragment6 = new Fragment6();
		    Bundle bundle = new Bundle();
		    bundle.putInt("position", i);
		    fragment6.setArguments(bundle);
		    fragments.add(fragment6);
        }
		adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
		viewpager.setAdapter(adapter);
		
		return view;
		
	}
	@OnRadioGroupCheckedChange(R.id.radioGroup5)
	public void onCheckedChanged(RadioGroup group, int checkedId) {
	    switch(checkedId){
	    case R.id.radioButton5:
	        viewpager.setCurrentItem(0);
	        break;
	    case R.id.radioButton6:
	        viewpager.setCurrentItem(1);
	        break;
	    case R.id.radioButton7:
	        viewpager.setCurrentItem(2);
	        break;
	    }
	}
	@OnPageChange(R.id.viewpager)
	public void onPageSelected(int arg0) {
	    switch(arg0){
	    case 0:
	        radioGroup.check(R.id.radioButton5);
	        break;
	    case 1:
	        radioGroup.check(R.id.radioButton6);
	        break;
	    case 2:
	        radioGroup.check(R.id.radioButton7);
	        break;
	    }
	}
	
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	    Log.e("aiitec", "onPageScrolled:   arg0"+arg0+"   arg1:"+arg0+ "    arg2:"+arg2);
	}
	
	public void onPageScrollStateChanged(int arg0) {
	    Log.e("aiitec", "onPageScrolled:   arg0"+arg0);
	}
	
}
