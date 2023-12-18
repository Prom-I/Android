package com.promi.view.group

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
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

        // 보유중인 그룹에 대한 리사이클러뷰 코드 필요


        setButtonStyle() // 버튼 색 변경


        // 다른 코드 작성

        return binding.root
    }

    private fun setButtonStyle(){
        // '약속' 글자만 색 변경
        val spannable = SpannableString("3초만에 약속잡기")
        val greenColorSpan = ForegroundColorSpan(resources.getColor(R.color.mainGreen, null))
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannable.setSpan(greenColorSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(boldSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.btnCreateGroup.text = spannable
    }

}