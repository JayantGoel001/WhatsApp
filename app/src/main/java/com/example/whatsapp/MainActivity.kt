package com.example.whatsapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        privacyPolicy.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse("https://www.whatsapp.com/legal/?lang=en#privacy-policy"))
            startActivity(intent)
        }
        termsOfService.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse("https://www.whatsapp.com/legal/?lang=en#terms-of-service"))
            startActivity(intent)
        }
        agreeAndContinue.setOnClickListener {
            val intent=Intent(this,EnterYourPhoneNumber::class.java)
            startActivity(intent)
        }

    }
}
