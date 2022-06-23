package com.sibaamap.notificationkotlin

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import com.sibaamap.notificationkotlin.Constants.CHANNEL_ID
import com.sibaamap.notificationkotlin.Constants.MUSIC_NOTIFICATION_ID

class MyService: Service() {

    private lateinit var musicPlayer:MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        initMusic()
        createNotificationChanel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        showNotification()
        return START_STICKY
    }

    private fun showNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, 0
        )

        val notification =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification
                    .Builder(this, CHANNEL_ID)
                    .setContentText("Music player")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .build()
            } else {
                TODO("VERSION.SDK_INT < O")
            }

        startForeground(MUSIC_NOTIFICATION_ID,notification)
    }

    private fun createNotificationChanel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val serviceChanel = NotificationChannel(
                CHANNEL_ID,"My Service channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(serviceChanel)
        }
    }

    private fun initMusic(){
        musicPlayer = MediaPlayer.create(this,R.raw.tuanhung)
        musicPlayer.isLooping = true
        musicPlayer.setVolume(100F,100F)

    }
}