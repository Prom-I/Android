package com.promi.ui.group

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promi.recyclerview.friend.Friend
import com.promi.recyclerview.miniProfile.MiniProfile

class FriendViewModel : ViewModel() {
    //전체 친구 목록 => 원본은 수정하면 안됨, 복사본을 반영해야함
    private val _friends = MutableLiveData<List<Friend>>()
    val friends: LiveData<List<Friend>> = _friends

    // 선택된 친구 목록 LiveData 추가
    private val _selectedFriends = MutableLiveData<List<MiniProfile>>()
    val selectedFriends: LiveData<List<MiniProfile>> = _selectedFriends

    // 원본 데이터를 저장해두기 위한변수
    private var allFriendsList: List<Friend> = listOf()


    /*
    * _frineds는 수정 가능한 MutableLiveData
    * friends는 읽기전용(수정 불가) LiveData
    * => 외부에서 데이터를 관측할 수만 있고, 데이터 값을 변경시킬수는 없음
    * => 함수를 통해서만, 친구와 선택된 친구에 대한 값을 변경할 수 있도록 위와같이 작성하였음
    * (예를 들어, 외부에서 관측할때는 friends와 selectedFriends를 관측하지만,
    * 값을 수정할때는 함수를 호출하여 _friends의 값을 변경함, friends는 _friends의 참조를 값으로 소유하므로, 변경시 같이 변경됨
    * 위와 같이 작성함으로서, 캡슐화를 통해 뷰 모델의 데이터를 보호하고, UI컨트롤러가 직접적으로 값을 변경하는 것을 방지하였음)
    * */


    init {
        // 초기 친구 목록 설정(더미 데이터)
        // 추후, 서버로부터 친구 목록 얻어오는 코드로 변경 필요
        val initialList = listOf(
            Friend("최강",555),
            Friend("남윤형",666),
            Friend("김지원",777),
            Friend("최강",555),
            Friend("남윤형",666),
            Friend("김지원",777),
            Friend("최강",555),
            Friend("남윤형",666),
            Friend("김지원",777)
        )

        _friends.value = initialList //친구데이터의 MutableLiveData에 친구 정보 기입

        // _friends.value가 null이 아님을 보장한 후 allFriendsList에 백업
        allFriendsList = _friends.value ?: listOf()


        // 초기 선택된 친구 목록 설정
        _selectedFriends.value = listOf(
            // 초기 선택된 친구 데이터
            //MiniProfile("test","친구1",111),
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


    //editText에 검색된 내용에 맞춰 검색 결과를 반환
    fun searchFriend(friendName: String){
        Log.d("검색","${friendName}에 일치하는 친구 탐색")
        //전체 친구 목록에서 친구이름을 포함하고 있는경우로 필터링 하여 친구 MutableLiveData 반환
        _friends.value = allFriendsList.filter {
            it.friendName.contains(friendName, ignoreCase = true)
        }
    }

    fun setFriendsListToAllFriendList(){
        _friends.value = allFriendsList
    }



    // 선택된 친구 추가
    fun addSelectedFriend(friend: Friend) {
        Log.d("체크박스",": 친구 추가 메소드 호출됨")
        val currentList = _selectedFriends.value ?: emptyList()
        _selectedFriends.value = currentList + MiniProfile("test", friend.friendName,friend.friendCode)
    }

    // 선택된 친구 제거 => 아이디를 통해 탐색하도록 변경
    fun removeSelectedFriend(id: Int) {
        Log.d("체크박스",": 친구 삭제 메소드 호출됨")
        val currentList = _selectedFriends.value ?: emptyList()
        _selectedFriends.value = currentList.filter { it.id != id } //아이디와 일치하는 유저 제외
    }


}
