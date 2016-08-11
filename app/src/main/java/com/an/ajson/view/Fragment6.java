package com.an.ajson.view;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class Fragment6 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = new View(getActivity());
		ViewGroup parent = (ViewGroup) view.getParent();  
		if (parent != null) {  
			parent.removeView(view);  
		} 
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        int position = getArguments().getInt("position");
        int color = 0;
        switch(position){
        case 0:
            color = Color.RED;
            break;
        case 1:
            color = Color.GREEN;            
            break;
        case 2:
            color = Color.BLUE;
            break;
        }
        view.setBackgroundColor(color);
        return view;
    }
}
