package com.vgaw.logview.log;

import android.content.Context;
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
        setOnTouchListener(touchListener);

        dataList = new ArrayList<>();
        adapter = new EasyAdapter(getContext(), dataList) {
            @Override
            public EasyHolder getHolder(int type) {
                return new LogHolder();
            }
        };
        setAdapter(adapter);
    }

    private OnTouchListener touchListener = new OnTouchListener() {
        private float downX;
        private float downY;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    downX = event.getRawX();
                    downY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float dX = event.getRawX() - downX;
                    float dY = event.getRawY() - downY;
                    setX(getX() + dX);
                    setY(getY() + dY);
                    downX = event.getRawX();
                    downY = event.getRawY();
                    break;
            }
            return false;
        }
    };

}
