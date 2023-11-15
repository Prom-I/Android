package com.promi.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.promi.databinding.ItemCategoryBinding
import com.promi.ui.calendar.CalendarAdapter

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
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.ViewHolder {
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

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
        if (position % 2 == 0)
            holder.dividerHorizontal.visibility = View.GONE
    }
}