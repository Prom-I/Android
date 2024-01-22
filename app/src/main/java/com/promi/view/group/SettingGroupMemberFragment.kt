package com.promi.view.group

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentSettingGroupMemberBinding
import com.promi.view.friend.adapter.FriendRecyclerViewAdapter
import com.promi.view.main.adapter.MiniProfileRecyclerViewAdapter
import com.promi.viewmodel.friend.FriendViewModel


//그룹 생성과 관련된 프래그먼트
//사용자로부터 그룹에 추가할 친구를 선택받고, 그 친구들을 바탕으로 그룹을 만든다.
class SettingGroupMemberFragment : BaseFragment<FragmentSettingGroupMemberBinding> (R.layout.fragment_setting_group_member){
    private lateinit var friendViewModel: FriendViewModel // 친구, 선택된 친구를 관리하기 위한 뷰모델
    private lateinit var friendsAdapter: FriendRecyclerViewAdapter
    private lateinit var selectedFriendsAdapter: MiniProfileRecyclerViewAdapter

    var sizeGroupMember: Int = 0

    override fun initStartView() {
        super.initStartView()

        //친구데이터에 대한 뷰 모델
        friendViewModel = ViewModelProvider(requireActivity()).get(FriendViewModel::class.java)

        // 빈 배열로 어댑터 초기화
        friendsAdapter = FriendRecyclerViewAdapter(emptyList(), friendViewModel)
        selectedFriendsAdapter = MiniProfileRecyclerViewAdapter(emptyList(), friendViewModel,friendsAdapter)

        binding.recyclerviewSearchUser.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = friendsAdapter
        }

        binding.recyclerviewSelectedUser.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = selectedFriendsAdapter
        }
    }

    override fun initDataBinding() {
        super.initDataBinding()

        binding.view = this

        //뷰 모델에서 변화 발생시 리사이클러뷰에 반영
        friendViewModel.friends.observe(viewLifecycleOwner) { friends ->
            friendsAdapter.updateData(friends)
        }

        friendViewModel.selectedFriends.observe(viewLifecycleOwner) { selectedFriends ->
            selectedFriendsAdapter.updateData(selectedFriends)
            //친구 추가 선택시, 스크롤을 가장 왼쪽으로 이동
            binding.recyclerviewSelectedUser.scrollToPosition(selectedFriendsAdapter.itemCount - 1)
            sizeGroupMember = selectedFriends.size

            binding.invalidateAll()
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.btnClear.setOnClickListener {
            findNavController().popBackStack()
        }


        //검색창 변화 이벤트
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            // 텍스트가 변경되기 전에 호출됨
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            // 텍스트가 변경될 때 호출됨
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if (s.toString().isEmpty()) {
                        // 검색어가 없는 경우, 모든 친구 목록을 표시
                        friendViewModel.setFriendsListToAllFriendList()
                    } else {
                        // 사용자가 입력한 검색어로 친구 목록 검색
                        friendViewModel.searchFriend(s.toString())
                    }
                }
            }

            // 텍스트가 변경된 후에 호출됨
            override fun afterTextChanged(s: Editable?) {

            }
        })

        // 확인(그룹 생성 버튼 클릭시)
        // 필요한 로직 => 그룹에 포함된 친구가 한명도 없을 경우에도 그룹 생성 허용?
        binding.btnConfirm.setOnClickListener {
            //그룹 설정 화면으로 이동
            findNavController().navigate(R.id.action_settingGroupMemberFragment_to_settingGroupNameFragment)
        }
    }
}