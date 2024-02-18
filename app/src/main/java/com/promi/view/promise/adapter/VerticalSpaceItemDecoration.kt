package com.promi.view.promise.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = verticalSpaceHeight

        // 첫 번째 아이템에만 위쪽 간격 추가
//        if (parent.getChildAdapterPosition(view) == 0) {
//            outRect.top = verticalSpaceHeight
//        }
    }
}
