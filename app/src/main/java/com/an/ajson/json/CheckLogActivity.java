package com.an.ajson.json;

import java.io.IOException;

import com.aiitec.openapi.utils.LogRecordUtils;
import com.an.ajson.BaseActivity;
import com.an.ajson.R;

import android.os.Bundle;
import android.widget.TextView;

/**
 * 查看log日志类
 * @author Anthony
 * @version 1.0
 * @createTime 2016-8-11
 */
public class CheckLogActivity extends BaseActivity {

	private TextView tv_values;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_log);
		tv_values =  (TextView) findViewById(R.id.tv_value);
		
		try {
			String log = LogRecordUtils.readLog();
			tv_values.setText(log);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
