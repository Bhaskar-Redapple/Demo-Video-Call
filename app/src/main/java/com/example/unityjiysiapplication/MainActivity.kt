package com.example.unityjiysiapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var playGame : Button
    private lateinit var doVideoCall : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playGame = findViewById(R.id.bt_play_game)
        doVideoCall = findViewById(R.id.bt_video_call)

        playGame.setOnClickListener {
            startActivity(Intent(this, GamePlayActivity::class.java))
        }

        doVideoCall.setOnClickListener {
            startActivity(Intent(this, JitsiVideoCallActivity::class.java))
        }
    }



}