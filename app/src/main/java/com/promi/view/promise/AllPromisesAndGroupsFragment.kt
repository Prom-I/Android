package com.promi.view.promise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promi.R
import com.promi.databinding.FragmentAllPromisesAndGroupsBinding

class AllPromisesAndGroupsFragment : Fragment() {

    private lateinit var binding : FragmentAllPromisesAndGroupsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllPromisesAndGroupsBinding.inflate(layoutInflater)


        // 디폴트 화면은 약속 목록을 보여주는 화면
        childFragmentManager.beginTransaction().apply {
            add(R.id.frameLayout_all_promises_and_groups, AllPromiseFragment())
            commit()
        }


        // 버튼을 여러번 누를 수 있으므로, 라디오버튼으로 변경 필요 => 혹은 여러번 선택 불가능 하도록 필요

        // 그룹 버튼 누르면 전체 그룹 목록 화면으로 이동
        binding.btnShowGroup.setOnClickListener {
            childFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout_all_promises_and_groups, AllGroupFragment())
                addToBackStack(null) // 스택에 추가 (뒤로가기 구현을 위해서)
                commit()
            }
        }

        // 약속 버튼 누르면 전체 약속 목록 화면으로 변경
        binding.btnShowPromise.setOnClickListener {
            childFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout_all_promises_and_groups, AllPromiseFragment())
                addToBackStack(null) // 스택에 추가 (뒤로가기 구현을 위해서)
                commit()
            }
        }



        return binding.root
    }

}