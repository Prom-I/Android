package com.promi.viewmodel.promise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promi.data.remote.model.Group
import com.promi.data.remote.model.Notification
import com.promi.data.remote.model.Promise

class PromiseViewModel : ViewModel() {

    // 보유 중인 그룹 목록
    private val groupList = mutableListOf<Group>()
    private val _groupLiveData: MutableLiveData<List<Group>> = MutableLiveData()
    val groupLiveData: LiveData<List<Group>> = _groupLiveData

    private val _promise = MutableLiveData<List<Promise>>()
    val promises: LiveData<List<Promise>> = _promise // 관측 가능


    // 아이템 중 하나가 스와이프된 상태라면, 다른 아이템은 스와이프 할 수 없어야하므로
    private var itemSwipeState : Boolean = false

    init {
        // 그룹 더미 데이터
        groupList.add(Group("공학경진대회",4))
        groupList.add(Group("캡스톤 디자인",4))
        groupList.add(Group("안드로이드 스터디",3))
        groupList.add(Group("코딩테스트 스터디",2))
        groupList.add(Group("iOS 스터디",4))



        // 초기 친구 목록 설정(더미 데이터)
        // 추후, 서버로부터 친구 목록 얻어오는 코드로 변경 필요
        val promiseDummyData = listOf(
            Promise("3차 회의","2023.12.20~2023.12.31",20),//D-20
            Promise("2차 회의","2023.11.30~2023.12.01",5), //D-5
            Promise("1차 회의","2023/08/28(월) 09:30",0), //완료

        )
        _promise.value = promiseDummyData


        _groupLiveData.value = groupList
    }

    // 아이템 스와이프 상태 반환
    fun getItemSwipeState() : Boolean{
        return this.itemSwipeState
    }

    // 아이템 스와이프 상태 활성화/비활성화
    fun setItemSwipeState(state : Boolean){
        this.itemSwipeState = state
    }

    // 그룹 삭제 이벤트
    fun deleteGroup(group: Group){
        groupList.remove(group)
        _groupLiveData.value = groupList.toList()
    }
}