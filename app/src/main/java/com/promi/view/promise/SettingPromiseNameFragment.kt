package com.promi.view.promise

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentSettingPromiseNameBinding

class SettingPromiseNameFragment : BaseFragment<FragmentSettingPromiseNameBinding>(R.layout.fragment_setting_promise_name) {
    private var lenPromiseName: Int = 0
    private lateinit var customButton: Button

    override fun initStartView() {
        super.initStartView()

        (activity as MainActivity).setToolbar(true, "그룹 이름")
        addToToolbar()
    }
    override fun initAfterBinding() {
        super.initAfterBinding()

        //tv_len_promise_name
        binding.etvPromiseName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                lenPromiseName = p0?.length!!
                binding.tvLenPromiseName.text = "${lenPromiseName}/30"

                customButton.apply{
                    if (lenPromiseName>0){
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
            navController.navigate(R.id.action_settingPromiseNameFragment_to_groupFragment)
        }
    }
}