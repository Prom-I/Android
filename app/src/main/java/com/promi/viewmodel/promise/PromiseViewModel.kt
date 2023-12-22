package com.promi.viewmodel.promise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promi.data.remote.model.Group
import com.promi.data.remote.model.Notification

class PromiseViewModel : ViewModel() {
    private val groupList = mutableListOf<Group>()
    private val _groupLiveData: MutableLiveData<List<Group>> = MutableLiveData()
    val groupLiveData: LiveData<List<Group>>
        get() = _groupLiveData


    init {
        // 그룹 더미 데이터
        groupList.add(Group("공학경진대회",4))
        groupList.add(Group("캡스톤 디자인",4))
        groupList.add(Group("안드로이드 스터디",3))
        groupList.add(Group("코딩테스트 스터디",2))
        groupList.add(Group("iOS 스터디",4))


        _groupLiveData.value = groupList
    }

    // 그룹 삭제 이벤트
    fun deleteGroup(group: Group){
        groupList.remove(group)
        _groupLiveData.value = groupList.toList()
    }
}