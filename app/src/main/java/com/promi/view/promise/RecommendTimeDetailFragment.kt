package com.promi.view.promise

import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentRecommendTimeDetailBinding

// 추천 날짜에 연관된 시간들 리스트 형식으로 제공
// 약속에 참여 가능한 멤버들, 불가능한 멤버들 보여주기

class RecommendTimeDetailFragment : BaseFragment<FragmentRecommendTimeDetailBinding>(R.layout.fragment_recommend_time_detail) {

    override fun initStartView() {
        (activity as MainActivity).setToolbar(true, "1차 회의")
        addToToolbar()

    }

    // * 데이터 바인딩 설정.
    override fun initDataBinding() {

    }

    // * 바인딩 이후에 할 일을 여기에 구현. * 그 외에 설정할 것이 있으면 이곳에서 설정. * 클릭 리스너도 이곳에서 설정.
    override fun initAfterBinding() {
        initRecyclerView()
    }

    private fun initRecyclerView(){

    }


    private fun addToToolbar() {
        // 툴바 찾기
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        val layout: LinearLayout = toolbar.findViewById(R.id.layout_toolbar_btn)

        val customButton = Button(requireContext())

        // 버튼 생성
        customButton.apply{
            text = "확인"
            textSize = 17f
            setTextColor(ContextCompat.getColor(context, R.color.mainBlack))
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
        }

        // 툴바에 버튼 추가 -> 이미지 버튼으로 변경 필요
        layout.removeAllViews()
        layout.addView(customButton)

        // 버튼에 클릭 이벤트 추가
        customButton.setOnClickListener {
            //
        }
    }

}