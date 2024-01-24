package com.promi.view.promise.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// 전체 시간의 길이와, 시작 시간을 입력받아 1시간 단위로 시간 텍스트뷰를 그린다.
class TimeTextViewRecyclerViewAdapter(private val timeSize : Int, private val startTime : String)
    : RecyclerView.Adapter<TimeTextViewRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val timeTextView: TextView = itemView.findViewById(R.id.tv_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_time_textview,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return timeSize
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 시작 시간을 기점으로 position만큼 1시간씩 더하면서 n개의 시간 그리기
        // "09:00" 같은 문자열을 LocalTime 객체로 파싱
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val startTime = LocalTime.parse(startTime, formatter)

        // position에 따라 시간을 1시간씩 증가
        val timeToShow = startTime.plusHours(position.toLong())

        // ViewHolder에 시간을 설정
        holder.timeTextView.text = timeToShow.format(formatter)
    }
}