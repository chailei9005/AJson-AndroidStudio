package com.aiitec.openapi.view.listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ToggleButton;

import com.aiitec.openapi.view.annatation.event.OnChildClick;
import com.aiitec.openapi.view.annatation.event.OnClick;
import com.aiitec.openapi.view.annatation.event.OnCompoundButtonCheckedChange;
import com.aiitec.openapi.view.annatation.event.OnEditorAction;
import com.aiitec.openapi.view.annatation.event.OnFocusChange;
import com.aiitec.openapi.view.annatation.event.OnGroupClick;
import com.aiitec.openapi.view.annatation.event.OnGroupCollapse;
import com.aiitec.openapi.view.annatation.event.OnGroupExpand;
import com.aiitec.openapi.view.annatation.event.OnItemClick;
import com.aiitec.openapi.view.annatation.event.OnItemLongClick;
import com.aiitec.openapi.view.annatation.event.OnItemSelected;
import com.aiitec.openapi.view.annatation.event.OnLongClick;
import com.aiitec.openapi.view.annatation.event.OnPageChange;
import com.aiitec.openapi.view.annatation.event.OnRadioGroupCheckedChange;
import com.aiitec.openapi.view.annatation.event.OnScroll;
import com.aiitec.openapi.view.annatation.event.OnSeekBarChange;
import com.aiitec.openapi.view.annatation.event.OnTabChange;
import com.aiitec.openapi.view.annatation.event.OnTextChanged;
import com.aiitec.openapi.view.annatation.event.OnTouch;
/**
 * 设置监听类
 * @author Anthony
 * createTime 2015-12-31
 * 
 */
public class Listener implements OnClickListener, OnLongClickListener, OnItemClickListener, 
OnItemLongClickListener, OnItemSelectedListener, OnTouchListener, OnScrollListener, OnChildClickListener,
OnGroupClickListener, OnGroupExpandListener, android.widget.CompoundButton.OnCheckedChangeListener,
OnCheckedChangeListener, OnFocusChangeListener, OnTabChangeListener, OnSeekBarChangeListener, TextWatcher, OnPageChangeListener, OnGroupCollapseListener, OnEditorActionListener 
{

    private Object obj;
    private Method method;
    private View view;
    public Listener(Object obj, Method method, View view) {
        this.obj = obj;
        this.method = method;
        this.view = view;
    }
    
    public void setListener() {
        try {
            setOnClickListener();
            setOnLongClickListener();
            setOnItemClickListener();
            setOnItemLongClickListener();
            setOnItemSelectedListener();
            setOnTouchListener();
            setOnScrollListener();
            setOnChildClickListener();
            setOnGroupClickListener();
            setOnGroupExpandListener();
            setOnCompoundButtonCheckedChangeListener();
            setOnRadioGroupCheckedChangeListener();
            setOnFocusChangeListener();
            setOnTabChangedListener();
            setOnSeekBarChangeListener();
            setOnTextChangedListener();
            setOnPageChangeListener();
            setOnGroupCollapseListener();
            setOnEditorActionListener();
            
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }
    private View getInnerView(int id) {
        View view1 = null;
        if(view != null){
            view1 = view.findViewById(id);
        } else {
            view1 = ((Activity) obj).findViewById(id);
        }
        return view1;
    }
    private void setOnClickListener() throws IllegalAccessException, IllegalArgumentException {
        OnClick onClick = method.getAnnotation(OnClick.class);
        if(onClick != null){
            if(onClick.value() > 0) {
                int id = onClick.value();
                View view1 = getInnerView(id);
                if(view1 != null)
                    view1.setOnClickListener(this);
            } else if(onClick.ids() != null && onClick.ids().length > 0){
                for(int id: onClick.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null)
                        view1.setOnClickListener(this);
                }
            }
        }
    }
   
    private void setOnLongClickListener() throws IllegalAccessException, IllegalArgumentException {
        OnLongClick onLongClick = method.getAnnotation(OnLongClick.class);
        if(onLongClick != null){
            if(onLongClick.value() > 0) {
                int id = onLongClick.value();
                View view1 = getInnerView(id);
                if(view1 != null)
                	view1.setOnLongClickListener(this);
            } else if(onLongClick.ids() != null && onLongClick.ids().length > 0){
                for(int id: onLongClick.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null)
                    	view1.setOnLongClickListener(this);
                }
            }
        }
    }
    
    private void setOnItemClickListener() throws IllegalAccessException, IllegalArgumentException {
        OnItemClick onItemClick = method.getAnnotation(OnItemClick.class);
        if(onItemClick != null){
            if(onItemClick.value() > 0  ) {
                int id = onItemClick.value();
                View view1 = getInnerView(id);
                if(view1 != null && ListView.class.isAssignableFrom(view1.getClass())){
                    ((ListView)view1).setOnItemClickListener(this);
                } 
                else if (view1 != null && GridView.class.isAssignableFrom(view1.getClass())){
                    ((GridView)view1).setOnItemClickListener(this);
                }
                else if (view1 != null && Spinner.class.isAssignableFrom(view1.getClass())){
                    ((Spinner)view1).setOnItemClickListener(this);
                }
            } else if(onItemClick.ids() != null && onItemClick.ids().length > 0){
                for(int id: onItemClick.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null && ListView.class.isAssignableFrom(view1.getClass())){
                        ((ListView)view1).setOnItemClickListener(this);
                    } else if (view1 != null && GridView.class.isAssignableFrom(view1.getClass())){
                        ((GridView)view1).setOnItemClickListener(this);
                    }
                    else if (view1 != null && Spinner.class.isAssignableFrom(view1.getClass())){
                        ((Spinner)view1).setOnItemClickListener(this);
                    }
                }
            }
        }
    }
    private void setOnItemLongClickListener() throws IllegalAccessException, IllegalArgumentException {
        OnItemLongClick onItemLongClick = method.getAnnotation(OnItemLongClick.class);
        if(onItemLongClick != null){
            if(onItemLongClick.value() > 0  ) {
                int id = onItemLongClick.value();
                View view1 = getInnerView(id);
                if(view1 != null && ListView.class.isAssignableFrom(view1.getClass())){
                    ((ListView)view1).setOnItemLongClickListener(this);
                } else if (view1 != null && GridView.class.isAssignableFrom(view1.getClass())){
                    ((GridView)view1).setOnItemLongClickListener(this);
                } else if (view1 != null && Spinner.class.isAssignableFrom(view1.getClass())){
                    ((Spinner)view1).setOnItemLongClickListener(this);
                }
            } else if(onItemLongClick.ids() != null && onItemLongClick.ids().length > 0){
                for(int id: onItemLongClick.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null && ListView.class.isAssignableFrom(view1.getClass())){
                        ((ListView)view1).setOnItemLongClickListener(this);
                    } else if (view1 != null && GridView.class.isAssignableFrom(view1.getClass())){
                        ((GridView)view1).setOnItemLongClickListener(this);
                    } else if (view1 != null && Spinner.class.isAssignableFrom(view1.getClass())){
                        ((Spinner)view1).setOnItemLongClickListener(this);
                    }
                }
            }
        }
    }
    private void setOnItemSelectedListener() throws IllegalAccessException, IllegalArgumentException {
        OnItemSelected onItemSelected = method.getAnnotation(OnItemSelected.class);
        if(onItemSelected != null){
            if(onItemSelected.value() > 0  ) {
                int id = onItemSelected.value();
                View view1 = getInnerView(id);
                if(view1 != null && ListView.class.isAssignableFrom(view1.getClass())){
                    ((ListView)view1).setOnItemSelectedListener(this);
                } else if (view1 != null && GridView.class.isAssignableFrom(view1.getClass())){
                    ((GridView)view1).setOnItemSelectedListener(this);
                } else if (view1 != null && Spinner.class.isAssignableFrom(view1.getClass())){
                    ((Spinner)view1).setOnItemSelectedListener(this);
                }
            } else if(onItemSelected.ids() != null && onItemSelected.ids().length > 0){
                for(int id: onItemSelected.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null && ListView.class.isAssignableFrom(view1.getClass())){
                        ((ListView)view1).setOnItemSelectedListener(this);
                    } else if (view1 != null && GridView.class.isAssignableFrom(view1.getClass())){
                        ((GridView)view1).setOnItemSelectedListener(this);
                    } else if (view1 != null && Spinner.class.isAssignableFrom(view1.getClass())){
                        ((Spinner)view1).setOnItemSelectedListener(this);
                    }
                }
            }
        }
    }
    private void setOnTouchListener() throws IllegalAccessException, IllegalArgumentException {
        OnTouch onTouch = method.getAnnotation(OnTouch.class);
        if(onTouch != null){
            if(onTouch.value() > 0  ) {
                int id = onTouch.value();
                View view1 = getInnerView(id);
                if(view1 != null)
                	view1.setOnTouchListener(this);
            } else if(onTouch.ids() != null && onTouch.ids().length > 0){
                for(int id: onTouch.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null)
                    	view1.setOnTouchListener(this);
                }
            }
        }
    }
    private void setOnScrollListener() throws IllegalAccessException, IllegalArgumentException {
        OnScroll onScroll = method.getAnnotation(OnScroll.class);
        if(onScroll != null){
            if(onScroll.value() > 0  ) {
                int id = onScroll.value();
                View view1 = getInnerView(id);
                if(view1 != null && ListView.class.isAssignableFrom(view1.getClass())){
                    ((ListView)view1).setOnScrollListener(this);
                } else if (view1 != null && GridView.class.isAssignableFrom(view1.getClass())){
                    ((GridView)view1).setOnScrollListener(this);
                }
            } else if(onScroll.ids() != null && onScroll.ids().length > 0){
                for(int id: onScroll.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null && ListView.class.isAssignableFrom(view1.getClass())){
                        ((ListView)view1).setOnScrollListener(this);
                    } else if (view1 != null && GridView.class.isAssignableFrom(view1.getClass())){
                        ((GridView)view1).setOnScrollListener(this);
                    }
                }
            }
        }
    }
    private void setOnChildClickListener() throws IllegalAccessException, IllegalArgumentException {
        OnChildClick onChildClick = method.getAnnotation(OnChildClick.class);
        if(onChildClick != null){
            if(onChildClick.value() > 0  ) {
                int id = onChildClick.value();
                ExpandableListView view1 = (ExpandableListView) getInnerView(id);
                if(view1 != null ){
                    view1.setOnChildClickListener(this);
                }
            } else if(onChildClick.ids() != null && onChildClick.ids().length > 0){
                for(int id: onChildClick.ids()){
                	ExpandableListView view1 = (ExpandableListView) getInnerView(id);
                    if(view1 != null && ExpandableListView.class.isAssignableFrom(view1.getClass())){
                        view1.setOnChildClickListener(this);
                    } 
                }
            }
        }
    }
    private void setOnGroupClickListener() throws IllegalAccessException, IllegalArgumentException {
        OnGroupClick onGroupClick = method.getAnnotation(OnGroupClick.class);
        if(onGroupClick != null){
            if(onGroupClick.value() > 0  ) {
                int id = onGroupClick.value();
                View view1 = getInnerView(id);
                if(view1 != null && ExpandableListView.class.isAssignableFrom(view1.getClass())){
                    ((ExpandableListView)view1).setOnGroupClickListener(this);
                }
            } else if(onGroupClick.ids() != null && onGroupClick.ids().length > 0){
                for(int id: onGroupClick.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null && ExpandableListView.class.isAssignableFrom(view1.getClass())){
                        ((ExpandableListView)view1).setOnGroupClickListener(this);
                    } 
                }
            }
        }
    }
    private void setOnGroupExpandListener() throws IllegalAccessException, IllegalArgumentException {
        OnGroupExpand onGroupExpand = method.getAnnotation(OnGroupExpand.class);
        if(onGroupExpand != null){
            if(onGroupExpand.value() > 0  ) {
                int id = onGroupExpand.value();
                View view1 = getInnerView(id);
                if(view1 != null && ExpandableListView.class.isAssignableFrom(view1.getClass())){
                    ((ExpandableListView)view1).setOnGroupExpandListener(this);
                }
            } else if(onGroupExpand.ids() != null && onGroupExpand.ids().length > 0){
                for(int id: onGroupExpand.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null && ExpandableListView.class.isAssignableFrom(view1.getClass())){
                        ((ExpandableListView)view1).setOnGroupExpandListener(this);
                    } 
                }
            }
        }
    }
    private void setOnGroupCollapseListener() throws IllegalAccessException, IllegalArgumentException {
        OnGroupCollapse onGroupCollapse = method.getAnnotation(OnGroupCollapse.class);
        if(onGroupCollapse != null){
            if(onGroupCollapse.value() > 0  ) {
                int id = onGroupCollapse.value();
                View view1 = getInnerView(id);
                if(view1 != null && ExpandableListView.class.isAssignableFrom(view1.getClass())){
                    ((ExpandableListView)view1).setOnGroupCollapseListener(this);
                }
            } else if(onGroupCollapse.ids() != null && onGroupCollapse.ids().length > 0){
                for(int id: onGroupCollapse.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null && ExpandableListView.class.isAssignableFrom(view1.getClass())){
                        ((ExpandableListView)view1).setOnGroupCollapseListener(this);
                    } 
                }
            }
        }
    }
    private void setOnCompoundButtonCheckedChangeListener() throws IllegalAccessException, IllegalArgumentException {
        OnCompoundButtonCheckedChange onCompoundButtonCheckedChange = method.getAnnotation(OnCompoundButtonCheckedChange.class);
        if(onCompoundButtonCheckedChange != null){
            if(onCompoundButtonCheckedChange.value() > 0  ) {
                int id = onCompoundButtonCheckedChange.value();
                View view1 = getInnerView(id);
                if(view1 != null && RadioButton.class.isAssignableFrom(view1.getClass())){
                    ((RadioButton)view1).setOnCheckedChangeListener(this);
                } else if (view1 != null && CheckBox.class.isAssignableFrom(view1.getClass())){
                    ((CheckBox)view1).setOnCheckedChangeListener(this);
                } else if (view1 != null && ToggleButton.class.isAssignableFrom(view1.getClass())){
                    ((ToggleButton)view1).setOnCheckedChangeListener(this);
                }
            } else if(onCompoundButtonCheckedChange.ids() != null && onCompoundButtonCheckedChange.ids().length > 0){
                for(int id: onCompoundButtonCheckedChange.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null && RadioButton.class.isAssignableFrom(view1.getClass())){
                        ((RadioButton)view1).setOnCheckedChangeListener(this);
                    } else if (view1 != null && CheckBox.class.isAssignableFrom(view1.getClass())){
                        ((CheckBox)view1).setOnCheckedChangeListener(this);
                    } else if (view1 != null && ToggleButton.class.isAssignableFrom(view1.getClass())){
                        ((ToggleButton)view1).setOnCheckedChangeListener(this);
                    }
                }
            }
        }
    }
    
    private void setOnRadioGroupCheckedChangeListener() throws IllegalAccessException, IllegalArgumentException {
        OnRadioGroupCheckedChange onRadioGroupCheckedChange = method.getAnnotation(OnRadioGroupCheckedChange.class);
        if(onRadioGroupCheckedChange != null){
            if(onRadioGroupCheckedChange.value() > 0  ) {
                int id = onRadioGroupCheckedChange.value();
                View view1 = getInnerView(id);
                if(view1 != null && RadioGroup.class.isAssignableFrom(view1.getClass())){
                    ((RadioGroup)view1).setOnCheckedChangeListener(this);
                }
            } else if(onRadioGroupCheckedChange.ids() != null && onRadioGroupCheckedChange.ids().length > 0){
                for(int id: onRadioGroupCheckedChange.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null && RadioButton.class.isAssignableFrom(view1.getClass())){
                        ((RadioButton)view1).setOnCheckedChangeListener(this);
                    } 
                }
            }
        }
    }
    
    private void setOnFocusChangeListener() throws IllegalAccessException, IllegalArgumentException {
        OnFocusChange onFocusChange = method.getAnnotation(OnFocusChange.class);
        if(onFocusChange != null){
            if(onFocusChange.value() > 0  ) {
                int id = onFocusChange.value();
                View view1 = getInnerView(id);
                if(view1 != null){
                	view1.setOnFocusChangeListener(this);
                }
            } else if(onFocusChange.ids() != null && onFocusChange.ids().length > 0){
                for(int id: onFocusChange.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null){
                    	view1.setOnFocusChangeListener(this);                    	
                    }
                }
            }
        }
    }
    
    private void setOnTabChangedListener() throws IllegalAccessException, IllegalArgumentException {
        OnTabChange onTabChange = method.getAnnotation(OnTabChange.class);
        if(onTabChange != null){
            if(onTabChange.value() > 0  ) {
                int id = onTabChange.value();
                View view1 = getInnerView(id);
                if(view1 != null && TabHost.class.isAssignableFrom(view1.getClass())){
                    ((TabHost)view1).setOnTabChangedListener(this);
                }
            } else if(onTabChange.ids() != null && onTabChange.ids().length > 0){
                for(int id: onTabChange.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null && TabHost.class.isAssignableFrom(view1.getClass())){
                        ((TabHost)view1).setOnTabChangedListener(this);
                    } 
                }
            }
        }
    }
    
    private void setOnSeekBarChangeListener() throws IllegalAccessException, IllegalArgumentException {
        OnSeekBarChange onSeekBarChange = method.getAnnotation(OnSeekBarChange.class);
        if(onSeekBarChange != null){
            if(onSeekBarChange.value() > 0  ) {
                int id = onSeekBarChange.value();
                View view1 = getInnerView(id);
                if(view1 != null && SeekBar.class.isAssignableFrom(view1.getClass())){
                    ((SeekBar)view1).setOnSeekBarChangeListener(this);
                }
            } else if(onSeekBarChange.ids() != null && onSeekBarChange.ids().length > 0){
                for(int id: onSeekBarChange.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null && SeekBar.class.isAssignableFrom(view1.getClass())){
                        ((SeekBar)view1).setOnSeekBarChangeListener(this);
                    } 
                }
            }
        }
    }
   
    private void setOnTextChangedListener() throws IllegalAccessException, IllegalArgumentException {
    	OnTextChanged onSeekBarChange = method.getAnnotation(OnTextChanged.class);
    	if(onSeekBarChange != null){
    		if(onSeekBarChange.value() > 0  ) {
    			int id = onSeekBarChange.value();
    			View view1 = getInnerView(id);
    			if(view1 != null && EditText.class.isAssignableFrom(view1.getClass())){
    				((EditText)view1).addTextChangedListener(this);
    			}
    		} else if(onSeekBarChange.ids() != null && onSeekBarChange.ids().length > 0){
    			for(int id: onSeekBarChange.ids()){
    				View view1 = getInnerView(id);
    				if(view1 != null && EditText.class.isAssignableFrom(view1.getClass())){
    					((EditText)view1).addTextChangedListener(this);
    				} 
    			}
    		}
    	}
    }
    private void setOnPageChangeListener() throws IllegalAccessException, IllegalArgumentException {
        OnPageChange onPageChange = method.getAnnotation(OnPageChange.class);
        if(onPageChange != null){
            if(onPageChange.value() > 0  ) {
                int id = onPageChange.value();
                View view1 = getInnerView(id);
                if(view1 != null && ViewPager.class.isAssignableFrom(view1.getClass())){
                    ((ViewPager)view1).addOnPageChangeListener(this);
                }
            } else if(onPageChange.ids() != null && onPageChange.ids().length > 0){
                for(int id: onPageChange.ids()){
                    View view1 = getInnerView(id);
                    if(view1 != null && ViewPager.class.isAssignableFrom(view1.getClass())){
                        ((ViewPager)view1).addOnPageChangeListener(this);
                    } 
                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        if(method != null){
            try {
                method.setAccessible(true);
                method.invoke(obj, v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean onLongClick(View v) {
        boolean result = false;
        if(method != null){
            try {
                method.setAccessible(true);
                Object objReturn = method.invoke(obj, v);
                Type returnType = method.getGenericReturnType();
                if(returnType.equals(boolean.class)){
                    result = (Boolean) objReturn;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
        Log.i("aiitec", "onItemClick");
        if(method != null){
            try {
                method.setAccessible(true);
                method.invoke(obj, adapter, v, position, id);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapter, View v, int position, long id) {
        boolean result = false;
        if(method != null){
            try {
                method.setAccessible(true);
                Object objReturn = method.invoke(obj, adapter, v, position, id);
                Type returnType = method.getGenericReturnType();
                if(returnType.equals(boolean.class)){
                    result = (Boolean) objReturn;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapter, View v, int position,
            long id) {
        if(method != null){
            try {
                method.setAccessible(true);
                method.invoke(obj, adapter, v, position, id);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapter) {
        OnItemSelected onItemSelected = method.getAnnotation(OnItemSelected.class);
        if(onItemSelected != null){
            Method method2;
            try {
                method2 = obj.getClass().getDeclaredMethod(onItemSelected.nothingSelectedMethodName(), AdapterView.class);
                if(method2 != null){
                    method2.setAccessible(true);
                    method2.invoke(obj, adapter);
                }
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean result = false;
        if(method != null){
            try {
                method.setAccessible(true);
                Object objReturn = method.invoke(obj, v, event);
                Type returnType = method.getGenericReturnType();
                if(returnType.equals(boolean.class)){
                    result = (Boolean) objReturn;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        if(method != null){
            try {
                method.setAccessible(true);
                method.invoke(obj, view, firstVisibleItem, visibleItemCount, totalItemCount);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        OnScroll onScroll = method.getAnnotation(OnScroll.class);
        if(onScroll != null){
            Method method2;
            try {
                method2 = obj.getClass().getDeclaredMethod(onScroll.scrollStateChangedMethodName(), AbsListView.class, int.class);
                if(method2 != null){
                    method2.setAccessible(true);
                    method2.invoke(obj, view, scrollState);
                }
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        if(method != null){
            try {
                method.setAccessible(true);
                method.invoke(obj, groupPosition);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v,
            int groupPosition, long id) {
        boolean result = false;
        if(method != null){
            try {
                method.setAccessible(true);
                Object objReturn = method.invoke(obj, parent, v, groupPosition, id);
                Type returnType = method.getGenericReturnType();
                if(returnType.equals(boolean.class)){
                    result = (Boolean) objReturn;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
            int groupPosition, int childPosition, long id) {
        boolean result = false;
        if(method != null){
            try {
                method.setAccessible(true);
                Object objReturn =  method.invoke(obj, parent, v, groupPosition, childPosition, id);
                Type returnType = method.getGenericReturnType();
                if(returnType.equals(boolean.class)){
                    result = (Boolean) objReturn;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(method != null){
            try {
                method.setAccessible(true);
                method.invoke(obj, buttonView, isChecked);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        if(method != null){
            try {
                method.setAccessible(true);
                method.invoke(obj, tabId);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(method != null){
            try {
                method.setAccessible(true);
                method.invoke(obj, v, hasFocus);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(method != null){
            try {
                method.setAccessible(true);
                method.invoke(obj, group, checkedId);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {
        if(method != null){
            try {
                method.setAccessible(true);
                method.invoke(obj, seekBar, progress, fromUser);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        OnSeekBarChange onSeekBarChange = method.getAnnotation(OnSeekBarChange.class);
        if(onSeekBarChange != null){
            Method method2;
            try {
                method2 = obj.getClass().getDeclaredMethod(onSeekBarChange.startTrackingTouchMethodName(), SeekBar.class);
                if(method2 != null){
                    method2.setAccessible(true);
                    method2.invoke(obj, seekBar);
                }
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        OnSeekBarChange onSeekBarChange = method.getAnnotation(OnSeekBarChange.class);
        if(onSeekBarChange != null){
            Method method2;
            try {
                method2 = obj.getClass().getDeclaredMethod(onSeekBarChange.stopTrackingTouchMethodName(), SeekBar.class);
                if(method2 != null){
                    method2.setAccessible(true);
                    method2.invoke(obj, seekBar);
                }
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    	 if(method != null){
             try {
                 method.setAccessible(true);
                 method.invoke(obj, s, start, before, count);
             } catch (IllegalAccessException e) {
                 e.printStackTrace();
             } catch (IllegalArgumentException e) {
                 e.printStackTrace();
             } catch (InvocationTargetException e) {
                 e.printStackTrace();
             }
         }
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,int after) {
		OnTextChanged onTextChanged = method.getAnnotation(OnTextChanged.class);
        if(onTextChanged != null){
            Method method2;
            try {
                method2 = obj.getClass().getDeclaredMethod(onTextChanged.beforeTextChangedMethodName(),CharSequence.class, int.class, int.class, int.class);
                if(method2 != null){
                    method2.setAccessible(true);
                    method2.invoke(obj, s, start, count, after);
                }
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		OnTextChanged onTextChanged = method.getAnnotation(OnTextChanged.class);
        if(onTextChanged != null){
            Method method2;
            try {
                method2 = obj.getClass().getDeclaredMethod(onTextChanged.afterTextChangedMethodName(), Editable.class);
                if(method2 != null){
                    method2.setAccessible(true);
                    method2.invoke(obj, s);
                }
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
	}

    @Override
    public void onPageScrollStateChanged(int arg0) {
        OnPageChange onPageChange = method.getAnnotation(OnPageChange.class);
        if(onPageChange != null){
            Method method2;
            try {
                method2 = obj.getClass().getDeclaredMethod(onPageChange.pageScrollStateChangedMethodName(), int.class);
                if(method2 != null){
                    method2.setAccessible(true);
                    method2.invoke(obj, arg0);
                }
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        OnPageChange onPageChange = method.getAnnotation(OnPageChange.class);
        if(onPageChange != null){
            Method method2;
            try {
                method2 = obj.getClass().getDeclaredMethod(onPageChange.pageScrolledMethodName(), int.class, float.class, int.class);
                if(method2 != null){
                    method2.setAccessible(true);
                    method2.invoke(obj, arg0, arg1, arg2);
                }
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPageSelected(int arg0) {
        if(method != null){
            try {
                method.setAccessible(true);
                method.invoke(obj, arg0);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        if(method != null){
            try {
                method.setAccessible(true);
                method.invoke(obj, groupPosition);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    private void setOnEditorActionListener() throws IllegalAccessException, IllegalArgumentException {
    	OnEditorAction onEditorAction = method.getAnnotation(OnEditorAction.class);
    	if(onEditorAction != null){
    		if(onEditorAction.value() > 0  ) {
    			int id = onEditorAction.value();
    			View view1 = getInnerView(id);
    			if(view1 != null && EditText.class.isAssignableFrom(view1.getClass())){
    				((EditText)view1).setOnEditorActionListener(this);
    			}
    		} else if(onEditorAction.ids() != null && onEditorAction.ids().length > 0){
    			for(int id: onEditorAction.ids()){
    				View view1 = getInnerView(id);
    				if(view1 != null && EditText.class.isAssignableFrom(view1.getClass())){
    					((EditText)view1).setOnEditorActionListener(this);
    				} 
    			}
    		}
    	}
    }
    @Override
	public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
		boolean result = false;
		if(method != null){
            try {
            	method.setAccessible(true);
            	Object objReturn = (Boolean) method.invoke(obj, arg0, arg1, arg2);
                Type returnType = method.getGenericReturnType();
                if(returnType.equals(boolean.class)){
                    result = (Boolean) objReturn;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
		return result;
	}
}
