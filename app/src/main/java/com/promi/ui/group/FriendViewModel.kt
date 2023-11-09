package com.promi.ui.group

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promi.recyclerview.friend.Friend
import com.promi.recyclerview.miniProfile.MiniProfile

class FriendViewModel : ViewModel() {
    private val _friends = MutableLiveData<List<Friend>>()
    val friends: LiveData<List<Friend>> = _friends

    // 선택된 친구 목록 LiveData 추가
    private val _selectedFriends = MutableLiveData<List<MiniProfile>>()
    val selectedFriends: LiveData<List<MiniProfile>> = _selectedFriends

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

        // 초기 선택된 친구 목록 설정
        _selectedFriends.value = listOf(
            // 초기 선택된 친구 데이터
            MiniProfile("test","테스트1"),
            MiniProfile("test","테스트2"),
            MiniProfile("test","테스트3"),
            MiniProfile("test","테스트4"),
            MiniProfile("test","테스트5")
        )
    }

    // 친구 목록 업데이트 메소드
    fun updateFriends(newFriends: List<Friend>) {
        _friends.value = newFriends
    }

    // 선택된 친구 목록 업데이트 메소드
    fun updateSelectedFriends(newSelectedFriends: List<MiniProfile>) {
        _selectedFriends.value = newSelectedFriends
    }


    // 선택된 친구 추가
    fun addSelectedFriend(friend: Friend) {
        Log.d("체크박스",": 친구 추가 메소드 호출됨")
        val currentList = _selectedFriends.value ?: emptyList()
        _selectedFriends.value = currentList + MiniProfile("test", friend.friendName)
    }

    // 선택된 친구 제거
    fun removeSelectedFriend(friend: Friend) {
        Log.d("체크박스",": 친구 삭제 메소드 호출됨")
        val currentList = _selectedFriends.value ?: emptyList()
        _selectedFriends.value = currentList.filter { it.name != friend.friendName }
    }
}
