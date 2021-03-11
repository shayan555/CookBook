package com.example.cookbook.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.cookbook.R

class SplaceAActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splace_a)
        supportActionBar!!.hide()
        var handler = Handler()
        handler.postDelayed(object :Runnable{
            override fun run() {
                startActivity(Intent(this@SplaceAActivity,MainActivity::class.java))
                finish()
            }

        },2000)
    }
}