package com.promi.view.promise.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalSpaceItemDecoration(private val horizontalSpaceWidth: Int) : RecyclerView.ItemDecoration() {
    private var option : Boolean = true // 첫번째 아이템에 대해 간격을 줄것인지

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // 모든 아이템에 대해 오른쪽에 간격 추가
        outRect.right = horizontalSpaceWidth

        // 첫 번째 아이템에만 왼쪽 간격 추가
        if(option){
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.left = horizontalSpaceWidth
            }
        }

    }

    fun setOption(option:Boolean){
        this.option = option
    }
}
