package com.promi.view.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.databinding.ItemCalendarDateBinding
import com.promi.util.DateCalendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// 높이를 구하는데 필요한 LinearLayout과 DateCalendar를 사용할 때 필요한 date를 받는다.
class CalendarAdapter(private val calendarLayout: LinearLayout, val date: Date, val thisMonth:Int) :
    RecyclerView.Adapter<CalendarAdapter.CalendarItemHolder>() {
    lateinit var context: Context

    // CalendarDate를 이용하여 날짜 리스트 세팅
    var dateCalendar: DateCalendar = DateCalendar(date)
    lateinit var year: String
    lateinit var month: String
    var dateData: ArrayList<Int> = arrayListOf()

    // 할 일 데이터
    // var itemList:
    
    init {
        dateCalendar.initBaseCalendar()
        year = dateCalendar.year
        month = dateCalendar.month
        dateData = dateCalendar.dateList
    }

    inner class CalendarItemHolder (itemBinding: ItemCalendarDateBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var layoutCalendarDate: View = itemBinding.layoutCalendarDate
        var itemCalendarDateText: TextView = itemBinding.itemCalendarDateText
        var listCalendarTodo: ListView = itemBinding.listCalendarTodo

        fun bind(data: Int, position: Int) {
            val firstDateIndex = dateCalendar.prevTail
            val lastDateIndex = dateData.size - dateCalendar.nextHead - 1

            // 날짜 표시
            itemCalendarDateText.text = data.toString()

            // 오늘 날짜 처리
            var dateString = SimpleDateFormat("dd", Locale.KOREA).format(date)
            var dateInt = dateString.toInt()

            // 현재 월의 1일 이전, 현재 월의 마지막일 이후 값의 텍스트를 회색처리
            if (position < firstDateIndex || position > lastDateIndex) {
                itemCalendarDateText.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                // itemCalendarDateText.setTextAppearance(R.style.notThisMonth)
            }
            else{
                // 오늘 날짜 처리
                if (thisMonth==1 && dateData[position] == dateInt) {
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
        context = parent.context
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
        holder?.bind(dateData[position], position)

        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        holder.layoutCalendarDate.setOnClickListener {
            itemClickListener?.onClick(it, position)
        }
    }
    // (2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    // (4) setItemClickListener로 설정한 함수 실행
    private var itemClickListener : OnItemClickListener? = null

    override fun getItemCount(): Int = dateData.size
}
