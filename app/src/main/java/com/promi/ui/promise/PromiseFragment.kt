package com.promi.ui.promise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpractice.item.Group
import com.promi.R
import com.promi.databinding.FragmentPromiseBinding
import com.promi.ui.group.GroupRecyclerViewAdapterWithItemHelper
import com.promi.ui.group.ItemTouchHelperCallback

class PromiseFragment : Fragment() {

    private var _binding: FragmentPromiseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //recycler view layout
    lateinit var recyclerViewGroup : RecyclerView

    //recycler view adapter
    lateinit var recyclerViewAdapterWithItemHelper: GroupRecyclerViewAdapterWithItemHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val promiseViewModel =
            ViewModelProvider(this).get(PromiseViewModel::class.java)

        _binding = FragmentPromiseBinding.inflate(inflater, container, false)

        recyclerViewGroup = binding.recyclerviewGroup //그룹 리사이클러뷰에 대한 참조
        var groups = initGroupDTOArray() //더미데이터 생성
        setAdapter(groups) //어댑터 붙이기

        return binding.root
    }

    fun initGroupDTOArray(): MutableList<Group> {
        return mutableListOf(
            (Group("투밋투미",9)),
            Group("공학경진대회",4),
            Group("캡스톤 디자인",4),
            Group("안드로이드 스터디",3),
            Group("안드로이드 스터디",3),
            Group("설계패턴 스터디",3),
            Group("운영체제 스터디",3),
            Group("멋쟁이 사자처럼",40),
            Group("UMC",45),
            Group("UMC",45),
            Group("UMC",45),
        )

    }

    //리사이클러뷰에 리사이클러뷰 어댑터 부착
    fun setAdapter(groups: MutableList<Group>){
        recyclerViewGroup.layoutManager = LinearLayoutManager(this.context)
        //어탭더 생성 => it(fragment의 context가 null이 아님을 알려줘야함)
        recyclerViewAdapterWithItemHelper = activity?.let{GroupRecyclerViewAdapterWithItemHelper(groups, it)}!!
        recyclerViewGroup.adapter = recyclerViewAdapterWithItemHelper

        // 리스너를 구현한 Adapter 클래스를 Callback 클래스의 생성자로 지정
        val itemTouchHelperCallback = ItemTouchHelperCallback(recyclerViewAdapterWithItemHelper)

        // ItemTouchHelper의 생성자로 ItemTouchHelper.Callback 객체 세팅
        val helper = ItemTouchHelper(itemTouchHelperCallback)

        // RecyclerView에 ItemTouchHelper 연결
        helper.attachToRecyclerView(recyclerViewGroup)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}