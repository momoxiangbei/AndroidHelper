package com.mmxb.mhelper.floatwindow;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mmxb.mhelper.MainWindow;
import com.mmxb.mhelper.R;

/**
 * Created by mmxb on 2019/2/20
 */
public class FloatWindow extends LinearLayout {

    // todo 弹性动画

    private WindowManager manager;
    private WindowManager.LayoutParams params;
    private int rawX;
    private int rawY;

    public FloatWindow(Context context) {
        this(context, null);
    }

    public FloatWindow(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatWindow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initManager();
    }


    private void initView() {
        TextView textView = new TextView(getContext());
        textView.setText(R.string.helper);
        textView.setBackgroundColor(Color.GREEN);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
                new MainWindow(getContext()).show();
            }
        });
        addView(textView);
    }

    private void initManager() {
        manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.gravity = Gravity.END;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //  params.flags 这两个属性很重要，避免了悬浮球全屏显示
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
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
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                rawX = (int) event.getRawX();
                rawY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                params.x = params.x + (int) (event.getRawX() - rawX);
                params.y = params.y + (int) (event.getRawY() - rawY);
                rawX = (int) event.getRawX();
                rawY = (int) event.getRawY();
                manager.updateViewLayout(this, params);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
