package com.promi.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PromiseItemSpaceItemDecoration(private val horizontalSpaceWidth: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // 모든 아이템에 대해 왼쪽에 간격 추가
        outRect.left = horizontalSpaceWidth
    }
}
