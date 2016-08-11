package com.an.ajson;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.aiitec.openapi.view.ViewUtils;
import com.aiitec.openapi.view.annatation.Resource;
import com.aiitec.openapi.view.annatation.event.OnItemClick;
import com.an.ajson.json.NormalCombinationActivity;
import com.an.ajson.sqlite.SqliteActivity;
import com.an.ajson.view.ViewActivity;

public class MainActivity extends BaseActivity{

    @Resource(R.id.lv)
	private ListView lv;
	private ArrayAdapter<String> adapter;
	@Resource(R.array.mainlistdata)
	private String[] data ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_packet);
		ViewUtils.inject(this);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
		
		lv.setAdapter(adapter);
		
	}

	@OnItemClick(R.id.lv)
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	    Log.e("aiitec", "arg2:"+arg2);
		switch (arg2) {
		case 0:
			startActivity(new Intent(this, NormalCombinationActivity.class));
			break;
		case 1:
		    startActivity(new Intent(this, SqliteActivity.class));
		    break;
		case 2:
		    startActivity(new Intent(this, ViewActivity.class));
		    break;

		default:
			break;
		}
	}

}
