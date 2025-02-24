package com.example.musicplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() , View.OnClickListener {
    private lateinit var playBtn: ImageView
    private lateinit var pauseBtn: ImageView
    private lateinit var stopBtn: ImageView
    private lateinit var tvStatus: TextView

    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        playBtn = findViewById(R.id.playBtn)
        pauseBtn = findViewById(R.id.pauseBtn)
        stopBtn = findViewById(R.id.stopBtn)
        tvStatus = findViewById(R.id.tvStatus)

        playBtn.setOnClickListener(this)
        pauseBtn.setOnClickListener(this)
        stopBtn.setOnClickListener(this)

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val status = intent?.getStringExtra("status")
                if (status != null) {
                    when (status) {
                        "Playing" -> {
                            tvStatus?.setText(getString(R.string.play_status))
                        }

                        "Paused" -> {
                            tvStatus?.setText(getString(R.string.pause_status))
                        }

                        "Stopped" -> {
                            tvStatus?.setText(getString(R.string.stop_status))
                        }
                    }
                }
            }
        }

        val filter = IntentFilter("com.example.musicplayer.MUSIC_STATUS")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED)
        }

    }

    override fun onClick(v: View?) {
        when (v) {
            playBtn -> {
                var intent = Intent(this, MyService::class.java)
                intent.putExtra("Function", "Play")
                startService(intent)
            }

            pauseBtn -> {
                var intent = Intent(this, MyService::class.java)
                intent.putExtra("Function", "Pause")
                startService(intent)
            }

            stopBtn -> {
                var intent = Intent(this, MyService::class.java)
                intent.putExtra("Function", "Stop")
                startService(intent)
            }

        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}