package com.hungerdeal

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        object :CountDownTimer(3000, 10){
            override fun onFinish() {
                startActivity(Intent(this@MainActivity, Dashboard::class.java))
                finish()
            }

            override fun onTick(p0: Long) {
            }
        }.start()
    }
}