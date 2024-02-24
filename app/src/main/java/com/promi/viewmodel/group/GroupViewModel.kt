package com.promi.viewmodel.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promi.data.remote.model.Group
import com.promi.data.remote.model.GroupMember
import com.promi.data.remote.model.Promise

class GroupViewModel : ViewModel() {
    // promise
    private val promiseList = mutableListOf<Promise>()
    private val _promise = MutableLiveData<List<Promise>>()
    val promises: LiveData<List<Promise>> = _promise

    // group member list
    private val _groupMember = MutableLiveData<List<GroupMember>>()
    val groupMembers: LiveData<List<GroupMember>> = _groupMember

    private val _groupLiveData = MutableLiveData<List<Group>>()
    val groups: LiveData<List<Group>> = _groupLiveData

    init {
        // 초기 친구 목록 설정(더미 데이터)
        promiseList.add(Promise("3차 회의","2023.12.20~2023.12.31",20))
        promiseList.add(Promise("2차 회의","2023.11.30~2023.12.01",5))
        promiseList.add(Promise("1차 회의","2023/08/28(월) 09:30",0))


        _promise.value = promiseList

        val groupMemberDummyList = listOf(
            GroupMember("최강",111),
            GroupMember("김지원",222),
            GroupMember("남윤형",333)
        )

        _groupMember.value = groupMemberDummyList

        // 그룹 더미 데이터
        val groupDummyList = listOf(
            Group("공학경진대회",4),
            Group("캡스톤 디자인",4),
            Group("안드로이드 스터디",3),
            Group("코딩테스트 스터디",2),
            Group("iOS 스터디",4)
        )

        _groupLiveData.value = groupDummyList

    }

    fun deletePromise(promise: Promise){
        promiseList.remove(promise)
        _promise.value = promiseList.toList()
    }
}
