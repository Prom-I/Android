package com.promi.view.promise.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.data.remote.model.RecommendDateAndTime

class RecommendDateAndTimeRecyclerViewAdapter(
    private val listener : RecommendDateAndTimeItemClickListener)
    : RecyclerView.Adapter<RecommendDateAndTimeRecyclerViewAdapter.ViewHolder>(){

    private var recommendDateList : List<RecommendDateAndTime> = emptyList()

    private var selectedPosition: Int = 0 // 클릭된 아이템 추적용(배경색상 변경)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: ConstraintLayout = itemView.findViewById(R.id.item_container)
        val tvRecommend : TextView = itemView.findViewById(R.id.tv_recommend) // 추천 1,추천 2, ... 업데이트
        val tvRecommendDate : TextView = itemView.findViewById(R.id.tv_date) // 추천하는 날짜
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_recommend_date2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recommendDate = recommendDateList[position]
        holder.tvRecommend.text = "추천 ${position+1}" // 추천 1
        holder.tvRecommendDate.text = recommendDate.recommendDate

//        // 배경색과 텍스트 색상 변경
//        if (selectedPosition == position) {
//            holder.itemView.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.black))
//            holder.tvRecommendDate.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
//        } else {
//            holder.itemView.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.mainLightGray))
//            holder.tvRecommendDate.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
//        }

        holder.itemView.setOnClickListener {
            // 이전 선택된 아이템을 원래 상태로 복원
//            notifyItemChanged(selectedPosition)
//            selectedPosition = holder.adapterPosition
//            // 새로 선택된 아이템 업데이트
//            notifyItemChanged(selectedPosition)

            listener.onRecommendDateAndTimeItemClicked(recommendDate.recommendTimeList) // 리사이클러뷰 아이템 변경 이벤트 작성 필요
        }
    }

    override fun getItemCount(): Int {
        return recommendDateList.size
    }

    fun updateData(recommendDate: List<RecommendDateAndTime>) {
        this.recommendDateList = recommendDate
    }


}