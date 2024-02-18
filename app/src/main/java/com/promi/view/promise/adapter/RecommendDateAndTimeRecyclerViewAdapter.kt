package com.promi.view.promise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.data.remote.model.RecommendDateAndTime

class RecommendDateAndTimeRecyclerViewAdapter(
    private val listener : RecommendDateAndTimeItemClickListener)
    : RecyclerView.Adapter<RecommendDateAndTimeRecyclerViewAdapter.ViewHolder>(){

    private var recommendDateList : List<RecommendDateAndTime> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

        holder.itemView.setOnClickListener {
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