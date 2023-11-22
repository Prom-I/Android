package com.promi.ui.main

import android.util.Log
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentMainBinding
import com.promi.ui.calendar.CalendarViewModel
import com.promi.ui.calendar.FragmentStateAdapter
import com.promi.ui.group.FriendViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainFragment : BaseFragment<FragmentMainBinding> (R.layout.fragment_main) {
    private lateinit var currentDate: Date
    private var pageIndex=0
    private var myFriendListMiniProfileAdapter : MyFriendListMiniProfileAdapter? = null

    private val friendViewModel by viewModels<FriendViewModel>()

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val calendarViewModel by lazy {
        ViewModelProvider(requireParentFragment())[CalendarViewModel::class.java]
    }

    override fun initStartView() {
        super.initStartView()

        binding.icNotification.setOnClickListener{
            navController.navigate(R.id.action_navigation_calendar_to_navigation_notification)
        }

        binding.icAddFriend.setOnClickListener{
            navController.navigate(R.id.action_navigation_calendar_to_navigation_add_friends)
        }

        myFriendListMiniProfileAdapter = MyFriendListMiniProfileAdapter()

        with(binding.recyclerviewMyFriendListMiniProfile){ // 메인 화면의 친구 목록 어댑터
            setHasFixedSize(true)
            adapter = myFriendListMiniProfileAdapter
            layoutManager = LinearLayoutManager(this@MainFragment.context, LinearLayoutManager.HORIZONTAL, true)
        }
    }
    override fun initDataBinding() {
        super.initDataBinding()

        setCalendar()

        friendViewModel.friends.observe(this){itemList->
            myFriendListMiniProfileAdapter?.submitList(itemList.toMutableList())
        }


    }

    private fun setCalendar(){
        val calendarViewPager = binding.viewPager
        val fragmentStateAdapter = FragmentStateAdapter(childFragmentManager, lifecycle)

        calendarViewPager.adapter = fragmentStateAdapter
        calendarViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        fragmentStateAdapter.apply {
            setMonth(this.calendarPosition)
            // 처음 나타나는 페이지 위치 설정. true시 이동 애니메이션 보임. fasle시 안보임
            calendarViewPager.setCurrentItem(this.calendarPosition, false)
            Log.d("poss_thisPosition", (Int.MAX_VALUE/2).toString())
        }

        // 페이지 변경시 콜백함수 호출
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
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
        currentDate = date
        // 포맷 적용
        val dateTime: String = SimpleDateFormat(
            requireContext().getString(R.string.calendar_year_month_format),
            Locale.KOREA
        ).format(date.time)

        binding.tvYearMonth.text = dateTime
    }
}