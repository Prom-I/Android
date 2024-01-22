package com.promi.view.promise

import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentSettingPromiseTimeBinding

class SettingPromiseTimeFragment : BaseFragment<FragmentSettingPromiseTimeBinding>(R.layout.fragment_setting_promise_time) {

    override fun initStartView() {
        super.initStartView()

        (activity as MainActivity).setToolbar(true, "그룹 이름")
        addToToolbar()
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

        // 툴바에 버튼 추가
        layout.removeAllViews()
        layout.addView(customButton)

        // 버튼에 클릭 이벤트 추가
        customButton.setOnClickListener {
            navController.navigate(R.id.action_settingPromiseTimeFragment_to_settingPromiseNameFragment)
        }
    }
}