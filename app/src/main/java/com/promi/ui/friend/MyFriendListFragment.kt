package com.promi.ui.friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.databinding.FragmentMyFriendListBinding
import com.promi.recyclerview.friend.MyFriendListRecyclerViewAdapter
import com.promi.ui.myInformation.MyInformationViewModel

class MyFriendListFragment : Fragment() {

    private lateinit var binding : FragmentMyFriendListBinding

    private lateinit var myFriendRecyclerViewAdapter : MyFriendListRecyclerViewAdapter
    private lateinit var myFriendRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyFriendListBinding.inflate(layoutInflater)

        myFriendRecyclerView = binding.recyclerviewMyFriendList

        val myInformationViewModel =
            ViewModelProvider(this)[MyInformationViewModel::class.java]

        // 뒤로 가기
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        // 친구 리스트 리사이클러뷰 설정
        myFriendRecyclerView.layoutManager = LinearLayoutManager(this.context)
        myFriendRecyclerViewAdapter = MyFriendListRecyclerViewAdapter() // 어댑터 생성
        myFriendRecyclerView.adapter = myFriendRecyclerViewAdapter
        // LiveData Observer 설정 => 변화 발생시 반영
        myInformationViewModel.myFriends.observe(viewLifecycleOwner, Observer { items ->
            myFriendRecyclerViewAdapter.setItemList(items)
        })




        return binding.root
    }

}