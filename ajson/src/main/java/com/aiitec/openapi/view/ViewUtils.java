package com.aiitec.openapi.view;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiitec.openapi.view.annatation.ContentView;
import com.aiitec.openapi.view.annatation.InflaterLayout;
import com.aiitec.openapi.view.annatation.Resource;
import com.aiitec.openapi.view.listener.Listener;
/**
 * View 初始化工具入口类
 * @author Anthony
 * <br>createTime 2015-12-31
 * <br>在Activity 的onCreate方法 加注解
 * <br>      α@ContentView(R.layout.activity_main),
 * <br>     并在onCreate方法内调用 ViewUtils.inject(this)
 * <br>Fragment 的onCreateView方法上加注解
 * <br>      α@ContentView(R.layout.fragment_main),
 * <br>     并在onCreateView内调用ViewUtils.inject(this)
 * <br>或者不加注解时，在onCreateView方法内调用 
 * <br>     View view = inflater.inflater(R.layout.fragment_main, null);
 * <br>     ViewUtils.inject(this, view)
 * <br> 如果不写@ContentView(R.layout.activity_main)， 而是setContentView(R.layout.activity_main)的话，
 * <br> 那么ViewUtils.inject(this)必须在setContentView之后才调用。
 */
public class ViewUtils {

	
    public static void inject(Activity activity) {
    	injectObject(activity, null);
    }
    public static View inject(Fragment fragment) {
    	return injectObject(fragment, null);
    }
    public static View inject(android.app.Fragment fragment) {
    	return injectObject(fragment, null);
    }
    public static View inject(Fragment fragment, View view) {
    	return injectObject(fragment, view);
    }
    public static View inject(android.app.Fragment fragment, View view) {
        return injectObject(fragment, view);
    }
    
    @SuppressLint("NewApi")
	private static View injectObject(Object obj, View view) {
    	Context context = null;
    	if(view != null){
    		context = view.getContext();
    	} 
    	else if(Activity.class.isAssignableFrom(obj.getClass())){
    		context = (Activity) obj;
    		try {
    		    Method onCreateMethod = obj.getClass().getDeclaredMethod("onCreate", Bundle.class);
    		    ContentView contentView = onCreateMethod.getAnnotation(ContentView.class);
    		    if(contentView != null && contentView.value() > 0){   
    		        Method method = obj.getClass().getMethod("setContentView", int.class);
    		        if(method != null){
    		            method.invoke(obj, contentView.value());
    		        }
    		    }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
    	}
    	else if(Fragment.class.isAssignableFrom(obj.getClass())){
    		context = ((Fragment) obj).getActivity();
    		
    		try {
//    		     ContentView contentView = obj.getClass().getAnnotation(ContentView.class);
    			 Method onCreateMethod = obj.getClass().getMethod("onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class);
     		     ContentView contentView = onCreateMethod.getAnnotation(ContentView.class);
                 if(contentView != null && contentView.value() > 0){
                     view = LayoutInflater.from(context).inflate(contentView.value(), null);
                 }
    		} catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
    	}
    	else if(android.app.Fragment.class.isAssignableFrom(obj.getClass())){
    	    context = ((android.app.Fragment) obj).getActivity();
    	    try {
//    	        ContentView contentView = obj.getClass().getAnnotation(ContentView.class);
    	    	Method onCreateMethod = obj.getClass().getMethod("onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class);
    	    	ContentView contentView = onCreateMethod.getAnnotation(ContentView.class);
                if(contentView != null && contentView.value() > 0){
    	            view = LayoutInflater.from(context).inflate(contentView.value(), null);
    	        }
    	    } catch (IllegalArgumentException e) {
    	        e.printStackTrace();
    	    } catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
    		
    	}
    	
        for (Field field: obj.getClass().getDeclaredFields()) {
        	try {
        		setValue(context, obj, field, view);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
        }
        for (Method method: obj.getClass().getMethods()) {
            Listener listener = new Listener(obj, method, view);
            listener.setListener();
        }
        
        return view;
    }
    
    /**
     * 给实例化出来的 字段内容赋值   目前仅兼容 stringId、 drawableId、  intArrayId、stringArrayId 支持数组[] 和集合List
     * @param context
     * @param obj
     * @param field
     * @param view
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private static void setValue(Context context, final Object obj, Field field, View view) throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
    	field.setAccessible(true);
    	Resource resource = field.getAnnotation(Resource.class);
    	InflaterLayout inflaterLayout = field.getAnnotation(InflaterLayout.class);
    	
    	View view1 = null;
    	Class<?> clazz = field.getType();
    	if(inflaterLayout != null && inflaterLayout.value() > 0){
    	    view1 = LayoutInflater.from(context).inflate(inflaterLayout.value(), null);
    	    field.set(obj, view1);
    	}
    	else if(resource != null ){
            
        	if(View.class.isAssignableFrom(clazz) && resource.value() > 0){
        		int stringId = resource.stringId();
        		int drawableId = resource.drawableId();
        		if(view != null){
        			view1 = view.findViewById(resource.value());
        		} else if(Activity.class.isAssignableFrom(obj.getClass())) {
        			view1 = ((Activity) obj).findViewById(resource.value());
        		}
        		if(stringId > 0 && view1 != null){
        			CharSequence stringValue = context.getResources().getString(stringId);
        			if(!TextUtils.isEmpty(stringValue)){
        				Method method = view1.getClass().getMethod("setText", CharSequence.class); 
        				if(method!= null){
        					method.invoke(view1, stringValue);
        				}
        			}
        		} 
        		
        		
        		if(drawableId > 0 && view1 != null)
        		{
        			Drawable value =context.getResources().getDrawable(drawableId);
        			Method method = view1.getClass().getMethod("setImageDrawable", Drawable.class); 
    				if(method!= null){
    					method.invoke(view1, value);
    				}
        		}
        		field.set(obj, view1);
        	}
        	else if(String[].class.isAssignableFrom(clazz) ){
        	    int id = -1;
        	  //@Resource(R.array.names)这样比较直观
        	  //@Resource(stringArrayId=R.array.names)这样比较不直观，但是符合设计，那么就两者兼容把
        	    if(resource.value() > 0){
        	        id = resource.value();
        	    } else if(resource.stringArrayId() > 0){
        	        id = resource.stringArrayId();
        	    }
        	    if(id > 0){
        	        String[] strs = context.getResources().getStringArray(id);
        	        if(strs != null){
        	            field.set(obj, strs);        			
        	        }
        	    }
        	}
        	else if(List.class.isAssignableFrom(clazz) && resource.stringArrayId() > 0){
        	    int id = -1;
        	    if(resource.value() > 0){
                    id = resource.value();
                } else if(resource.stringArrayId() > 0){
                    id = resource.stringArrayId();
                }
        	    if(id > 0){
        	        String[] strs = context.getResources().getStringArray(id);
        	        if(strs != null){
        	            field.set(obj, Arrays.asList(strs));        			
        	        }
        	    }
        	}
        	else if(Integer[].class.isAssignableFrom(clazz) && resource.intArrayId() > 0){
        	    int id = -1;
                if(resource.value() > 0){
                    id = resource.value();
                } else if(resource.stringArrayId() > 0){
                    id = resource.stringArrayId();
                }
                if(id > 0){
                    int[] ints = context.getResources().getIntArray(id);
                    if(ints != null){
                        field.set(obj, ints);        			
                    }
                }
        	}
        	else if(List.class.isAssignableFrom(clazz) && resource.intArrayId() > 0){
        	    int id = -1;
                if(resource.value() > 0){
                    id = resource.value();
                } else if(resource.stringArrayId() > 0){
                    id = resource.stringArrayId();
                }
                if(id > 0){
                    int[] ints = context.getResources().getIntArray(id);
                    if(ints != null){
                        field.set(obj, Arrays.asList(ints));        			
                    }                    
                }
        	}
        	else if(String.class.isAssignableFrom(clazz) && resource.stringId() > 0){
        		String str = context.getResources().getString(resource.stringId());
        		if(!TextUtils.isEmpty(str)){
        			field.set(obj, str);        			
        		}
        	}
    	}
    	
    	
	}
    
   
}
