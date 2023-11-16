package com.promi.ui.friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.promi.R
import com.promi.databinding.FragmentMyFriendListBinding

class MyFriendListFragment : Fragment() {

    private lateinit var binding : FragmentMyFriendListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyFriendListBinding.inflate(layoutInflater)

        // 뒤로 가기
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }



        return binding.root
    }

}