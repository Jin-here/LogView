package com.vgaw.logview.log;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.vgaw.logview.adapter.EasyAdapter;
import com.vgaw.logview.adapter.EasyHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caojin on 2016/10/7.
 */

public class LogView extends ListView {
    private List<String> dataList;
    private EasyAdapter adapter;

    public LogView(Context context){
        super(context);
        init();
    }

    public void addMsg(String msg){
        dataList.add(msg);
        adapter.notifyDataSetChanged();
        setSelection(getBottom());
    }

    private void init(){
        setBackgroundColor(Color.parseColor("#55555555"));

        dataList = new ArrayList<>();
        adapter = new EasyAdapter(getContext(), dataList) {
            @Override
            public EasyHolder getHolder(int type) {
                return new LogHolder();
            }
        };
        setAdapter(adapter);
    }
}
