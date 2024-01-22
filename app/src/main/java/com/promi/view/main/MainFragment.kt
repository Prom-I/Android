package com.promi.view.main

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentMainBinding
import com.promi.view.main.adapter.CalendarFragmentStateAdapter
import com.promi.view.main.adapter.MyFriendListMiniProfileAdapter
import com.promi.viewmodel.friend.FriendViewModel
import com.promi.viewmodel.main.CalendarViewModel
import com.promi.viewmodel.main.MainViewModel
import java.util.Locale

class MainFragment : BaseFragment<FragmentMainBinding> (R.layout.fragment_main) {
    private val TAG = "MainFragment"

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

        (activity as MainActivity).setToolbar(false, "")

        myFriendListMiniProfileAdapter = MyFriendListMiniProfileAdapter()
        with(binding.recyclerviewMyFriendListMiniProfile){ // 메인 화면의 친구 목록 어댑터
            setHasFixedSize(true)
            adapter = myFriendListMiniProfileAdapter
            layoutManager = LinearLayoutManager(this@MainFragment.context, LinearLayoutManager.HORIZONTAL, true)
        }

        friendViewModel.friends.observe(this){itemList->
            myFriendListMiniProfileAdapter?.submitList(itemList.toMutableList())
        }

        setCalendar()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.icNotification.setOnClickListener{
            navController.navigate(R.id.action_navigation_calendar_to_navigation_notification)
        }

        binding.icAddFriend.setOnClickListener{
            navController.navigate(R.id.action_navigation_calendar_to_navigation_add_friends)
        }
    }

    private fun setCalendar(){
        val calendarViewPager = binding.viewPager
        val fragmentStateAdapter = CalendarFragmentStateAdapter(requireActivity())
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