package com.promi.view.promise

import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentViewPromiseTimeBinding

class ViewPromiseTimeFragment : BaseFragment<FragmentViewPromiseTimeBinding>(R.layout.fragment_view_promise_time) {
    private val START_TIME = 9
    private val END_TIME = 15
    private val COLUMN = 7 // 동적으로 설정할 행의 개수
    private val ROW = END_TIME-START_TIME // 동적으로 설정할 열의 개수
    private val CELL_HIEGHT = 24
    private var CELL_HIEGHT_DP = 0
    private lateinit var cellMatrix: Array<Array<View>>

    override fun initStartView() {
        super.initStartView()

        (activity as MainActivity).setToolbar(true, "약속 이름")
        addToToolbar()

        CELL_HIEGHT_DP = pxToDp(CELL_HIEGHT)
        setTimeTable()
        setPromiseTable()
    }

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

    private fun setPromiseTable() {
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

            binding.tablelayoutPromise.addView(tableRow)
        }

        // TableLayout에 OnTouchListener 설정
        binding.tablelayoutPromise.setOnTouchListener(TableTouchListener())
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

        private fun handleDrag(startX: Int, startY: Int, endX: Int, endY: Int, resultState: Boolean) {
            // 드래그 중인 영역의 시작 셀과 끝 셀을 기준으로 선택 처리
            val minX = maxOf(minOf(startX, endX), 0)
            val maxX = minOf(maxOf(startX, endX), COLUMN-1)
            val minY = maxOf(minOf(startY, endY), 0)
            val maxY = minOf(maxOf(startY, endY), ROW-1)

            for (i in minX..maxX) {
                for (j in minY..maxY) {
                    cellMatrix[j][i].isSelected = resultState
                }
            }

            Log.d("DRAG_DRAG", "[$resultState] start: ($startX, $startY), end: ($endX, $endY)")
        }

        private fun handlePressed(startX: Int, startY: Int, endX: Int, endY: Int, resultState: Boolean) {
            // 드래그 중인 영역의 시작 셀과 끝 셀을 기준으로 선택 처리
            val minX = maxOf(minOf(startX, endX), 0)
            val maxX = minOf(maxOf(startX, endX), COLUMN-1)
            val minY = maxOf(minOf(startY, endY), 0)
            val maxY = minOf(maxOf(startY, endY), ROW-1)

            for (i in minX..maxX) {
                for (j in minY..maxY) {
                    cellMatrix[j][i].isPressed = resultState
                }
            }

            Log.d("DRAG_PRESSED", "[$resultState] start: ($startX, $startY), end: ($endX, $endY)")
        }

        private fun calculateRowIndex(y: Float): Int {
            // Y 좌표를 통해 행을 계산
            val cellHeight = binding.tablelayoutPromise.height / ROW
            return (y / cellHeight).toInt()
        }

        private fun calculateColumnIndex(x: Float): Int {
            // X 좌표를 통해 열을 계산
            val cellWidth = binding.tablelayoutPromise.width / COLUMN
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

        val menuButton = Button(requireContext())
        val refreshButton = Button(requireContext())

        // TODO 이미지 넣기
        // 버튼 생성
        menuButton.apply{
            text = "..."
            textSize = 17f
            setTextColor(ContextCompat.getColor(context, R.color.mainBlack))
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
        }

        // 버튼 생성
        refreshButton.apply{
            text = "..."
            textSize = 17f
            setTextColor(ContextCompat.getColor(context, R.color.mainBlack))
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
        }

        // 툴바에 버튼 추가
        layout.removeAllViews()
        layout.addView(menuButton)
        layout.addView(refreshButton)

        // 버튼에 클릭 이벤트 추가
        menuButton.setOnClickListener {
            // TODO 메뉴 추가
        }
        refreshButton.setOnClickListener {
            // TODO 약속 갱신
        }
    }

}
