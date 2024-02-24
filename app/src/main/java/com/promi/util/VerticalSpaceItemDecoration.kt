package com.promi.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {
    private var option : Boolean = true // 첫번째 아이템에 대해 간격을 줄것인지

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = verticalSpaceHeight

        // 첫 번째 아이템에만 왼쪽 간격 추가
        if(option){
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = verticalSpaceHeight
            }
        }
    }

    fun setOption(option:Boolean){
        this.option = option
    }
}
