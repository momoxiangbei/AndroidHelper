package com.mmxb.mhelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mmxb.mhelper.floatwindow.FloatWindow;
import com.mmxb.mhelper.floatwindow.FloatWindowService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                startFloatWindowService();
            } else {
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
            }
        } else {
            startFloatWindowService();
        }

    }

    private void startFloatWindowService() {
        startService(new Intent(MainActivity.this, FloatWindowService.class));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (Settings.canDrawOverlays(this)) {
                startFloatWindowService();
            } else {
                Toast.makeText(this, "请打开悬浮窗权限", Toast.LENGTH_LONG).show();
            }
        }
    }


}

