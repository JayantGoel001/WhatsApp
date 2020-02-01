package com.example.whatsapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginTop
import androidx.core.widget.addTextChangedListener
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_enter_your_phone_number.*
import org.json.JSONArray
import org.json.JSONException
import java.util.zip.Inflater

@Suppress("CAST_NEVER_SUCCEEDS")
class EnterYourPhoneNumber : AppCompatActivity() {
    private var url="https://restcountries.eu/rest/v2/all"
    private var countryList=HashMap<String,String>()
    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_your_phone_number)
        setSupportActionBar(toolbar)
        getArrayInitialized()
        var countryCode= "+91"
        var str:String = if(intent.getStringExtra("CountryName")==null) {
            countryCode="+91"
            "India"
        } else {
            countryCode=intent.getStringExtra("CountryCode")!!
            countryCodeNumber.setText(countryCode,TextView.BufferType.EDITABLE)
            intent.getStringExtra("CountryName")!!
        }
        var adapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayOf(str))
        spinner.adapter=adapter
        spinner.setOnTouchListener { _, _ ->
            val intent=Intent(this, ChooseACountry::class.java)
            intent.putExtra("countryName",str)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            true
        }
        whatsMyNumber.setOnClickListener {
            val toast=Toast.makeText(this,"Unable to get phone number from SIM. Please type in your phone number",Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
        countryCodeNumber.addTextChangedListener{
            str = when {
                countryCodeNumber.text.toString() in countryList -> {
                    countryCode=countryCodeNumber.text.toString()
                    countryList[countryCode]!!
                }
                countryCodeNumber.text.toString().isEmpty() -> {
                    "Choose a country"
                }
                else -> {
                    "invalid country code"
                }
            }
            adapter= ArrayAdapter(this,android.R.layout.simple_spinner_item, arrayOf(str))
            spinner.adapter=adapter
        }
        val view=layoutInflater.inflate(R.layout.alert_layout,null)
        val phoneNumberOfAlertDialog=view.findViewById<TextView>(R.id.phone_Number)

        next_button.setOnClickListener {
            phoneNumberOfAlertDialog.text="$countryCode ${phoneNumber.text}"
           AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("OK"
                ) { dialog, which ->
                    val intent=Intent(this,Verify::class.java)
                    intent.putExtra("phoneNumber",(phoneNumber.text).toString())
                    intent.putExtra("countryCode",countryCode)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                .setNeutralButton("EDIT"
                ) { dialog, which ->
                    Toast.makeText(this,"Please Enter your phone Number again",Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }.show()
        }
    }

    private fun getArrayInitialized() {
        val jsonArrayRequest = JsonArrayRequest(url,
            Response.Listener<JSONArray> { response ->
                try {
                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        if (jsonObject.getString("callingCodes").length in 5..8) {
                                countryList[(jsonObject.getString("callingCodes"))
                                    .substring(2,(jsonObject.getString("callingCodes"))
                                        .lastIndexOf("\""))]=jsonObject.getString("name")
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener {
                Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show()
            })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonArrayRequest)
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
