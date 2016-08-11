package com.an.ajson.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.aiitec.openapi.view.ViewUtils;
import com.aiitec.openapi.view.annatation.ContentView;
import com.aiitec.openapi.view.annatation.Resource;
import com.an.ajson.BaseActivity;
import com.an.ajson.R;

/**
 * View 注入框架显示类
 * 要使用@ContentView(R.layout.fragment_tab)， 而不使用setContentView(R.layout.fragment_tab)
 * 具体使用看代码就行了， 我不太喜欢写文档
 * @author Anthony
 * @version 1.0
 * @createTime 2016-8-11
 */
public class ViewActivity extends BaseActivity {

    @Resource(android.R.id.tabhost)
    private FragmentTabHost mTabHost;

    private Class<?> mFragmentArray[] = { Fragment1.class, Fragment2.class,
    		Fragment3.class, Fragment4.class, Fragment5.class };

    @Resource(stringArrayId= R.array.tabs)
    private String mTextArray[] ;

    @ContentView(R.layout.fragment_tab)
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      ViewUtils.inject(this);
      initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        // 得到fragment的个数
        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
        }
    }
  
    /**
     *
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
      
    	TextView textView = (TextView) getLayoutInflater().inflate(R.layout.tab_item_view, null);
        textView.setText(mTextArray[index]);

        return textView;
    }


  
}
