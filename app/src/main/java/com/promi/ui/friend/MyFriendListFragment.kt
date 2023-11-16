package com.promi.ui.friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

        val MyInformationViewModel =
            ViewModelProvider(this)[MyInformationViewModel::class.java]

        // 뒤로 가기
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // 리사이클러뷰 어댑터 설정



        return binding.root
    }

}