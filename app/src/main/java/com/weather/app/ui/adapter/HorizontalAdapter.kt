package com.weather.app.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.weather.app.R
import com.weather.app.data.model.DailyWeather
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HorizontalAdapter(private val list: ArrayList<DailyWeather>) :
    RecyclerView.Adapter<HorizontalAdapter.MyView>() {
    class MyView(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.imageWeather)
        var dayName: TextView = view.findViewById(R.id.day_name)
        var tempDay: TextView = view.findViewById(R.id.temp_day)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.weather_list_item,
                parent,
                false
            )
        return MyView(itemView)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val listData = list[position]

        Picasso.get().load(Uri.parse("https://openweathermap.org/img/wn/${listData.weather[0].icon}@2x.png")).placeholder(R.mipmap.ic_launcher).into(holder.imageView)
        holder.dayName.text = getDayName(listData.dt)
        holder.tempDay.text =  "${listData.temp.day}°"

    }
    private fun getDayName(s: Long): String? {
        return try {
            val sdf = SimpleDateFormat("EE")
            val netDate = Date(s * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}