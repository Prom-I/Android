package com.promi.view.promise

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

        initRecyclerView()
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
        //initDayTextRecyclerView()
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
        recommendTimeRecyclerViewAdapter = RecommendTimeRecyclerViewAdapter(this)
        recommendTimeRecyclerView.adapter = recommendTimeRecyclerViewAdapter
        recommendTimeRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 좌우 간격 지정
        val space = dipToPx(16f, requireContext())
        recommendTimeRecyclerView.addItemDecoration(VerticalSpaceItemDecoration(space))
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
