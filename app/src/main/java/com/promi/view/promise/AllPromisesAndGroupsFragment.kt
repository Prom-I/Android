package com.promi.view.promise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promi.R
import com.promi.databinding.FragmentAllPromisesAndGroupsBinding

class AllPromisesAndGroupsFragment : Fragment() {

    private var _binding: FragmentAllPromisesAndGroupsBinding? = null
    private val binding get() = _binding!!

    // 현재 활성화된 하위 프래그먼트 추적
    private var currentFragment: Fragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllPromisesAndGroupsBinding.inflate(inflater, container, false)

        // 초기 프래그먼트 설정 또는 이전 상태 복원
        if (savedInstanceState == null) {
            showFragment(AllPromiseFragment(), "PromiseFragment")
        }

        // 그룹 버튼 클릭 이벤트
        binding.btnShowGroup.setOnClickListener {
            showFragment(AllGroupFragment(), "GroupFragment")
        }

        // 약속 버튼 클릭 이벤트
        binding.btnShowPromise.setOnClickListener {
            showFragment(AllPromiseFragment(), "PromiseFragment")
        }

        return binding.root
    }

    // 하위 프래그먼트 보여주는 함수
    private fun showFragment(fragment: Fragment, tag: String) {
        childFragmentManager.beginTransaction().apply {
            // 모든 프래그먼트 숨기기
            childFragmentManager.fragments.forEach { hide(it) }

            // 프래그먼트 찾기 또는 추가
            var frag = childFragmentManager.findFragmentByTag(tag)
            if (frag == null) {
                frag = fragment
                add(R.id.frameLayout_all_promises_and_groups, frag, tag)
            } else {
                show(frag)
            }

            commit()
        }
        currentFragment = fragment
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
