package com.promi.view.group

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentSettingGroupNameBinding

class SettingGroupNameFragment : BaseFragment<FragmentSettingGroupNameBinding> (R.layout.fragment_setting_group_name) {
    var lenGroupName: Int = 0
    private lateinit var customButton: Button

    override fun initStartView() {
        super.initStartView()

        (activity as MainActivity).setToolbar(true, "그룹 설정")
        addToToolbar()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        // 사용자가 입력한 글자 수만큼 표시
        // etv_group_name의 글자수 변경시 tv_len_group_name 변경 size/30
        binding.etvGroupName.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                lenGroupName = p0?.length!!
                binding.tvLenGroupName.text = "${lenGroupName}/30"

                customButton.apply{
                    if (lenGroupName>0){
                        isEnabled = true
                        setTextColor(ContextCompat.getColor(context, R.color.mainBlack))
                    } else {
                        isEnabled = false
                        setTextColor(ContextCompat.getColor(context, R.color.mainGray))
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun addToToolbar() {
        // 툴바 찾기
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        val layout: LinearLayout = toolbar.findViewById(R.id.layout_toolbar_btn)

        customButton = Button(requireContext())

        // 버튼 생성
        customButton.apply{
            text = "확인"
            isEnabled = false
            textSize = 17f
            setTextColor(ContextCompat.getColor(context, R.color.mainGray))
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
        }

        // 툴바에 버튼 추가
        layout.removeAllViews()
        layout.addView(customButton)

        // 버튼에 클릭 이벤트 추가
        customButton.setOnClickListener {
            navController.navigate(R.id.action_settingGroupNameFragment_to_navigation_all_promises_and_groups)
        }
    }
}