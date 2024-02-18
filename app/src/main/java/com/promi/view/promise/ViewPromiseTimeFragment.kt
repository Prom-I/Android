package com.promi.view.promise

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentViewPromiseTimeBinding
import com.promi.view.promise.adapter.DayTextViewRecyclerViewAdapter
import com.promi.view.promise.adapter.HorizontalSpaceItemDecoration
import com.promi.view.promise.adapter.RecommendTimeItemClickListener
import com.promi.view.promise.adapter.RecommendTimeRecyclerViewAdapter
import com.promi.view.promise.adapter.TimeTextViewRecyclerViewAdapter
import com.promi.view.promise.adapter.VerticalSpaceItemDecoration
import com.promi.viewmodel.promise.ViewPromiseTimeViewModel

class ViewPromiseTimeFragment : BaseFragment<FragmentViewPromiseTimeBinding>(R.layout.fragment_view_promise_time),RecommendTimeItemClickListener {

    // 날짜 텍스트뷰 리사이클러뷰
    private lateinit var dayTextViewRecyclerView : RecyclerView
    private lateinit var dayTextViewRecyclerViewAdapter : DayTextViewRecyclerViewAdapter

    // 시간 텍스트뷰 리사이클러뷰(왼쪽에 시간 보여주는 용도)
    private lateinit var timeTextViewRecyclerView : RecyclerView
    private lateinit var timeTextViewRecyclerViewAdapter : TimeTextViewRecyclerViewAdapter
    // 추천 약속 시간
    private lateinit var recommendTimeRecyclerView : RecyclerView
    private lateinit var recommendTimeRecyclerViewAdapter: RecommendTimeRecyclerViewAdapter

    private lateinit var viewPromiseTimeViewModel : ViewPromiseTimeViewModel

    private var timeItemHeight : Double = 0.0 // 아이템 셀 하나의 세로 크기
  

    private val COLUMN = 7 // 동적으로 설정할 행의 개수
    private val ROW = 10 // 동적으로 설정할 열의 개수
    private lateinit var cellMatrix: Array<Array<View>>
  
    // 화면 이동 로직(추천 시간 클릭시)
    override fun onRecommendTimeItemClicked(position: Int) {
        Log.d("onRecommendTimeItemClicked : ","$position")
        navController.navigate(R.id.action_viewPromiseTimeFragment_to_recommendTimeDetailFragment)
    }

    override fun initStartView() {
        super.initStartView()
        //addToToolbar()

        cellMatrix = Array(ROW) { Array(COLUMN) { View(requireContext()) } }

        for (i in 0 until ROW) {
            val tableRow = TableRow(requireContext())
            val params = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1f
            )

            tableRow.layoutParams = params

            for (j in 0 until COLUMN) {
                val cell = View(requireContext())
                val cellId = getResourceId("cell_${i}_${j}")
                cell.id = cellId
                cell.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1f
                )
                cell.setBackgroundResource(R.drawable.cell_selector)
                cellMatrix[i][j] = cell

                tableRow.addView(cell)
            }

            binding.tableLayout.addView(tableRow)
        }
        // TableLayout에 OnTouchListener 설정
        binding.tableLayout.setOnTouchListener(TableTouchListener())
    }

    // * 데이터 바인딩 설정.
//    override fun initDataBinding() {
////        dayTextViewRecyclerView = binding.recyclerviewDayTime
////        timeTextViewRecyclerView = binding.recyclerviewTimeText
////        recommendTimeRecyclerView = binding.recyclerviewRecommendTime
//        // TableLayout에 OnTouchListener 설정
//        binding.tableLayout.setOnTouchListener(TableTouchListener())
//    }

//    override fun initAfterBinding() {
//        super.initAfterBinding()
//        //initRecyclerView()
//    }


    private fun addToToolbar() {
        // 툴바 찾기
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        val layout: LinearLayout = toolbar.findViewById(R.id.layout_toolbar_btn)

        val customButton = Button(requireContext())

        // 버튼 생성
        customButton.apply {
            text = "확인"
            textSize = 17f
            setTextColor(ContextCompat.getColor(context, R.color.mainBlack))
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
        }
        ROW
        // 툴바에 버튼 추가
        layout.removeAllViews()
        layout.addView(customButton)

        // 버튼에 클릭 이벤트 추가
        customButton.setOnClickListener {
            //
        }
    }
    private fun initRecyclerView() {
        initTimeTextRecyclerView()
        initRecommendTimeRecyclerView()
        initDayTextRecyclerView()
    }

    private fun initTimeTextRecyclerView() {
        // timeSize만큼 리사이클러뷰 그리기
        timeTextViewRecyclerViewAdapter = TimeTextViewRecyclerViewAdapter(ROW, "09:00")
        timeTextViewRecyclerView.adapter = timeTextViewRecyclerViewAdapter // 어뎁터 부착
        timeTextViewRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val width = requireContext().getScreenWidth() // 현재 프레임의 가로 길이


        // 아이템 위아래 간격 설정

        // 393 : 310 = width : x
        // x = 310/393 * width
        //val width = requireContext().getScreenWidth() // 현재 프레임의 가로 길이
        val x = (310.0 / 393) * width
        Log.d("heightLog", "${x}")
        // 310 : 277 = x : height
        // height * 310 = 277x
        // height = (277.0/310)*x
        val height = (277.0 / 310) * x
        Log.d("heightLog", "${height}")


        //val itemHeight = (timeItemHeight + 20)/3


        // 시간 개수만큼 나눠서 한 칸 간격 구하기 (*2 +1공식)
        val itemHeight = ((height) / ((ROW + 1) * 2 + 1)).toInt()
        timeTextViewRecyclerView.addItemDecoration(VerticalSpaceItemDecoration(itemHeight.toInt()))
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
                        handlePressed(startCellX, startCellY, endCellX, endCellY, false)
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
            Log.d("DRAG_TOUCH", "($cellX, ${cellY})")
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

            Log.d("DRAG_DRAG", "[$resultState] start: ($startX, $startY), end: ($endX, $endY)")
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

            for (i in minX..maxX) {
                for (j in minY..maxY) {
                    cellMatrix[j][i].isPressed = resultState
                }
            }

            Log.d(
                "DRAG_PRESSED",
                "[$resultState] start: ($startX, $startY), end: ($endX, $endY)"
            )
        }

        private fun calculateRowIndex(y: Float): Int {
            // Y 좌표를 통해 행을 계산
            val cellHeight = binding.tableLayout.height / ROW
            return (y / cellHeight).toInt()
        }

        private fun calculateColumnIndex(x: Float): Int {
            // X 좌표를 통해 열을 계산
            val cellWidth = binding.tableLayout.width / COLUMN
            return (x / cellWidth).toInt()
        }
    }

    private fun getResourceId(resourceName: String): Int {
        return resources.getIdentifier(resourceName, "id", requireActivity().packageName)
    }


}
