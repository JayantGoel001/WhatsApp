package com.example.whatsapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.VectorDrawable
import android.media.Image
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso

class CountryAdapter(val arr:ArrayList<CountryData>,val context:Context,val str:String):RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.country_data_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =arr.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country=arr[position]
        holder.countryName.text=country.name
        val x=(country.callingCodes).lastIndexOf("\"")
        holder.countryCode.text="+"+(country.callingCodes).subSequence(2,x)
        holder.countryLanguage.text=country.nativeName
        GlideToVectorYou.init().with(context).load(Uri.parse(country.flag),holder.countryFlag)
        if(country.name==str)
        {
            holder.countryName.setTextColor(Color.parseColor("#00574B"))
            holder.countryName.textSize=20.0f
            holder.countryCode.setTextColor(Color.parseColor("#00574B"))
            holder.countryLanguage.setTextColor(Color.parseColor("#00574B"))
            holder.tick.isVisible=true
        }
        else
        {
            holder.countryName.setTextColor(Color.parseColor("#000000"))
            holder.countryName.textSize=12.0f
            holder.countryCode.setTextColor(Color.parseColor("#000000"))
            holder.countryLanguage.setTextColor(Color.parseColor("#000000"))
            holder.tick.isVisible=false
        }
        holder.itemView.setOnClickListener {
            val intent=Intent(context,EnterYourPhoneNumber::class.java)
            intent.putExtra("CountryName",country.name)
            intent.putExtra("CountryCode",((holder.countryCode.text).substring( 1)))
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        var countryFlag=itemView.findViewById<ImageView>(R.id.imageView)
        var countryName=itemView.findViewById<TextView>(R.id.textView)
        var countryLanguage=itemView.findViewById<TextView>(R.id.language)
        var countryCode=itemView.findViewById<TextView>(R.id.countryCode)
        var tick=itemView.findViewById<ImageView>(R.id.tickOrNot)

    }
}