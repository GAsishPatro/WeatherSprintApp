package com.personal.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.personal.myapplication.R
import com.personal.myapplication.databinding.ActivityMainBinding
import java.util.Timer
import java.util.TimerTask

class SplashActivity : AppCompatActivity() {

    private lateinit var splashImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashImage=findViewById(R.id.splashI)

        val anim= AnimationUtils.loadAnimation(splashImage.context, R.anim.splash_anim)

        splashImage.startAnimation(anim)

        Timer().schedule(object: TimerTask(){
            override fun run() {
                val i = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(i)
                finish()
            }

        },3000)
    }


}