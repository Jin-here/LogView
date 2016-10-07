package com.vgaw.logview.log;

import android.view.View;
import android.widget.TextView;

import com.vgaw.logview.adapter.EasyHolder;

/**
 * Created by caojin on 2016/10/7.
 */

/**
 * default implement, new functions later
 */
public class LogHolder extends EasyHolder {
    private TextView tv;

    @Override
    public int getLayout() {
        return android.R.layout.activity_list_item;
    }

    @Override
    public View createView() {
        tv = (TextView) view.findViewById(android.R.id.text1);
        return view;
    }

    @Override
    public void refreshView(Object item) {
        tv.setText(String.valueOf(item));
    }
}
