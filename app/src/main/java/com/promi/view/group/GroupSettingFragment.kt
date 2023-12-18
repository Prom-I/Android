package com.promi.view.group

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.promi.databinding.FragmentGroupSettingBinding

class GroupSettingFragment : Fragment() {

    private lateinit var binding : FragmentGroupSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupSettingBinding.inflate(inflater,container,false)


        //사용자가 입력한 글자 수만큼 표시
        //etv_group_name의 글자수 변경시 tv_len_group_name 변경 size/30
        binding.etvGroupName.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val length = p0?.length
                binding.tvLenGroupName.text = "${length}/30"
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })


        //뒤로 가기
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

}