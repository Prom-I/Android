package com.promi.view.promise.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.promi.view.promise.SelectCalendarFragment

class SelectCalendarFragmentStateAdapter(fragmentActivity: FragmentActivity)
    :FragmentStateAdapter(fragmentActivity){
    private val pageCount = Int.MAX_VALUE
    val calendarPosition = (Int.MAX_VALUE/2)

    override fun getItemCount(): Int = pageCount
    override fun createFragment(position: Int): Fragment {
        return SelectCalendarFragment(position)
    }
}