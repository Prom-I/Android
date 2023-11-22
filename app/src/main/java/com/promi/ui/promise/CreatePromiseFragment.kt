package com.promi.ui.promise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.promi.R
import com.promi.databinding.FragmentCreatePromiseBinding

class CreatePromiseFragment : Fragment() {

    private lateinit var binding : FragmentCreatePromiseBinding

    private lateinit var frameContainer : FrameLayout

    private lateinit var toolBarTitle : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePromiseBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        frameContainer = binding.frameLayoutCreatePromise

        toolBarTitle = binding.tvCreatePromise

        //Fragment의 초기 설정 화면은 달력 화면
        childFragmentManager.beginTransaction().apply {
            add(R.id.frameLayout_create_promise, SelectPromiseDateFragment())
            commit()
        }

        //초기엔 달력(기간설정화면)
        //확인 누를 경우 화면 전환 : 달력 => 약속 소요시간 및 상세 기능 설정
        //달력 화면일때 확인 누르면 약속 상세 설정으로 이동
        //약속 상세 설정에서 확인 누르면, 약속 이름,이미지 설정 화면(PromiseFinalSettingFragment)으로 이동
        binding.tvBtnConfirm.setOnClickListener {
            //스택에 쌓여있는게 없다면 달력 화면에 있는 것이므로, 상세 설정 화면으로 이동
            if(childFragmentManager.backStackEntryCount <= 0){
                childFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout_create_promise, PromiseSettingFragment())
                    addToBackStack(null) // 스택에 추가 (뒤로가기 구현을 위해서)
                    commit()
                }
            } else { // 1 이상이라면, 현재 약속 상세 설정 화면인 것임
                // 약속 최종 설정(이미지, 그룹 사진 설정 화면으로 이동)
                toolBarTitle.text = "약속 설정"
                childFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout_create_promise,PromiseFinalSettingFragment())
                    addToBackStack(null) //스택에 팝
                    commit()
                }
            }


        }

        //뒤로가기
        binding.btnBack.setOnClickListener {
            if (childFragmentManager.backStackEntryCount > 0) {
                // 백 스택에 항목이 있는 경우, 즉 PromiseSettingFragment가 표시되고 있는 경우
                toolBarTitle.text = "약속 생성"
                childFragmentManager.popBackStack()
            } else {
                // 백 스택이 비어있는 경우, 즉 SelectPromiseDateFragment가 표시되고 있는 경우
                //toolBarTitle.text = "약속 생성"
                findNavController().popBackStack()
            }
        }


        return binding.root
    }
}