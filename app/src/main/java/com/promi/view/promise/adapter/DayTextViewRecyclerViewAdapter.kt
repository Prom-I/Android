package com.promi.view.promise.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

// 시작 날짜를 입력받아서 7일치의 날짜를 그린다.
class DayTextViewRecyclerViewAdapter(
    private val startDay : String,)
    : RecyclerView.Adapter<DayTextViewRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val dayTextView : TextView = itemView.findViewById(R.id.tv_day) //tv_day
        val dateTextView : TextView = itemView.findViewById(R.id.tv_date) //tv_date
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_day_textview,parent,false)
//        view.updateLayoutParams {
//            width = itemWidthSize
//        }

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 7 // 무조건 7일
    }

    // LocalDate를 활용하여 날짜 하루씩 갱신
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 시작 날짜를 LocalDate로 파싱
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // 시간 포맷팅 지정
        val startDate = LocalDate.parse(startDay, formatter)

        // 각 아이템 위치에 맞는 날짜 계산
        val date = startDate.plusDays(position.toLong())

        // 요일과 날짜 설정
        val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
        val dayOfMonth = date.dayOfMonth

        // 월 구하기 (1월: 1, 2월: 2, ...)
        val month = date.monthValue

        // 홀더에 설정
        holder.dayTextView.text = dayOfWeek // 무슨 요일
        holder.dateTextView.text = "${month}월 ${dayOfMonth}일" // 몇월 며칠
    }
}