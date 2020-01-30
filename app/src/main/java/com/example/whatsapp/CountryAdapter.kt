package com.example.whatsapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.VectorDrawable
import android.media.Image
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso

class CountryAdapter(val arr:ArrayList<CountryData>,val context:Context):RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
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