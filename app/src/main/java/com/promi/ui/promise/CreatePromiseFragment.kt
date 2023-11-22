package com.promi.ui.promise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import com.promi.R
import com.promi.databinding.FragmentCreatePromiseBinding

class CreatePromiseFragment : Fragment() {

    private lateinit var binding : FragmentCreatePromiseBinding

    private lateinit var frameContainer : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePromiseBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        frameContainer = binding.frameLayoutCreatePromise

        //Fragment의 초기 설정 화면은 달력 화면
        childFragmentManager.beginTransaction().apply {
            add(R.id.frameLayout_create_promise, SelectPromiseDateFragment())
            commit()
        }

        //초기엔 달력(기간설정화면)
        //확인 누를 경우 화면 전환 : 달력 => 약속 소요시간 및 상세 기능 설정
        binding.tvBtnConfirm.setOnClickListener {
            childFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout_create_promise, PromiseSettingFragment())
                addToBackStack(null) // 스택에 추가 (뒤로가기 구현을 위해서)
                commit()
            }
        }

        //뒤로가기
        binding.btnBack.setOnClickListener {
            if (childFragmentManager.backStackEntryCount > 0) {
                // 백 스택에 항목이 있는 경우, 즉 PromiseSettingFragment가 표시되고 있는 경우
                childFragmentManager.popBackStack()
            } else {
                // 백 스택이 비어있는 경우, 즉 SelectPromiseDateFragment가 표시되고 있는 경우
                findNavController().popBackStack()
            }
        }


        return binding.root
    }
}