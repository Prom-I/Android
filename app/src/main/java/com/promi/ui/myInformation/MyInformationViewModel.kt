package com.promi.ui.myInformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promi.recyclerview.friend.Friend

class MyInformationViewModel : ViewModel() {

    private val _myFriends = MutableLiveData<List<Friend>>() //외부 수정 방지용
    val myFriends: LiveData<List<Friend>> = _myFriends


    // 원본 데이터를 저장해두기 위한변수(검색 이후에 되돌아 올때)
    private var allMyFriendsList: List<Friend> = listOf()

    init {
        // 초기 친구 목록 설정(더미 데이터)
        // 추후, 서버로부터 친구 목록 얻어오는 코드로 변경 필요
        val initialList = listOf(
            Friend("최강",555),
            Friend("남윤형",666),
            Friend("김지원",777),
        )

        _myFriends.value = initialList //친구데이터의 MutableLiveData에 친구 정보 기입

        allMyFriendsList = _myFriends.value ?: listOf()

    }


    // 친구 검색 결과를 _myFriends의 값으로 반환
    // allMyFriendList에 전체 친구의 이름이 기록되어 있기 때문에, 검색결과를 반환해도 원본으 수정되지 않음
    fun searchFriend(name:String){
        //전체 친구 리스트에서 해당 이름을 포함하고 있는 이름으로 반환
        _myFriends.value = allMyFriendsList.filter {
            it.friendName.contains(name,ignoreCase = true)
        }
    }


}