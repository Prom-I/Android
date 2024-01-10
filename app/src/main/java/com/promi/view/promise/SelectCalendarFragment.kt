package com.promi.view.promise

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentCalendarBinding
import com.promi.view.promise.adapter.SelectCalendarAdapter
import com.promi.viewmodel.promise.SelectCalendarViewModel
import java.util.Calendar
import java.util.Date
import kotlin.properties.Delegates

class SelectCalendarFragment(index: Int): BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar){
    private val viewModel by lazy {
        ViewModelProvider(this)[SelectCalendarViewModel::class.java]
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
        val calendarAdapter = SelectCalendarAdapter(binding.calendarLayout, currentDate, isThisMonth)
        binding.listCalendarDate.apply {
            adapter = calendarAdapter
            layoutManager = GridLayoutManager(requireContext(), 7, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        viewModel.startDateLiveData.observe(viewLifecycleOwner){
            try {
                val position = it.split(" ")[0].toInt()
                calendarAdapter.startDatePos = position
                Log.d("startDatee", position.toString())
            } catch (e: NumberFormatException) {
                // NumberFormatException이 발생했을 때의 처리
                Log.e("Error", "Invalid number format: ${e.message}")
            }
        }

        calendarAdapter.setItemClickListener(object: SelectCalendarAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                // val todoData = calendarAdapter.itemList[position]
//                val month = calendarAdapter.month
//                val date = calendarAdapter.dateData[position].toString()
//                val day = when (position%7){
//                    0 -> "일"
//                    1 -> "월"
//                    2 -> "화"
//                    3 -> "수"
//                    4 -> "목"
//                    5 -> "금"
//                    6 -> "토"
//                    else -> "일"
//                }
                val month = calendarAdapter.month
                val date = calendarAdapter.dateData[position].toString()

                viewModel.setDate("${position} ${month}월 ${date}일")
            }
        })
    }
}