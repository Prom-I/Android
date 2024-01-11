package com.promi.view.group

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentSelectGroupBinding
import com.promi.view.group.adapter.SelectGroupRecyclerViewAdapter
import com.promi.viewmodel.group.GroupViewModel

// 약속을 생성할 프레그먼트를 선택
class SelectGroupFragment : BaseFragment<FragmentSelectGroupBinding>(R.layout.fragment_select_group) {

    private lateinit var selectGroupRecyclerViewAdapter: SelectGroupRecyclerViewAdapter
    private lateinit var selectGroupRecyclerView : RecyclerView
    private lateinit var groupViewModel : GroupViewModel

    // * 레이아웃을 띄운 직후 호출. * 뷰나 액티비티의 속성 등을 초기화. * ex) 리사이클러뷰, 툴바, 드로어뷰..
    override fun initStartView() {
        (activity as MainActivity).setToolbar(false)

        // 뷰 모델 가져오기
        groupViewModel = ViewModelProvider(requireActivity())[GroupViewModel::class.java]
        Log.d("SelectGroupFragment Error #1","${groupViewModel.groups.value}")

        // recyclerview init
        initRecyclerView()

        // observe data
        groupViewModel.groups.observe(viewLifecycleOwner) { group ->

            selectGroupRecyclerViewAdapter.updateData(group)

        }

    }
    // * 데이터 바인딩 설정.
    override fun initDataBinding() {
        // 뷰 모델로부터 데이터 받아오기?
        // 서버로부터 그룹 정보 fetch 해오기?
    }

    // * 바인딩 이후에 할 일을 여기에 구현. * 그 외에 설정할 것이 있으면 이곳에서 설정. * 클릭 리스너도 이곳에서 설정.
    override fun initAfterBinding() {

        // 뒤로가기
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // 확인 -> 상세 날짜 선택
        binding.btnConfirm.setOnClickListener {
            findNavController().navigate(R.id.action_selectGroupFragment_to_selectPromiseDateFragment)
        }

    }
    private fun initRecyclerView(){
        selectGroupRecyclerView = binding.recyclerviewSelectGroup

        selectGroupRecyclerViewAdapter = SelectGroupRecyclerViewAdapter(groupViewModel)
        selectGroupRecyclerView.adapter = selectGroupRecyclerViewAdapter
        selectGroupRecyclerView.layoutManager = LinearLayoutManager(context)
    }

}

