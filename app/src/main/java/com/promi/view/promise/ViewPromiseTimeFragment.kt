package com.promi.view.promise

import android.graphics.Canvas
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentViewPromiseTimeBinding
import com.promi.util.HorizontalSpaceItemDecoration
import com.promi.util.VerticalSpaceItemDecoration
import com.promi.view.promise.adapter.DayTextViewRecyclerViewAdapter
import com.promi.view.promise.adapter.RecommendTimeItemClickListener
import com.promi.view.promise.adapter.RecommendTimeRecyclerViewAdapter
import com.promi.viewmodel.promise.ViewPromiseTimeViewModel

class ViewPromiseTimeFragment :
    BaseFragment<FragmentViewPromiseTimeBinding>(R.layout.fragment_view_promise_time),
    RecommendTimeItemClickListener {

    private val START_TIME = 9
    private val END_TIME = 15  
    private val COLUMN = 7 // 동적으로 설정할 행의 개수
    private val ROW = END_TIME-START_TIME // 동적으로 설정할 열의 개수
    private val CELL_HIEGHT = 24
    private var CELL_HIEGHT_DP = 0
    private lateinit var cellMatrix: Array<Array<View>>

    var isEditMode: Boolean = false
  
    // 날짜 텍스트뷰 리사이클러뷰
    private lateinit var dayTextViewRecyclerView : RecyclerView
    private lateinit var dayTextViewRecyclerViewAdapter : DayTextViewRecyclerViewAdapter

    // 추천 약속 시간
    private lateinit var recommendTimeRecyclerView : RecyclerView
    private lateinit var recommendTimeRecyclerViewAdapter: RecommendTimeRecyclerViewAdapter

    private lateinit var viewPromiseTimeViewModel : ViewPromiseTimeViewModel

    private var timeItemHeight : Double = 0.0 // 아이템 셀 하나의 세로 크기
  
    // 화면 이동 로직(추천 시간 클릭시)
    override fun onRecommendTimeItemClicked(position: Int) {
        Log.d("onRecommendTimeItemClicked : ","$position")
        navController.navigate(R.id.action_viewPromiseTimeFragment_to_recommendTimeDetailFragment)
    }

    override fun initStartView() {
        super.initStartView()

        (activity as MainActivity).setToolbar(true, "약속 이름")
        addToToolbar()

        CELL_HIEGHT_DP = pxToDp(CELL_HIEGHT)
        setTimeTable()
        setShowPromiseTable()
        setSelectPromiseTable()
    }

    override fun initDataBinding() {
        recommendTimeRecyclerView = binding.recyclerviewRecommendTime
        dayTextViewRecyclerView = binding.recyclerviewDayText

        binding.view = this

        binding.btnTogglePromiseMode.setOnClickListener {
            isEditMode = !isEditMode
            Log.d("isEditModee", isEditMode.toString())
            binding.invalidateAll()
        }

        // 뷰 모델 가져오기
        viewPromiseTimeViewModel = ViewModelProvider(requireActivity())[ViewPromiseTimeViewModel::class.java]

        initRecyclerView()

        // 약속시간 관련 정보 리사이클러뷰에 바인딩
        // observe data
        viewPromiseTimeViewModel.recommendDate.observe(viewLifecycleOwner) { recommendDate ->
            recommendTimeRecyclerViewAdapter.updateData(recommendDate)
        }
    }

//    override fun initAfterBinding() {
//        super.initAfterBinding()
//        //initRecyclerView()
//    }

    private fun setTimeTable() {
        for (i in 0 .. END_TIME - START_TIME) {
            val tableRow = TableRow(requireContext())
            val params = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
                1f
            )

            tableRow.layoutParams = params

            val cell = TextView(requireContext())
            val cellId = getResourceId("cell_time_${i}")
            cell.id = cellId
            cell.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                CELL_HIEGHT_DP,
                pxToSp(10f)
            )
            cell.apply{
                text = "${START_TIME + i}"
                textSize = 10f
                setTextColor(ContextCompat.getColor(requireContext(), R.color.Gray4))
                gravity = Gravity.CENTER
            }
            tableRow.addView(cell)

            binding.tablelayoutTime.addView(tableRow)
        }
    }

    // TODO 참여도 별 색 변화
    private fun setShowPromiseTable() {
        cellMatrix = Array(ROW) { Array(COLUMN) { View(requireContext()) }}

        for (i in 0 until ROW) {
            val tableRow = TableRow(requireContext())
            val params = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                CELL_HIEGHT_DP*ROW,
                1f
            )

            tableRow.layoutParams = params

            for (j in 0 until COLUMN) {
                val cell = View(requireContext())
                val cellId = getResourceId("cell_show_promise_${i}_${j}")
                cell.id = cellId
                cell.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    CELL_HIEGHT_DP,
                    1f
                )
                cell.setBackgroundResource(R.drawable.cell_selector)
                cellMatrix[i][j] = cell

                tableRow.addView(cell)
            }

            binding.tablelayoutShowPromise.addView(tableRow)
        }
    }
    
    private fun setSelectPromiseTable() {
        cellMatrix = Array(ROW) { Array(COLUMN) { View(requireContext()) }}

        for (i in 0 until ROW) {
            val tableRow = TableRow(requireContext())
            val params = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                CELL_HIEGHT_DP*ROW,
                1f
            )

            tableRow.layoutParams = params

            for (j in 0 until COLUMN) {
                val cell = View(requireContext())
                val cellId = getResourceId("cell_promise_${i}_${j}")
                cell.id = cellId
                cell.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    CELL_HIEGHT_DP,
                    1f
                )
                cell.setBackgroundResource(R.drawable.cell_selector)
                cellMatrix[i][j] = cell

                tableRow.addView(cell)
            }

            binding.tablelayoutSelectPromise.addView(tableRow)
        }
        // TableLayout에 OnTouchListener 설정
        binding.tablelayoutSelectPromise.setOnTouchListener(TableTouchListener())
    }

    private fun initRecyclerView() {
        initRecommendTimeRecyclerView()
        setItemTouchHelper()

        initDayTextRecyclerView()
    }


    private fun initDayTextRecyclerView() {

        // 올바른 날짜 형식으로 시작 날짜 지정
        dayTextViewRecyclerViewAdapter = DayTextViewRecyclerViewAdapter("2024-08-28")
        dayTextViewRecyclerView.adapter = dayTextViewRecyclerViewAdapter // 어댑터 부착
        dayTextViewRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)


        val space = dipToPx(6f, requireContext())
        dayTextViewRecyclerView.addItemDecoration(HorizontalSpaceItemDecoration(space))
    }
    private fun initRecommendTimeRecyclerView() {
        recommendTimeRecyclerViewAdapter = RecommendTimeRecyclerViewAdapter(this,viewPromiseTimeViewModel)
        recommendTimeRecyclerView.adapter = recommendTimeRecyclerViewAdapter
        recommendTimeRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 좌우 간격 지정
        val space = dipToPx(16f, requireContext())
        recommendTimeRecyclerView.addItemDecoration(VerticalSpaceItemDecoration(space))
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
                    // 스와이프 상태를 어댑터에 업데이트(하나만 스와이프 가능하도록)
                    if (isCurrentlyActive) {
                        recommendTimeRecyclerViewAdapter.updateSwipedPosition(viewHolder.adapterPosition)
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
                            // 되돌아간 이후 토글상태 -1로 다시 변환
                            recommendTimeRecyclerViewAdapter.swipedPositionInit()
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
            attachToRecyclerView(recommendTimeRecyclerView)
        }
    }

    inner class TableTouchListener : View.OnTouchListener {
        private var startCellX: Int = -1
        private var startCellY: Int = -1
        private var endCellX: Int = -1
        private var endCellY: Int = -1

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // 터치된 위치를 기준으로 셀의 행과 열 계산
                    startCellX = calculateColumnIndex(event.x)
                    startCellY = calculateRowIndex(event.y)
                    endCellX = startCellX
                    endCellY = startCellY

                    handlePressed(startCellX, startCellY, endCellX, endCellY, true)

                    return true
                }

                MotionEvent.ACTION_MOVE -> {
                    // 터치된 위치를 기준으로 셀의 행과 열 계산
                    endCellX = calculateColumnIndex(event.x)
                    endCellY = calculateRowIndex(event.y)

                    handlePressed(startCellX, startCellY, endCellX, endCellY, true)

                    return true
                }

                MotionEvent.ACTION_UP -> {
                    if (startCellX != endCellX || startCellY != endCellY) {
                        val resultState = !cellMatrix[startCellY][startCellX].isSelected
                        handleDrag(startCellX, startCellY, endCellX, endCellY, resultState)
                        handlePressed(startCellX, startCellY, endCellX, endCellY, false)
                    } else {
                        handleTouch(startCellX, startCellY)
                        handlePressed(startCellX, startCellY, startCellX, startCellY, false)
                    }

                    return true
                }
            }
            return false
        }

        private fun handleTouch(cellX: Int, cellY: Int) {
            // 터치한 셀을 토글
            val view = cellMatrix[cellY][cellX]
            view.isSelected = !view.isSelected
        }

        private fun handleDrag(
            startX: Int,
            startY: Int,
            endX: Int,
            endY: Int,
            resultState: Boolean
        ) {
            // 드래그 중인 영역의 시작 셀과 끝 셀을 기준으로 선택 처리
            val minX = maxOf(minOf(startX, endX), 0)
            val maxX = minOf(maxOf(startX, endX), COLUMN - 1)
            val minY = maxOf(minOf(startY, endY), 0)
            val maxY = minOf(maxOf(startY, endY), ROW - 1)

            for (i in minX..maxX) {
                for (j in minY..maxY) {
                    cellMatrix[j][i].isSelected = resultState
                }
            }
        }

        private fun handlePressed(
            startX: Int,
            startY: Int,
            endX: Int,
            endY: Int,
            resultState: Boolean
        ) {
            // 드래그 중인 영역의 시작 셀과 끝 셀을 기준으로 선택 처리
            val minX = maxOf(minOf(startX, endX), 0)
            val maxX = minOf(maxOf(startX, endX), COLUMN - 1)
            val minY = maxOf(minOf(startY, endY), 0)
            val maxY = minOf(maxOf(startY, endY), ROW - 1)

            initPressed()

            for (i in minX..maxX) {
                for (j in minY..maxY) {
                    cellMatrix[j][i].isPressed = resultState
                }
            }
        }

        private fun initPressed() {
            for (i in 0 until ROW) {
                for (j in 0 until COLUMN) {
                    cellMatrix[i][j].isPressed = false
                }
            }
        }

        private fun calculateRowIndex(y: Float): Int {
            // Y 좌표를 통해 행을 계산
            val cellHeight = binding.tablelayoutSelectPromise.height / ROW
            return (y / cellHeight).toInt()
        }

        private fun calculateColumnIndex(x: Float): Int {
            // X 좌표를 통해 열을 계산
            val cellWidth = binding.tablelayoutSelectPromise.width / COLUMN
            return (x / cellWidth).toInt()
        }
    }

    private fun getResourceId(resourceName: String): Int {
        return resources.getIdentifier(resourceName, "id", requireActivity().packageName)
    }
    
    private fun addToToolbar() {
        // 툴바 찾기
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        val layout: LinearLayout = toolbar.findViewById(R.id.layout_toolbar_btn)

        val menuButton = ImageButton(requireContext())
        val refreshButton = ImageButton(requireContext())

        // TODO 이미지 넣기
        // 버튼 생성
        menuButton.apply {
            setImageResource(R.drawable.baseline_more_horiz_24)
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))

            // ImageButton에 LayoutParams 설정
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // 너비
                LinearLayout.LayoutParams.WRAP_CONTENT  // 높이
            ).apply {
                marginEnd = resources.getDimensionPixelSize(R.dimen.margin_btn_right) // 오른쪽 마진 설정
            }
            layoutParams = params
        }

        // 버튼 생성
        refreshButton.apply {
            setImageResource(R.drawable.img_refresh)
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))

            // ImageButton에 LayoutParams 설정
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // 너비
                LinearLayout.LayoutParams.WRAP_CONTENT  // 높이
            ).apply {
                marginEnd = resources.getDimensionPixelSize(R.dimen.margin_btn_right) // 오른쪽 마진 설정
            }
            layoutParams = params
        }

        // 툴바에 버튼 추가
        layout.removeAllViews()
        layout.addView(refreshButton)
        layout.addView(menuButton)

        // 버튼에 클릭 이벤트 추가
        menuButton.setOnClickListener {
            // TODO 메뉴 추가
        }
        refreshButton.setOnClickListener {
            // TODO 약속 갱신
        }
    }

}
