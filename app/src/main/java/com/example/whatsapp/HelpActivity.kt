package com.example.whatsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_help.*

class HelpActivity : AppCompatActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        FAQ.setOnClickListener {
            startActivity(Intent(ACTION_VIEW, Uri.parse("https://faq.whatsapp.com/en/android/20970873?_fb_noscript=1")))
        }

    }
}
