package com.promi.view.notification

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentNotificationBinding
import com.promi.view.notification.adapter.NotificationAdapter
import com.promi.viewmodel.notification.NotificationViewModel

// 알림 화면
class NotificationFragment:BaseFragment<FragmentNotificationBinding> (R.layout.fragment_notification){

    private val viewModel by viewModels<NotificationViewModel>()
    private var notificationAdapter: NotificationAdapter? = null

    override fun initStartView() {
        super.initStartView()

        notificationAdapter = NotificationAdapter(
            notificationDeleteEvent = { notification -> viewModel.deleteNotification(notification) } // viewModel의 삭제 함수를 어뎁터로 전달
        )

        with(binding.recyclerviewNotification){
            setHasFixedSize(true)
            adapter = notificationAdapter
            layoutManager = LinearLayoutManager(this@NotificationFragment.context)
        }

        binding.icNotificationBack.setOnClickListener{
            navController.popBackStack()
        }
    }
    override fun initDataBinding() {
        super.initDataBinding()

        viewModel.notificationLiveData.observe(this){
            notificationAdapter?.submitList(it.toMutableList()) // 변경된 리스트를 어뎁터에 전달
        }
    }



}