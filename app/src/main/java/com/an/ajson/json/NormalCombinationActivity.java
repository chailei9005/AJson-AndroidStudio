package com.an.ajson.json;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.aiitec.openapi.json.JSON;
import com.aiitec.openapi.utils.LogUtil;
import com.aiitec.openapi.utils.PacketUtil;
import com.aiitec.openapi.utils.ToastUtil;
import com.an.ajson.BaseActivity;
import com.an.ajson.R;
import com.an.ajson.business.model.Address;
import com.an.ajson.business.model.Card;
import com.an.ajson.business.model.Region;
import com.an.ajson.business.model.User;

/**
 * JSON解析类
 * 组包（组成json字符串）统一使用 JSON.toJsonString(T t)
 * 
 * 解包 （json字符串解成javaBean对象）
 * 
 * List<T> parseArray(String json, Class<T> entityClazz)
 * 
 * T parseObject(JSONObject json, Class<T> clazz)
 * 
 * T parseObject(String json, Class<T> clazz)
 * 
 * T parseObject(JSONObject json, Class<T> clazz, CombinationType combinationType)
 * 
 * T parseObject(String json, Class<T> clazz, CombinationType combinationType)
 * 
 * combinationType 是为我们公司的需求而产生的，正常json可以不使用。
 * 
 * @author Anthony
 * @version 1.0
 * @createTime 2016-8-11
 */
public class NormalCombinationActivity extends BaseActivity {

	private EditText et_nickname, et_salary, et_height, et_sex;		
	private EditText et_addressId, et_regionId, et_provinceId, et_cityId, et_districtId;		
	private EditText et_provinceName, et_cityName, et_districtName;		
			
	private EditText et_value;
	private Button btn;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_normal_combination);
		LogUtil.showLog = true;	
		et_nickname = (EditText) findViewById(R.id.et_nickname);
		et_salary = (EditText) findViewById(R.id.et_salary);
		et_height = (EditText) findViewById(R.id.et_height);
		et_sex = (EditText) findViewById(R.id.et_sex);
		et_addressId = (EditText) findViewById(R.id.et_addressId);
		et_regionId = (EditText) findViewById(R.id.et_regionId);
		et_provinceId = (EditText) findViewById(R.id.et_provinceRegionId);
		et_cityId = (EditText) findViewById(R.id.et_cityRegionId);
		et_districtId = (EditText) findViewById(R.id.et_districtRegionId);
		et_provinceName = (EditText) findViewById(R.id.et_provinceRegionName);
		et_cityName = (EditText) findViewById(R.id.et_cityRegionName);
		et_districtName = (EditText) findViewById(R.id.et_districtRegionName);
		
		et_value = (EditText) findViewById(R.id.et_value);
		btn = (Button) findViewById(R.id.btn);
		
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				combination();
			}
		});
		findViewById(R.id.btn_2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				decomposition();
			}
		});
		
	}
	
	/**
	 * 组包
	 */
	private void combination() {

		Card card = new Card();
		
		Address address = new Address();


		
		try {
			long addressId = Long.parseLong(et_addressId.getText().toString());
			address.setId(addressId);
		} catch (Exception e) {
		}
		try {
			int regionId = Integer.parseInt(et_regionId.getText().toString());
			address.setRegionId(regionId);
		} catch (Exception e) {
		}
		ArrayList<Region> regionInfo = new ArrayList<Region>();
		if(!TextUtils.isEmpty(et_provinceId.getText().toString()) ||
				!TextUtils.isEmpty(et_provinceName.getText().toString())){
			
			Region region1 = new Region();
			try {
				long provinceId = Long.parseLong(et_provinceId.getText().toString());
				region1.setId(provinceId);
			} catch (Exception e) {
			}
			region1.setName(et_provinceName.getText().toString());
			regionInfo.add(region1);
		}
		
		if(!TextUtils.isEmpty(et_cityId.getText().toString()) ||
				!TextUtils.isEmpty(et_cityName.getText().toString())){
			
			Region region2 = new Region();
			try {
				long cityId = Long.parseLong(et_cityId.getText().toString());
				region2.setId(cityId);
			} catch (Exception e) {
			}
			region2.setName(et_cityName.getText().toString());
			regionInfo.add(region2);
		}
		if(!TextUtils.isEmpty(et_districtId.getText().toString()) ||
				!TextUtils.isEmpty(et_districtName.getText().toString())){
			Region region3 = new Region();
			try {
				long districtId = Long.parseLong(et_districtId.getText().toString());
				region3.setId(districtId);
			} catch (Exception e) {
			}
			region3.setName(et_districtName.getText().toString());
			regionInfo.add(region3);
			
		}
		
		address.setRegionInfo(regionInfo);
		card.setAddress(address);
		card.setCardName(et_nickname.getText().toString());
		
		User user = new User();
		user.setName("张三");
		try {
			int sex = Integer.parseInt(et_sex.getText().toString());
			user.setSex(sex);
		} catch (Exception e) {
		}
		try {
		    user.setMoney(Double.parseDouble(et_salary.getText().toString()));
        } catch (Exception e) {
        }
		try {
		    user.setAttention(Integer.parseInt(et_height.getText().toString()));            
        } catch (Exception e) {
        }
		try {
			float height = Float.parseFloat(et_height.getText().toString());
			user.setHeight(height);
		} catch (Exception e) {
		}
		card.setUser(user);
		String json = "";
		try {
			json = JSON.toJsonString(card);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Log.e("aiitec", json);
		et_value.setText(PacketUtil.formatJson(json));
	}
	
	/**
	 * 解包
	 */
	private void decomposition() {
		try {
			String content = PacketUtil.compactJson(et_value.getText().toString());
			System.out.println(content);
			Card card = JSON.parseObject(content, Card.class);
			et_addressId.setText(card.getAddress().getId()+"");
			et_regionId.setText(card.getAddress().getRegionId()+"");
			List<Region> regionInfo = card.getAddress().getRegionInfo();
			if(regionInfo != null && regionInfo.size() > 0){
				et_provinceId.setText(regionInfo.get(0).getId()+"");
				et_provinceName.setText(regionInfo.get(0).getName()+"");
				if(regionInfo.size() > 1){
					et_cityId.setText(regionInfo.get(1).getId()+"");
					et_cityName.setText(regionInfo.get(1).getName()+"");
				} else {
					et_cityName.setText("");
					et_cityId.setText("");
				}
				if(regionInfo.size() > 2){
					et_districtId.setText(regionInfo.get(2).getId()+"");
					et_districtName.setText(regionInfo.get(2).getName()+"");
				} else {
					et_districtId.setText("");
					et_districtName.setText("");
				}
			} else {
				et_provinceId.setText("");
				et_provinceName.setText("");
				et_cityName.setText("");
				et_cityId.setText("");
				et_districtId.setText("");
				et_districtName.setText("");
			}
			User user = card.getUser();
			et_height.setText(user.getHeight()+"");
			et_nickname.setText(user.getName());
			et_salary.setText(user.getMoney()+"");
			et_sex.setText(user.getSex()+"");
			
		} catch (Exception e) {
			e.printStackTrace();
			ToastUtil.show(getApplicationContext(), "json数据格式不正确"+e.getMessage());
		}
		
	}

	

}
