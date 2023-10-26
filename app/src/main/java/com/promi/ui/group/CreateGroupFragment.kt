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
import com.promi.ui.myInformation.MyInformationViewModel

class CreateGroupFragment : Fragment(){
    private var _binding: FragmentCreateGroupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //recycler view layout
    private lateinit var recyclerViewFriend : RecyclerView

    //recycler view adapter
    private lateinit var recyclerViewFriendAdapter : FriendRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateGroupBinding.inflate(inflater, container, false)

        recyclerViewFriend = binding.recyclerviewSearchUser //친구 목록
        var friendArray = initFriendDTOArray() //더미데이터 생성
        setAdapter(friendArray) //어댑터 붙이기

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

    //리사이클러뷰에 리사이클러뷰 어댑터 부착
    private fun setAdapter(groups: Array<Friend>){
        recyclerViewFriend.layoutManager = LinearLayoutManager(this.context)
        //어탭더 생성
        //it(fragment의 context)이 null일수도 있음 => 검사 필요
        recyclerViewFriendAdapter = activity?.let { FriendRecyclerViewAdapter(groups, it) }!!
        recyclerViewFriend.adapter = recyclerViewFriendAdapter
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}