package com.promi.view.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.promi.databinding.ItemCategoryBinding

class CategoryAdapter() :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    lateinit var context: Context
    var itemList: ArrayList<String> = ArrayList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemViewBinding: ItemCategoryBinding)
        :RecyclerView.ViewHolder(itemViewBinding.root){
        val dividerHorizontal: View = itemViewBinding.dividerHorizontal
        val dividerVertical: View = itemViewBinding.dividerVertical
        val layoutCategory: ConstraintLayout = itemViewBinding.layoutCategory
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context
        return ViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int { return itemList.size}

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position % 2 == 1)
            holder.dividerHorizontal.visibility = View.GONE

        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        holder.layoutCategory.setOnClickListener {
            itemClickListener?.onClick(it, position)
        }
    }
    // (2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    // (4) setItemClickListener로 설정한 함수 실행
    private var itemClickListener : OnItemClickListener? = null
}