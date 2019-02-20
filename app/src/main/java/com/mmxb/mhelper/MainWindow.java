package com.mmxb.mhelper;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.mmxb.mhelper.floatwindow.FloatWindowService;

/**
 * Created by mmxb on 2019/2/20
 */
public class MainWindow extends LinearLayout {

    private WindowManager manager;
    private WindowManager.LayoutParams params;

    public MainWindow(Context context) {
        this(context, null);
    }

    public MainWindow(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainWindow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initManager();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.float_window_layout, this);
    }

    private void initManager() {
        manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.gravity = Gravity.TOP;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        // 设置Window flag
        params.flags = WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD   //可使用FLAG_DISMISS_KEYGUARD选项直接解除非加锁的锁屏状态。此选项只用于最顶层的全屏幕窗口。
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL   //必须  设置窗口不拦截窗口范围之外事件
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH  // 必须  设置在有FLAG_NOT_TOUCH_MODAL属性时，窗口之外事件发生时自己也获取事件
                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
    }

    public void show() {
        manager.addView(this, params);
    }

    public void remove() {
        manager.removeView(this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                || event.getKeyCode() == KeyEvent.KEYCODE_SETTINGS) {
            if (manager != null) {
                remove();
                FloatWindowService.showFloatWindow();
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public void setWindowManager(WindowManager manager) {
        this.manager = manager;
    }
}






















