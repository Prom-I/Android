package com.promi.view.promise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promi.databinding.FragmentSelectPromiseDateBinding


// 사용자에게 달력을 보여주고, 사용자로부터 원하는 기간의 날짜를 선택받는 로직 작성 필요
class SelectPromiseDateFragment : Fragment() {

    private lateinit var binding: FragmentSelectPromiseDateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectPromiseDateBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment


        return binding.root
    }

}