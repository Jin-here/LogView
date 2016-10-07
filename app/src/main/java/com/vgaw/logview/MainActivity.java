package com.vgaw.logview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vgaw.logview.log.LogViewManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * All you need to know is the {@link LogViewManager} class
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogViewManager.getInstance().init(this);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                LogViewManager.getInstance().feedYou(String.valueOf(System.currentTimeMillis()));
            }
        };
        timer.schedule(task, 1000, 1000);
    }
}
