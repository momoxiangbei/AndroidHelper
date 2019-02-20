package com.mmxb.mhelper.floatwindow;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.WindowManager;

/**
 * Created by mmxb on 2019/2/20
 */
public class FloatWindowService extends Service {

    // todo gc问题
    private Context context;
    public static FloatWindow floatWindow;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        // todo service？ lifecycle
        floatWindow = new FloatWindow(this);
        floatWindow.show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
        // todo
    }


    public static void showFloatWindow() {
        floatWindow.show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

//    private void initFloatWindow() {
//        floatParams.gravity = Gravity.END;
//        floatParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        floatParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        //  floatParams.flags 这两个属性很重要，避免了悬浮球全屏显示
//        floatParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            floatParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//        } else {
//            floatParams.type = WindowManager.LayoutParams.TYPE_PHONE;
//        }
//        floatWindow = new FloatWindow(context);
//        floatWindow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                manager.removeView(floatWindow);
//                showMessageWindow();
//            }
//        });
//        floatWindow.setOnTouchListener(new FloatWindowTouchListener());
//    }
//
//    public void showFloatWindow() {
//        manager.addView(floatButton, floatParams);
//    }
//
//    public void showMessageWindow() {
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//        params.gravity = Gravity.TOP;
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//        } else {
//            params.type = WindowManager.LayoutParams.TYPE_PHONE;
//        }
//        MessageWindow messageWindow = new MessageWindow(context);
//        messageWindow.setWindowManager(manager);
//        manager.addView(messageWindow, params);
//    }
//
//    // todo 弹性动画
//    private class FloatWindowTouchListener implements View.OnTouchListener {
//        private int x;
//        private int y;
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    x = (int) event.getRawX();
//                    y = (int) event.getRawY();
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    floatParams.x = floatParams.x + (int) (event.getRawX() - x);
//                    floatParams.y = floatParams.y + (int) (event.getRawY() - y);
//                    x = (int) event.getRawX();
//                    y = (int) event.getRawY();
//                    manager.updateViewLayout(v, floatParams);
//                    break;
//            }
//            return false;
//        }
//    }
}
