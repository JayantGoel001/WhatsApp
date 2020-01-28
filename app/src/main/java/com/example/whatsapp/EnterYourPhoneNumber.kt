package com.example.whatsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_enter_your_phone_number.*
import java.util.zip.Inflater

class EnterYourPhoneNumber : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_your_phone_number)
        toolbar.inflateMenu(R.menu.menu)
        val str="India"
        val adapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayOf(str))
        spinner.adapter=adapter
        spinner.setOnTouchListener { v, event ->
            startActivity(Intent(this, ChooseACountry::class.java))
            true
        }
    }


}
