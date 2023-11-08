package com.promi.ui.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.databinding.FragmentAddFriendsBinding
import com.promi.databinding.FragmentCreateGroupBinding
import com.promi.recyclerview.friend.Friend
import com.promi.recyclerview.friend.FriendRecyclerViewAdapter
import com.promi.recyclerview.miniProfile.MiniProfile
import com.promi.recyclerview.miniProfile.MiniProfileRecyclerViewAdapter
import com.promi.ui.myInformation.MyInformationViewModel


//친구 생성과 관련된 그룹
class CreateGroupFragment : Fragment(){
    private var _binding: FragmentCreateGroupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //recycler view layout
    private lateinit var recyclerViewFriend : RecyclerView

    //recycler view adapter
    private lateinit var recyclerViewFriendAdapter : FriendRecyclerViewAdapter

    //선택된 친구들에 대한 리사이클러뷰
    private lateinit var recyclerViewSelectedFriend: RecyclerView

    //선택된 친구 리사이클러뷰 어댑터
    private lateinit var recyclerViewSelectedFriendAdapter: MiniProfileRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateGroupBinding.inflate(inflater, container, false)

        recyclerViewFriend = binding.recyclerviewSearchUser //친구 목록
        var friendArray = initFriendDTOArray() //더미데이터 생성

        //선택된 친구 목록
        recyclerViewSelectedFriend = binding.recyclerviewSelectedUser //선택된 친구들
        var selectedFriendArray = initSelectedFriendDTOArray() //더미데이터 생성

        //어댑터 부착
        setAdapter(friendArray,selectedFriendArray)

        return binding.root
    }

    private fun initFriendDTOArray(): Array<Friend> {
        return arrayOf(
            Friend("친구1",123),
            Friend("친구2",123),
            Friend("친구3",123),
            Friend("친구4",123),
            Friend("친구5",123),
            Friend("친구6",123),
        )
    }

    //선택된 친구들
    private fun initSelectedFriendDTOArray():Array<MiniProfile>{
        return arrayOf(
            MiniProfile("test","테스트1"),
            MiniProfile("test","테스트2"),
            MiniProfile("test","테스트3"),
            MiniProfile("test","테스트4"),
            MiniProfile("test","테스트5")
        )
    }


    //리사이클러뷰에 리사이클러뷰 어댑터 부착
    private fun setAdapter(friends: Array<Friend>,selectedFriends: Array<MiniProfile>){
        recyclerViewFriend.layoutManager = LinearLayoutManager(this.context)
        //친구 목록 어탭더 생성
        //it(fragment의 context)이 null일수도 있음 => 검사 필요
        recyclerViewFriendAdapter = activity?.let { FriendRecyclerViewAdapter(friends, it) }!!
        recyclerViewFriend.adapter = recyclerViewFriendAdapter

        //선택된 친구 목록 어댑터
        recyclerViewSelectedFriendAdapter = activity?.let{ MiniProfileRecyclerViewAdapter(selectedFriends,it)}!!
        recyclerViewSelectedFriend.adapter = recyclerViewSelectedFriendAdapter // 어댑터 부착
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}