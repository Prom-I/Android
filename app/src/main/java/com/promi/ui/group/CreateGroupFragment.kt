package com.promi.ui.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.promi.databinding.FragmentCreateGroupBinding
import com.promi.recyclerview.friend.FriendRecyclerViewAdapter
import com.promi.recyclerview.miniProfile.MiniProfileRecyclerViewAdapter


//친구 생성과 관련된 그룹
class CreateGroupFragment : Fragment(){
    private var _binding: FragmentCreateGroupBinding? = null
    private val binding get() = _binding!!

    private lateinit var friendViewModel: FriendViewModel // 친구, 선택된 친구를 관리하기 위한 뷰모델

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateGroupBinding.inflate(inflater, container, false)
        //친구데이터에 대한 뷰 모델
        friendViewModel = ViewModelProvider(requireActivity()).get(FriendViewModel::class.java)

        // 빈 배열로 어댑터 초기화
        val friendsAdapter = FriendRecyclerViewAdapter(emptyList(), friendViewModel)
        val selectedFriendsAdapter = MiniProfileRecyclerViewAdapter(emptyList(), friendViewModel)

        binding.recyclerviewSearchUser.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = friendsAdapter
        }

        binding.recyclerviewSelectedUser.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = selectedFriendsAdapter
        }


        //뷰 모델에서 변화 발생시 리사이클러뷰에 반영
        friendViewModel.friends.observe(viewLifecycleOwner) { friends ->
            friendsAdapter.updateData(friends)
        }

        friendViewModel.selectedFriends.observe(viewLifecycleOwner) { selectedFriends ->
            selectedFriendsAdapter.updateData(selectedFriends)
            //친구 추가 선택시, 스크롤을 가장 왼쪽으로 이동
            binding.recyclerviewSelectedUser.scrollToPosition(selectedFriendsAdapter.itemCount - 1)
        }

        binding.btnClear.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}