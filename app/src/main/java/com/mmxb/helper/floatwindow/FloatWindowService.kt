package com.mmxb.helper.floatwindow

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import com.mmxb.helper.HelperApplication
import com.mmxb.helper.MainActivity
import com.mmxb.helper.R


/**
 * Created by mmxb on 2019/2/20
 */
class FloatWindowService : Service() {

    companion object {
        lateinit var floatWindow: FloatWindow
    }

    override fun onCreate() {
        super.onCreate()
        floatWindow = FloatWindow(this)
        floatWindow.show()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notification = buildNotification(HelperApplication.instance, "android开发助手守护进程", "点击打开android开发助手")
        startForeground(1, notification)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun buildNotification(context: Context, title: String, content: String): Notification? {
        // todo 静音 不显示弹窗 4.4.4系统不可被清除的适配
        val channelId = "1024"
        val channelName = "androidDeveloperHelper"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // android 8.0开始 构建notification需要指定channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            channel.enableLights(false)
            channel.setShowBadge(false)
            notificationManager.createNotificationChannel(channel)
        }
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(context, channelId)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                .setVibrate(null)
                .setSound(null)
                .build()
        notification.flags = Notification.FLAG_ONGOING_EVENT or Notification.FLAG_NO_CLEAR or Notification.FLAG_FOREGROUND_SERVICE
        return notification
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}
