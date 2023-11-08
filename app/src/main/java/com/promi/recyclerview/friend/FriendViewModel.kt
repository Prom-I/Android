package com.promi.recyclerview.friend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FriendViewModel : ViewModel() {
    private val _friends = MutableLiveData<List<Friend>>()
    val friends: LiveData<List<Friend>> = _friends

    init {
        // 초기 친구 목록 설정(더미 데이터)
        _friends.value = listOf(
            Friend("친구1", 123),
            Friend("친구2",123),
            Friend("친구3",123),
            Friend("친구4",123),
            Friend("친구5",123),
            Friend("친구6",123),
        )
    }

    // 친구 목록 업데이트 메소드
    fun updateFriends(newFriends: List<Friend>) {
        _friends.value = newFriends
    }
}
