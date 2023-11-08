package com.promi.ui.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.databinding.FragmentAddFriendsBinding
import com.promi.databinding.FragmentCreateGroupBinding
import com.promi.recyclerview.friend.Friend
import com.promi.recyclerview.friend.FriendRecyclerViewAdapter
import com.promi.recyclerview.friend.FriendViewModel
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


    //친구 데이터의 변화를 뷰 모델에서 관리
    private lateinit var friendViewModel: FriendViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateGroupBinding.inflate(inflater, container, false)

        //친구데이터에 대한 뷰 모델
        friendViewModel = ViewModelProvider(requireActivity()).get(FriendViewModel::class.java)



        // 뷰 모델에서 친구 리스트 관찰
        friendViewModel.friends.observe(viewLifecycleOwner) { newFriends ->
            // 어댑터에 데이터 업데이트
            recyclerViewFriendAdapter.updateData(newFriends)
        }

        //친구 목록 어댑터
        recyclerViewFriend = binding.recyclerviewSearchUser
        recyclerViewFriend.layoutManager = LinearLayoutManager(context) //레이아웃 매니저 설정
        //친구 어댑터 초기화 및 부착
        recyclerViewFriendAdapter = FriendRecyclerViewAdapter(friendViewModel.friends.value ?: emptyList())
        recyclerViewFriend.adapter = recyclerViewFriendAdapter



//        var friendArray = initFriendDTOArray() //더미데이터 생성
//
//        //선택된 친구 목록
//        recyclerViewSelectedFriend = binding.recyclerviewSelectedUser //선택된 친구들
//        var selectedFriendArray = initSelectedFriendDTOArray() //더미데이터 생성

        //어댑터 부착
        //setAdapter(friendArray,selectedFriendArray)

        //뒤로가기 버튼
        binding.btnClear.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun initFriendDTOArray(): Array<Friend> {
        return arrayOf(

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
//    private fun setAdapter(friends: Array<Friend>, selectedFriends: Array<MiniProfile>) {
//        recyclerViewFriend.layoutManager = LinearLayoutManager(context)
//
//        // 친구 목록 어댑터 생성
//        recyclerViewFriendAdapter = FriendRecyclerViewAdapter(friends)
//        recyclerViewFriend.adapter = recyclerViewFriendAdapter
//
//        // 선택된 친구 목록 어댑터
//        recyclerViewSelectedFriendAdapter = MiniProfileRecyclerViewAdapter(selectedFriends, requireContext())
//        recyclerViewSelectedFriend.adapter = recyclerViewSelectedFriendAdapter
//    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}