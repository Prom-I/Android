package com.promi.view.main

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentCalendarBinding
import com.promi.view.main.adapter.CalendarAdapter
import com.promi.view.main.dialog.TodoDialog
import com.promi.viewmodel.main.CalendarViewModel
import java.util.Calendar
import java.util.Date
import kotlin.properties.Delegates

class CalendarFragment(index: Int): BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar){
    private val viewModel by lazy {
        ViewModelProvider(this)[CalendarViewModel::class.java]
    }

    private lateinit var currentDate: Date
    private var pageIndex = index
    private var isThisMonth by Delegates.notNull<Boolean>()

    override fun initDataBinding() {
        super.initDataBinding()

        initDate()
        initCalendar()
    }

    private fun initDate() {
        // 이번달 pageIndex는 0
        pageIndex -= (Int.MAX_VALUE/2)
        isThisMonth = pageIndex==0

        val date = Calendar.getInstance().run {
            add(Calendar.MONTH, pageIndex)
            time
        }

        currentDate = date
    }
    private fun initCalendar() {
        val calendarAdapter = CalendarAdapter(binding.calendarLayout, currentDate, isThisMonth)
        binding.listCalendarDate.apply {
            adapter = calendarAdapter
            layoutManager = GridLayoutManager(requireContext(), 7, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        calendarAdapter.setItemClickListener(object: CalendarAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                // val todoData = calendarAdapter.itemList[position]
                val month = calendarAdapter.month
                val date = calendarAdapter.dateData[position].toString()
                val day = when (position%7){
                    0 -> "일"
                    1 -> "월"
                    2 -> "화"
                    3 -> "수"
                    4 -> "목"
                    5 -> "금"
                    6 -> "토"
                    else -> "일"
                }

                TodoDialog(month, date, day).show(parentFragmentManager,"TodoDialog")
            }
        })
    }
}