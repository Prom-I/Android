package com.promi.view.group

import android.content.Context
import android.graphics.Canvas
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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.MainActivity
import com.promi.R
import com.promi.databinding.FragmentGroupBinding
import com.promi.util.HorizontalSpaceItemDecoration
import com.promi.util.PromiseItemSpaceItemDecoration
import com.promi.util.VerticalSpaceItemDecoration
import com.promi.view.group.adapter.GroupMemberRecyclerViewAdapter
import com.promi.view.group.adapter.GroupRecyclerViewAdapter
import com.promi.view.promise.adapter.PromiseItemClickListener
import com.promi.view.promise.adapter.PromiseRecyclerViewAdapter
import com.promi.viewmodel.group.GroupViewModel

// 특정 그룹에 대한 상세 페이지
// 그 그룹에 어떤 약속이 포함 되어 있는지, 그룹에 소속된 멤버는 누구 인지 등
// Data Source : GroupViewModel
// RecyclerView : PromiseRecyclerView (약속 목록 보여주기), GroupMemeberRecyclerView (소속 멤버 보여주기)
class GroupFragment : Fragment(),PromiseItemClickListener {

    // 약속 타입 정의
    private val PROGRESS = 0 // 진행중인 약속
    private val DONE = 1 // 끝난 약속

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

        setItemTouchHelper()


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

    override fun onPromiseItemClicked(positio: Int,type : Int) {

        if (type == PROGRESS) {
            findNavController().navigate(R.id.action_groupFragment_to_viewPromiseTimeFragment)
        } else if (type == DONE){
            findNavController().navigate(R.id.action_groupFragment_to_promiseDetailFragment)
        }

    }

    // init Group RecyclerView
    private fun setRecyclerViewAdapter(){
        promiseRecyclerview.layoutManager = LinearLayoutManager(context)
        promiseRecyclerviewAdapter = PromiseRecyclerViewAdapter(this)
        promiseRecyclerview.adapter = promiseRecyclerviewAdapter
        var verticalSpaceItemDecoration = VerticalSpaceItemDecoration(dipToPx(16f,requireContext()))
        var horizontalSpaceItemDecoration = PromiseItemSpaceItemDecoration(dipToPx(24f,requireContext()))
        verticalSpaceItemDecoration.setOption(true)
        promiseRecyclerview.addItemDecoration(verticalSpaceItemDecoration)
        promiseRecyclerview.addItemDecoration(horizontalSpaceItemDecoration)

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

    // 리사이클러뷰에 적용할 스와이프 이벤트 정의
    private fun setItemTouchHelper(){
        ItemTouchHelper(object : ItemTouchHelper.Callback() {

            // 스와이프 가능 범위 지정 => 이 이상으로 스와이프 되지 않음
            private val limitScrollX = dipToPx(64f, context!!)
            private var currentScrollX = 0
            private var currentScrollXWhenInActive = 0
            private var initXWhenInActive = 0f
            private var firstInActive = false

            // 스와이프 기능 활성화, 드래그는 비활성화
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                // View Holder
                // 삭제 버튼이 표시된 상태에서는 오른쪽으로 스와이프 허용 => 다시 되돌릴 수 있도록
                val swipeFlags = if (viewHolder.itemView.scrollX > 0) {
                    ItemTouchHelper.RIGHT
                } else {
                    // 기본 상태에서는 왼쪽으로 스와이프 허용
                    ItemTouchHelper.LEFT
                }
                return makeMovementFlags(0, swipeFlags)
            }

            // 항목이 이동될 경우에 대한 동작 (위,아래 이동)
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            // 항목이 스와이프(좌,우) 될 경우에 대한 동작
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }

            // 항목이 '스와이프 되었다'고 판단되는 임계값(threshold) 정의
            // 예를 들어 0.5라면, 사용자가 절반의 너비만큼 스와이프해야 항목이 완전히 스와이프 된 것으로 간주됨
            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return Integer.MAX_VALUE.toFloat()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float, // 사용자가 얼마나 스와이프 했는지(거리)
                dY: Float,
                actionState: Int, // 현재 엑션 상태를 정의 => 스와이프 중인지, 드래그 중인지
                isCurrentlyActive: Boolean // 활성화 상태인지(스크롤 또는 드래그 중인지)파악
            ) {

                // 현재 엑션이 스와이프인지 확인(드래그인 경우에는 동작하지 않음)
                // 스와이프 상태인 경우에 한해 아래 동작 수행
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    // 스와이프 상태를 어댑터에 업데이트
                    if (isCurrentlyActive) {
                        //groupRecyclerViewAdapter.updateSwipedPosition(viewHolder.adapterPosition)
                    }
                    if(dX == 0f){
                        currentScrollX = viewHolder.itemView.scrollX // 현재 뷰의 스크롤 위치 저장
                        firstInActive = true // 스와이프 활성상태 추적을 위해
                    }

                    // 스와이프 중인 경우(손가락이 화면에 닿아있는 상태)
                    if(isCurrentlyActive){
                        var scrollOffset = currentScrollX + (-dX).toInt() // 항목이 얼마나 스크롤 되어야 할지 계산

                        // 항목이 지정된 범위(limitScrollX : 스와이프 메뉴의 크기)를 넘어서서 스와이프 되지 않도록 조정
                        if (scrollOffset > limitScrollX) {
                            scrollOffset = limitScrollX
                        } else if (scrollOffset < 0){
                            scrollOffset = 0
                        }

                        // 스크롤 되어야하는 만큼만 스크롤해서 위치 조정
                        viewHolder.itemView.scrollTo(scrollOffset,0)
                    }
                    else {
                        // swipe with auto animation
                        if (firstInActive){
                            firstInActive = false // 비활성화
                            // 사용자가 스와이프를 멈추고 손을 뗀 순간의 뷰의 스크롤 위치와 스와이프 양(dX)을 저장
                            currentScrollXWhenInActive = viewHolder.itemView.scrollX // 사용자가 스와이프를 뗀 순간의 스크롤 위치
                            initXWhenInActive = dX // 사용자가 손을 덴 순간의 스와이프 양을 저장 => 얼마나 스와이프 했는지 판단하기 위함
                        }

                        // 사용자가 손을 뗀 이후, 뷰가 스와이프 제한(limitScrollX)에 도달하지 않았다면 초기 상태로 서서히 되돌아감
                        if (viewHolder.itemView.scrollX < limitScrollX){
                            // 사용자가 손을 뗀 순간부터 항목이 어떻게 스크롤되어야 할지를 결정함.
                            // 스와이프 제한에 도달하지 않았다면, 계산된 비율에 따라 항목을 천천히 원래 위치로 되돌림
                            viewHolder.itemView.scrollTo((currentScrollXWhenInActive * dX/initXWhenInActive).toInt(),0)
                        }
                    }
                }
            }

            // 사용자의 스와이프 동작이 완료된 이후, 사용자의 상호작용이 끝난 이후 호출됨
            // RecyclerView 항목의 스크롤 위치를 조정하여, 뷰가 정상적인 범위 내에서 표시되도록 보장
            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)

                // 항목 뷰의 스크롤 위치가 스와이프 제한을 초과한 경우
                if(viewHolder.itemView.scrollX > limitScrollX){
                    // 뷰를 스와이프 제한 위치로 스크롤 => 스와이프 메뉴(삭제 버튼)영역을 넘어서지 않도록
                    viewHolder.itemView.scrollTo(limitScrollX,0)
                }
                // 항목 뷰의 스크롤 위치가 음수인 경우 (왼쪽으로 너무 많이 스크롤된 경우)
                else if(viewHolder.itemView.scrollX < 0){
                    viewHolder.itemView.scrollTo(0,0)
                }
            }

        }).apply {
            attachToRecyclerView(promiseRecyclerview)
        }
    }

    // DP를 PX값으로 변환
    private fun dipToPx(dipValue: Float, context: Context): Int{
        return (dipValue * context.resources.displayMetrics.density).toInt()
    }

}