package com.an.ajson.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.aiitec.openapi.db.AIIDBManager;
import com.aiitec.openapi.json.JSON;
import com.aiitec.openapi.utils.LogUtil;
import com.an.ajson.BaseActivity;
import com.an.ajson.R;

/**
 * 数据库存储类
 * 
 * 按对象存储，会自动检查字段增加减少而改变数据库结构
 * 
 * @author Anthony
 * 
 */
public class SqliteActivity extends BaseActivity {

	private AIIDBManager manager;
	private ListView lv;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		setContentView(ll);
		LogUtil.showLog = true;
		manager = AIIDBManager.getInstance(this);
		// manager.save(datas);//插入List
		// manager.save(data);//插入对象
		// manager.deleteAll(User.class)//删除该表的所以数据
		// manager.delete(User.class, "id=?", new
		// String[]{"23"})//通过条件删除，与安卓默认的条件一样
		// manager.findAll(User.class);//查询所有User的数据
		// manager.findAll(User.class, "id<?", new String[]{"10"});
		// manager.findFirst(User.class);
		// manager.findFirst(User.class, "id>?", new String[]{"23"}, "desc");
		// manager.findObjectFromId(User.class, 32);
		lv = new ListView(this);
		Button btn = new Button(this);
		btn.setText("插入");
		Button btn2 = new Button(this);
		btn2.setText("查询");
		Button btn3 = new Button(this);
		btn3.setText("删除所有");
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lv.setLayoutParams(params2);
		ll.addView(btn);
		ll.addView(btn2);
		ll.addView(btn3);
		ll.addView(lv);

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				User user;
				try {
					user = JSON.parseObject(adapter.getItem(arg2), User.class);

					manager.delete(User.class, "id=?",
							new String[] { user.getId() + "" });

					doRead();// 刷新
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}
		});

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		lv.setAdapter(adapter);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

//				Person person = new Person();
//				person.setId(6);
//				person.setName("zhangsan");
//				person.setMoney(9445);
////				person.setEnglishName("sssss");
////				person.setYear(888);
//				manager.update(person);
				for(int i=0; i<30; i++){
					
					new InsertThread(i).start();
				}
			}
		});
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				doRead();
//				List<Person> users = null;
//				try {
//					users = manager.findAll(Person.class);
//				} catch (InstantiationException e) {
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				}
//				if(users != null){
//					
//					adapter.clear();
//					for (int i = 0; i < users.size(); i++) {
//						try {
//							adapter.add(JSON.toJsonString(users.get(i)));
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//					adapter.notifyDataSetChanged();
//				}
			}
		});
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			    new Thread(new Runnable() {
                    
                    @Override
                    public void run() {
                        
                        manager.deleteAll(User.class);
                        doRead();// 刷新
                    }
                }).start();

			
				

			}
		});
	}

	@SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what == 1){
				
				adapter.clear();
				// User user = manager.findObjectFromId(User.class, 32);
				// adapter.add(JSON.toJsonString(user));
				@SuppressWarnings("unchecked")
                List<User> users = (ArrayList<User>) msg.obj;
				if(users != null){  
					
					for (int i = 0; i < users.size(); i++) {
						try {
							adapter.add(i+"-----"+users.get(i).toString());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					adapter.notifyDataSetChanged();  
					
				}
			}
		};
	};
	public void doRead() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<User> users;
				try {
					users = manager.findAll(User.class);
					Message msg = new Message();
					msg.obj = users;
					msg.what = 1;
					handler.sendMessage(msg);
					LogUtil.e("aiitec", users.size()+"==============");  
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		

	}

	class InsertThread extends Thread {
		int index;

		public InsertThread(int index) {
			this.index = index;
		}

		@Override
		public void run() {
			super.run();

			doSave(index);
		}

	}

	public void doSave(int index) {

		List<User> users = new ArrayList<User>();
		int startValue = index * 100;
		int endValue = startValue + 100;
		for (int i = startValue; i < endValue; i++) {
			User user = new User();
			user.setJob("engineer");
			user.setTime("20151208");
			user.setSex("male");
			user.setId(i + 1);
			user.setEnglishName("englishNameffff" + i);
			user.setHeight(160 + i);
			user.setMoney(255.2 + i);
			user.setName("name" + i);
			user.setYear(18 + i);

			Person person = new Person();
			person.setEnglishName("personEnName" + i);
			person.setGoods(16.3f + i);
			person.setId(100 + i);
			person.setMoney(465.3 + i);
			person.setYear(30 + i);

			user.setPerson(person);

			List<String> mobiles = new ArrayList<String>();
			for (int j = 0; j < 3; j++) {
				mobiles.add("1350001000" + i);
			}
			user.setMobiles(mobiles);
			users.add(user);
			Log.w("aiitec", user.toString());
		}
		manager.save(users);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
