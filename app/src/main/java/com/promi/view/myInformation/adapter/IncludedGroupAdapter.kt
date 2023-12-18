package com.promi.view.myInformation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.promi.databinding.ItemGroupIncludedBinding

class IncludedGroupAdapter: RecyclerView.Adapter<IncludedGroupAdapter.ViewHolder>(){
    lateinit var context: Context
    var itemlist: ArrayList<String> = ArrayList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemViewBinding: ItemGroupIncludedBinding)
        :RecyclerView.ViewHolder(itemViewBinding.root){
        val tvGroupName: TextView = itemViewBinding.tvGroupName
        val btnSelectGroupScope: ImageButton = itemViewBinding.btnSelectGroupScope
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            ItemGroupIncludedBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvGroupName.text = itemlist.get(position)
        Log.d("dddd",itemlist.toString())

        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        holder.btnSelectGroupScope.setOnClickListener {
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