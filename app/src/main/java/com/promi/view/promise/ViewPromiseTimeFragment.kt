package com.promi.view.promise

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentViewPromiseTimeBinding

class ViewPromiseTimeFragment : BaseFragment<FragmentViewPromiseTimeBinding>(R.layout.fragment_view_promise_time) {

    private val COLUMN = 7 // 동적으로 설정할 행의 개수
    private val ROW = 10 // 동적으로 설정할 열의 개수
    private lateinit var cellMatrix: Array<Array<View>>

    override fun initStartView() {
        super.initStartView()

        cellMatrix = Array(ROW) { Array(COLUMN) { View(requireContext()) }}

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



