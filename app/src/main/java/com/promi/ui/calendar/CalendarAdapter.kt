package com.promi.ui.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.databinding.ItemCalendarDateBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// 높이를 구하는데 필요한 LinearLayout과 DateCalendar를 사용할 때 필요한 date를 받는다.
class CalendarAdapter(val context: Context, val calendarLayout: LinearLayout, val date: Date, val thisMonth:Int) :
    RecyclerView.Adapter<CalendarAdapter.CalendarItemHolder>() {

    // CalendarDate를 이용하여 날짜 리스트 세팅
    var dateCalendar: DateCalendar = DateCalendar(date)
    var dataList: ArrayList<Int> = arrayListOf()
    
    init {
        dateCalendar.initBaseCalendar()
        dataList = dateCalendar.dateList
    }

    inner class CalendarItemHolder (itemBinding: ItemCalendarDateBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var itemCalendarDateText: TextView = itemBinding.itemCalendarDateText
        var itemCalendarDotView: View = itemBinding.itemCalendarDotView

        fun bind(data: Int, position: Int, context: Context) {
            val firstDateIndex = dateCalendar.prevTail
            val lastDateIndex = dataList.size - dateCalendar.nextHead - 1

            // 날짜 표시
            itemCalendarDateText.setText(data.toString())

            // 오늘 날짜 처리
            var dateString: String = SimpleDateFormat("dd", Locale.KOREA).format(date)
            var dateInt = dateString.toInt()

            // 현재 월의 1일 이전, 현재 월의 마지막일 이후 값의 텍스트를 회색처리
            if (position < firstDateIndex || position > lastDateIndex) {
                itemCalendarDateText.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                // itemCalendarDateText.setTextAppearance(R.style.notThisMonth)
                itemCalendarDotView.background = null
            }
            else{
                // 오늘 날짜 처리
                if (thisMonth==1 && dataList[position] == dateInt) {
//                    itemCalendarDateText.background = ContextCompat.getDrawable(context!!,
//                        R.drawable.shape_circle_stroke1
//                    )
                    itemCalendarDateText.setTextColor(ContextCompat.getColor(context!!,
                        R.color.mainGreen
                    ))
                }
                // 토, 일 색상 변경
                if((position%7) == 0){
                    itemCalendarDateText.setTextColor(ContextCompat.getColor(context!!,
                        R.color.black
                    ))
                }
                else if((position%7) == 6){
                    itemCalendarDateText.setTextColor(ContextCompat.getColor(context!!,
                        R.color.black
                    ))
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarItemHolder {
        return CalendarItemHolder(
            ItemCalendarDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CalendarItemHolder, position: Int) {
        // list_item_calendar 높이 지정
        val h = calendarLayout.height / 6
        holder.itemView.layoutParams.height = h
        holder?.bind(dataList[position], position, context)
    }

    override fun getItemCount(): Int = dataList.size
}
