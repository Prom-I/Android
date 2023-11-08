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
import com.promi.databinding.FragmentCreateGroupBinding
import com.promi.recyclerview.friend.Friend
import com.promi.recyclerview.friend.FriendRecyclerViewAdapter
import com.promi.recyclerview.miniProfile.MiniProfile
import com.promi.recyclerview.miniProfile.MiniProfileRecyclerViewAdapter


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

        //어뎁터 생성
        recyclerViewFriendAdapter = FriendRecyclerViewAdapter(emptyList())
        recyclerViewSelectedFriendAdapter = MiniProfileRecyclerViewAdapter(emptyList())

        setupRecyclerView() // 친구 리스트와 선택된 친구 리스트에 대한 리사이클러뷰 부착
        setupObservers() //뷰 모델의 친구 리스트와 선택된 친구 리스트에 대한 옵서버 설정

        //뒤로가기 버튼
        binding.btnClear.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }


    private fun setupObservers() {
        // 뷰 모델에서 친구 리스트 관찰
        friendViewModel.friends.observe(viewLifecycleOwner) { newFriends ->
            recyclerViewFriendAdapter.updateData(newFriends)
        }

        // 뷰 모델에서 선택된 친구 리스트 관찰
        friendViewModel.selectedFriends.observe(viewLifecycleOwner) { newSelectedFriends ->
            recyclerViewSelectedFriendAdapter.updateData(newSelectedFriends)
        }
    }

    private fun setupRecyclerView() {
        // 친구 목록 RecyclerView 설정
        recyclerViewFriend = binding.recyclerviewSearchUser.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = FriendRecyclerViewAdapter(friendViewModel.friends.value ?: emptyList())
        }

        // 선택된 친구 목록 RecyclerView 설정
        recyclerViewSelectedFriend = binding.recyclerviewSelectedUser.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = MiniProfileRecyclerViewAdapter(friendViewModel.selectedFriends.value ?: emptyList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}