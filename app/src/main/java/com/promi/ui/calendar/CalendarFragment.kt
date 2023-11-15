package com.promi.ui.calendar

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentCalendarBinding
import java.util.*

class CalendarFragment(index: Int): BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar){
    private val viewModel by lazy {
        ViewModelProvider(requireParentFragment())[CalendarViewModel::class.java]
    }

    private lateinit var currentDate: Date
    private var pageIndex = index
    private var thisMonth = 0

    override fun initDataBinding() {
        super.initDataBinding()

        viewModel.setPosition(pageIndex)
        initDate()
        initCalendar()
    }

    private fun initDate() {
        // 이번달 pageIndex는 0
        pageIndex -= (Int.MAX_VALUE/2)
        if(pageIndex==0)
            thisMonth=1
        val date = Calendar.getInstance().run {
            add(Calendar.MONTH, pageIndex)
            time
        }
        Log.d("poss",pageIndex.toString())
        currentDate = date
    }
    private fun initCalendar() {
        val calendarAdapter = CalendarAdapter(binding.calendarLayout, currentDate, thisMonth)
        binding.listCalendarDate.apply {
            adapter = calendarAdapter
            layoutManager = GridLayoutManager(requireContext(), 7, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        calendarAdapter.setItemClickListener(object: CalendarAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                // val todoData = calendarAdapter.itemList[position]
                // PostDialog(userEmail, post).show(parentFragmentManager,"PostDialog")
            }
        })
    }
}