package com.promi.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.promi.R
import com.promi.databinding.FragmentGroupBinding

class GroupFragment : Fragment() {

    private lateinit var binding : FragmentGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupBinding.inflate(layoutInflater)

        //뒤로가기
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        //약속 생성 버튼 클릭시
        binding.btnCreateGroup.setOnClickListener {
            findNavController().navigate(R.id.action_groupFragment_to_createPromiseFragment)
        }

        return binding.root
    }

}