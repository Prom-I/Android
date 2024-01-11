package com.promi.view.promise

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentSelectPromiseDateBinding
import com.promi.view.main.adapter.FragmentStateAdapter
import java.util.Locale

class SelectPromiseDateFragment : BaseFragment<FragmentSelectPromiseDateBinding> (R.layout.fragment_select_promise_date) {
    private var pageIndex = 0

    override fun initStartView() {
        super.initStartView()

        (activity as MainActivity).setToolbar(true, "그룹 이름")

        setCalendar()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.switchIsAllDay.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.ConstraintSelectPromiseDate.visibility = View.GONE
            } else {
                binding.ConstraintSelectPromiseDate.visibility = View.VISIBLE
            }
        }
    }

    private fun setCalendar(){
        val calendarViewPager = binding.viewPager
        val fragmentStateAdapter = FragmentStateAdapter(requireActivity())
        calendarViewPager.adapter = fragmentStateAdapter
        calendarViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        fragmentStateAdapter.apply {
            // 처음 나타나는 페이지 위치 설정. true시 이동 애니메이션 보임. fasle시 안보임
            calendarViewPager.setCurrentItem(this.calendarPosition, false)
        }

        // 페이지 변경시 콜백함수 호출
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                setMonth(position)
            }
        })
    }

    fun setMonth(pos:Int){
        // 이번달 pageIndex는 0
        pageIndex = pos-(Int.MAX_VALUE/2)

        val date = Calendar.getInstance().run {
            add(Calendar.MONTH, pageIndex)
            time
        }

        // 포맷 적용
        val dateTime: String = SimpleDateFormat(
            requireContext().getString(R.string.calendar_year_month_format),
            Locale.KOREA
        ).format(date.time)

        binding.tvYearMonth.text = dateTime
    }
}