package com.example.poc.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.poc.ui.MainActivity

class SplashScreen: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val time:Long =1000

        Handler().postDelayed(Runnable { val intent =  Intent(SplashScreen@this, MainActivity::class.java)
            startActivity(intent)
            finish()

        },time)

    }
}