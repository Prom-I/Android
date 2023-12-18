package com.promi.viewmodel.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promi.data.remote.model.Notification

class NotificationViewModel: ViewModel() {

    private val notificationList = mutableListOf<Notification>()
    private val _notificationLiveData:MutableLiveData<List<Notification>>  = MutableLiveData()
    val notificationLiveData: LiveData<List<Notification>>
        get() = _notificationLiveData

    init {
        // 알림 더미 데이터
        notificationList.add(Notification("", "이자민님이 친구 신청을 보냈습니다.", "1분 전"))
        notificationList.add(Notification("", "투밋투미가 새로워졌어요 !", "어제"))
        notificationList.add(Notification("", "프로미 팀 그룹에 초대되었습니다. :D", "1개월 전"))

        _notificationLiveData.value = notificationList
    }

    // 알림 확인 버튼 클릭 시 삭제
    fun deleteNotification(notification : Notification){
        notificationList.remove(notification)
        _notificationLiveData.value = notificationList.toList()
    }

}