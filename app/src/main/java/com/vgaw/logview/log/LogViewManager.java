package com.vgaw.logview.log;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by caojin on 2016/10/7.
 */

/**
 * there are only two method that you need to know:
 * {@link #init(Context)} which will show the suspended window when be called.
 * {@link #feedYou(String)} once you want to log something, just call it.
 */
public class LogViewManager {
    private static LogViewManager instance = new LogViewManager();

    private Context context;
    private Handler handler;
    private LogView logView;
    private WindowManager.LayoutParams params;

    private LogViewManager(){}

    public static LogViewManager getInstance() {
        return instance;
    }

    public void init(Context context){
        this.context = context;
        handler = new Handler(context.getMainLooper());
        openSuspendedWindow();
    }

    public void feedYou(final String msg){
        handler.post(new Runnable() {
            @Override
            public void run() {
                logView.addMsg(msg);
            }
        });
    }

    /**
     * 关闭悬浮窗
     * close the suspended window
     */
    private void removeSuspendedWindow(){
        if (logView != null){
            WindowManager manager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
            manager.removeViewImmediate(logView);
        }
    }

    /**
     * 打开悬浮窗
     * open the suspended window
     */
    private void openSuspendedWindow(){
        logView = new LogView(context);
        logView.setOnTouchListener(touchListener);

        WindowManager manager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        //params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;
        params.width = manager.getDefaultDisplay().getWidth() / 3;
        params.height = manager.getDefaultDisplay().getHeight() / 2;

        manager.addView(logView, params);
    }

    /**
     * 移动悬浮窗
     */
    private void moveSuspendedWindow(int x, int y){
        WindowManager manager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        params.x = x;
        params.y = y;
        manager.updateViewLayout(logView, params);
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        private float downX;
        private float downY;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    downX = event.getRawX();
                    downY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float dX = event.getRawX() - downX;
                    float dY = event.getRawY() - downY;
                    moveSuspendedWindow((int)(params.x + dX), (int)(params.y + dY));
                    downX = event.getRawX();
                    downY = event.getRawY();
                    break;
            }
            return true;
        }
    };
}
