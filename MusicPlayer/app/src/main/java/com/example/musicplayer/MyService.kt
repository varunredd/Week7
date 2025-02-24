package com.example.musicplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import android.util.Log

class MyService : Service() {

    private var player: MediaPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(player == null){
            player = MediaPlayer.create(this, R.raw.deadtome)
        }

        var function = intent?.getStringExtra("Function")
        Log.d("test",function+"")

        if(function.equals("Play")){
            player?.start()
            sendStatusBroadcast("Playing")
        }else if(function.equals("Pause")){
            if(player?.isPlaying == true){
                Log.d("test","hit")
                player?.pause()
                sendStatusBroadcast("Paused")
            }
        }else if(function.equals("Stop")){
            if(player?.isPlaying == true){
                player?.stop()
                player?.release()
                player = null
                sendStatusBroadcast("Stopped")
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun sendStatusBroadcast(status: String) {
        val broadcastIntent = Intent("com.example.musicplayer.MUSIC_STATUS")
        broadcastIntent.putExtra("status", status)
        sendBroadcast(broadcastIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

}