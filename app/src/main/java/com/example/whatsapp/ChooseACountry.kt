package com.example.whatsapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.activity_choose_acountry.*
import kotlinx.android.synthetic.main.content_choose_acountry.*
import org.json.JSONArray
import org.json.JSONException

class ChooseACountry : AppCompatActivity() {
    private var countryList:ArrayList<CountryData> = ArrayList()
    private var url="https://restcountries.eu/rest/v2/all"
    lateinit var initialName:String
    private lateinit var adapter:CountryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_acountry)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initialName=intent.extras!!.get("countryName").toString()
        adapter=CountryAdapter(countryList,this,initialName)
        val linearLayoutManager=LinearLayoutManager(this)
        linearLayoutManager.orientation=LinearLayoutManager.VERTICAL
        val divideItemDecoration=DividerItemDecoration(this,linearLayoutManager.orientation)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=linearLayoutManager
        recyclerView.addItemDecoration(divideItemDecoration)
        recyclerView.adapter=adapter
        getData()
    }

    private fun getData() {
        val progressDialog=ProgressDialog(this)
        progressDialog.setMessage("Loading..")
        progressDialog.show()
        val jsonArrayRequest=JsonArrayRequest(url,
            Response.Listener<JSONArray> { response ->
                try {
                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        if(jsonObject.getString("callingCodes").length in 5..8) {
                            val countryData = CountryData(
                                jsonObject.getString("name"),
                                jsonObject.getString("callingCodes"),
                                jsonObject.getString("flag"),
                                jsonObject.getString("nativeName")
                            )
                            if(countryData.name==initialName)
                            {
                                countryList.add(0,countryData)
                            }
                            else {
                                countryList.add(countryData)
                            }
                        }
                    }
                } catch (e:JSONException) {
                    e.printStackTrace()
                    progressDialog.dismiss()
                }
                adapter.notifyDataSetChanged()
                progressDialog.dismiss()
            }, Response.ErrorListener {
                Toast.makeText(this,"Network Error",Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
        })
        val requestQueue=Volley.newRequestQueue(this)
        requestQueue.add(jsonArrayRequest)
    }

    override fun onBackPressed() {
        val intent=Intent(this,EnterYourPhoneNumber::class.java)
        startActivity(intent)
        super.onBackPressed()
    }
}
