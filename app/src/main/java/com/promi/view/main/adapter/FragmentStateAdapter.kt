package com.promi.view.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.promi.view.main.CalendarFragment

class FragmentStateAdapter(fm: FragmentManager, lc: Lifecycle)
    :FragmentStateAdapter(fm, lc){
    private val pageCount = Int.MAX_VALUE
    val calendarPosition = (Int.MAX_VALUE/2)

    override fun getItemCount(): Int = pageCount
    override fun createFragment(position: Int): Fragment {
        return CalendarFragment(position)
    }
}