package com.promi.view.promise

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.promi.R
import com.promi.databinding.FragmentAllPromisesAndGroupsBinding

class AllPromisesAndGroupsFragment : Fragment() {

    private var _binding: FragmentAllPromisesAndGroupsBinding? = null
    private val binding get() = _binding!!

    private lateinit var btnShowPromises : TextView
    private lateinit var btnShowGroups : TextView

    // 현재 선택된 탭의 상태 추적
    private var selectedTab: TextView? = null

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

        btnShowGroups = binding.btnShowGroup
        btnShowPromises = binding.btnShowPromise



        // 약속 버튼 클릭 이벤트
        btnShowPromises.setOnClickListener {
            if (selectedTab != btnShowPromises) {
                selectTab(btnShowPromises)
                showFragment(AllPromiseFragment(), "PromiseFragment")
            }
        }

        // 그룹 버튼 클릭 이벤트
        btnShowGroups.setOnClickListener {
            if (selectedTab != btnShowGroups) {
                selectTab(btnShowGroups)
                showFragment(AllGroupFragment(), "GroupFragment")
            }
        }

        selectTab(btnShowPromises) // 초기 상태는 약속 버튼

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


    // 상단 탭 선택 함수
    private fun selectTab(selectedTextView: TextView) {
        // 선택되지 않은 탭의 스타일 초기화(선택 안된 상태로 변경)
        val nonSelectedTextView = if (selectedTextView == btnShowPromises) btnShowGroups else btnShowPromises
        nonSelectedTextView.apply {
            setBackgroundResource(R.drawable.shape_radius10)
            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.mainLightGray))
            setTypeface(null, Typeface.NORMAL)
            setTextColor(ContextCompat.getColor(context, R.color.black))
        }

        // 선택된 탭의 스타일 변경
        selectedTextView.apply {
            setBackgroundResource(R.drawable.shape_radius10)
            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
            setTypeface(null, Typeface.BOLD)
            setTextColor(ContextCompat.getColor(context, R.color.black))
        }

        // 선택된 탭을 현재 상태로 업데이트
        selectedTab = selectedTextView
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
