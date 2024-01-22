package com.promi.view.group

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.MainActivity
import com.promi.R
import com.promi.databinding.FragmentGroupBinding
import com.promi.view.group.adapter.GroupMemberRecyclerViewAdapter
import com.promi.view.promise.adapter.PromiseItemClickListener
import com.promi.view.promise.adapter.PromiseRecyclerViewAdapter
import com.promi.viewmodel.group.GroupViewModel

// 특정 그룹에 대한 상세 페이지
// 그 그룹에 어떤 약속이 포함 되어 있는지, 그룹에 소속된 멤버는 누구 인지 등
// Data Source : GroupViewModel
// RecyclerView : PromiseRecyclerView (약속 목록 보여주기), GroupMemeberRecyclerView (소속 멤버 보여주기)
class GroupFragment : Fragment(),PromiseItemClickListener {

    private lateinit var binding : FragmentGroupBinding

    // 약속 목록
    private lateinit var promiseRecyclerview : RecyclerView
    private lateinit var promiseRecyclerviewAdapter : PromiseRecyclerViewAdapter

    // 그룹 소속 멤버 목록
    private lateinit var groupMemberRecyclerView: RecyclerView
    private lateinit var groupMemberRecyclerViewAdapter: GroupMemberRecyclerViewAdapter

    // Data-Source
    private lateinit var groupViewModel: GroupViewModel //그룹에 포함된 약속 목록들이 정의되어 있음

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupBinding.inflate(layoutInflater)

        (activity as MainActivity).setToolbar(false)

        // 번들로부터 그룹 이름 얻어오기
        val groupName = arguments?.getString("groupName")
        binding.tvGroupName.text = groupName

        // 그룹이 소유하고 있는 약속과 그룹 멤버에 대한 정보가 포함되어있음
        groupViewModel = ViewModelProvider(requireActivity())[GroupViewModel::class.java]

        //init recyclerview
        promiseRecyclerview = binding.recyclerviewPromise

        val drawerLayout = binding.drawerMenu
        groupMemberRecyclerView = drawerLayout.recyclerviewGroupMembers

        setRecyclerViewAdapter()

        //뒤로가기
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        //약속 생성 버튼 클릭시
        binding.btnCreateGroup.setOnClickListener {
            findNavController().navigate(R.id.action_groupFragment_to_selectPromiseDateFragment)
        }

        // promiseRecyclerviewAdapter.setList(groupViewModel.promises)
        // 현재 약속 목록 observe
        groupViewModel.promises.observe(viewLifecycleOwner) { promise ->
            promiseRecyclerviewAdapter.updateData(promise)
        }

        // 그룹 멤버 목록 observe
        groupViewModel.groupMembers.observe(viewLifecycleOwner) { groupMembers ->
            groupMemberRecyclerViewAdapter.updateData(groupMembers)
        }


        setButtonStyle() // 버튼 색 변경

        // 참여자 명단 보기
        val drawer = binding.drawer
        binding.btnGroupOptionMenu.setOnClickListener {
            drawer.openDrawer(Gravity.RIGHT)
        }

        // DrawerListener를 추가하여 드로어의 상태를 감지 => 특정 상황에서만 스와이프 허용
        drawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerOpened(drawerView: View) {
                // 드로어가 열린 상태에서는 스와이프를 통해 닫을 수 있도록 잠금 해제
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.RIGHT)
            }

            override fun onDrawerClosed(drawerView: View) {
                // 드로어가 닫힌 상태에서는 다시 스와이프 잠금
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT)
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // 슬라이드 중에는 특별한 동작 없음
            }

            override fun onDrawerStateChanged(newState: Int) {
                // 드로어 상태가 변경될 때 특별한 동작 없음
            }
        })

        // 뒤로가기 버튼 콜백 설정 => 상위 엑티비티에게 콜백 전달
        val callback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // 드로어가 열려있는지 확인
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    // 드로어가 열려있다면 닫기
                    drawer.closeDrawer(Gravity.RIGHT)
                } else {
                    // 드로어가 닫혀있다면 기존 뒤로가기 동작 수행
                    isEnabled = false
                    requireActivity().onBackPressed()
                    isEnabled = true
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)



        return binding.root
    }

    override fun onPromiseItemClicked(positio: Int) {
        findNavController().navigate(R.id.action_groupFragment_to_promiseDetailFragment)
    }

    // init Group RecyclerView
    private fun setRecyclerViewAdapter(){
        promiseRecyclerview.layoutManager = LinearLayoutManager(context)
        promiseRecyclerviewAdapter = PromiseRecyclerViewAdapter(this)
        promiseRecyclerview.adapter = promiseRecyclerviewAdapter

        groupMemberRecyclerView.layoutManager = LinearLayoutManager(context)
        groupMemberRecyclerViewAdapter = GroupMemberRecyclerViewAdapter()
        groupMemberRecyclerView.adapter = groupMemberRecyclerViewAdapter
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