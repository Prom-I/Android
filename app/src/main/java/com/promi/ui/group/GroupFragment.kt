package com.promi.ui.group

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.databinding.FragmentGroupBinding
import com.promi.recyclerview.promise.PromiseRecyclerViewAdapter

class GroupFragment : Fragment() {

    private lateinit var binding : FragmentGroupBinding

    private lateinit var promiseRecyclerview : RecyclerView
    private lateinit var promiseRecyclerviewAdapter : PromiseRecyclerViewAdapter

    private lateinit var groupViewModel: GroupViewModel //그룹에 포함된 약속 목록들이 정의되어 있음

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupBinding.inflate(layoutInflater)

        // 그룹이 소유하고 있는 약속에 대한 정보가 포함되어있음
        groupViewModel = ViewModelProvider(requireActivity())[GroupViewModel::class.java]

        //init recyclerview
        promiseRecyclerview = binding.recyclerviewPromise
        setRecyclerViewAdapter()

        //뒤로가기
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        //약속 생성 버튼 클릭시
        binding.btnCreateGroup.setOnClickListener {
            findNavController().navigate(R.id.action_groupFragment_to_createPromiseFragment)
        }

        //promiseRecyclerviewAdapter.setList(groupViewModel.promises)
        //현재 약속 목록 observe
        groupViewModel.promises.observe(viewLifecycleOwner) { promise ->
            promiseRecyclerviewAdapter.updateData(promise)
        }

        setButtonStyle() // 버튼 색 변경

        return binding.root
    }


    private fun setRecyclerViewAdapter(){
        promiseRecyclerview.layoutManager = LinearLayoutManager(context)
        promiseRecyclerviewAdapter = PromiseRecyclerViewAdapter()
        promiseRecyclerview.adapter = promiseRecyclerviewAdapter
    }

    private fun setButtonStyle(){
        val spannable = SpannableString("3초만에 약속잡기") // 원본 문자열
        // 1. 일부 글자 색 변경
        //val greenColorSpan = ForegroundColorSpan(resources.getColor(R.color.mainGreen, null))
        //spannable.setSpan(greenColorSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        // 2. 일부 글자만 굵게 설정
        val boldSpan = StyleSpan(Typeface.BOLD) // BOLD 스타일 적용(글씨 굵게)
        spannable.setSpan(boldSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.btnCreateGroup.text = spannable
    }

}