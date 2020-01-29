package com.example.whatsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_enter_your_phone_number.*
import java.util.zip.Inflater

class EnterYourPhoneNumber : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_your_phone_number)

        setSupportActionBar(toolbar)
        val str="India"
        val adapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayOf(str))
        spinner.adapter=adapter
        spinner.setOnTouchListener { v, event ->
            startActivity(Intent(this, ChooseACountry::class.java))
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(R.id.Help==item.itemId)
        {
            startActivity(Intent(this,HelpActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}
