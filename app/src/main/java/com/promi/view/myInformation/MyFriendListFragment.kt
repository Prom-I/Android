package com.promi.view.myInformation

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentMyFriendListBinding
import com.promi.view.friend.adapter.MyFriendListRecyclerViewAdapter
import com.promi.viewmodel.myinformation.MyInformationViewModel

class MyFriendListFragment : BaseFragment<FragmentMyFriendListBinding>(R.layout.fragment_my_friend_list) {
    private lateinit var myFriendRecyclerViewAdapter : MyFriendListRecyclerViewAdapter
    private lateinit var myFriendRecyclerView : RecyclerView

    override fun initAfterBinding() {
        super.initAfterBinding()

        myFriendRecyclerView = binding.recyclerviewMyFriendList

        val myInformationViewModel =
            ViewModelProvider(this)[MyInformationViewModel::class.java]

        // 친구 리스트 리사이클러뷰 설정
        myFriendRecyclerView.layoutManager = LinearLayoutManager(this.context)
        myFriendRecyclerViewAdapter = MyFriendListRecyclerViewAdapter() // 어댑터 생성
        myFriendRecyclerView.adapter = myFriendRecyclerViewAdapter
        // LiveData Observer 설정 => 변화 발생시 반영
        myInformationViewModel.myFriends.observe(viewLifecycleOwner, Observer { items ->
            myFriendRecyclerViewAdapter.setItemList(items)
        })

        // 친구 검색 이벤트
        binding.etSearch.addTextChangedListener(object :TextWatcher{
            // 텍스트가 변경되기 전에 호출됨
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            // 텍스트가 변경될 때 호출됨
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if (s.toString().isEmpty()) {
                        // 검색어가 없는 경우, 모든 친구 목록을 표시
                        myInformationViewModel.listInit()
                    } else {
                        // 사용자가 입력한 검색어로 친구 목록 검색
                        myInformationViewModel.searchFriend(s.toString())
                    }
                }
            }

            // 텍스트가 변경된 후에 호출됨
            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

}