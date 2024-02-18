package com.promi.view.promise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.data.remote.model.RecommendDate
import com.promi.view.promise.ViewPromiseTimeFragment
import java.util.PrimitiveIterator

// 그룹에 포함된 약속 목록들
class RecommendTimeRecyclerViewAdapter(
//    private val listener: ViewPromiseTimeFragment.TableTouchListener,
    private val itemClickListener : RecommendTimeItemClickListener
)
    : RecyclerView.Adapter<RecommendTimeRecyclerViewAdapter.ViewHolder>(){

    private var recommendDateList : List<RecommendDate> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRecommend : TextView = itemView.findViewById(R.id.tv_recommend) // 추천 1,추천 2, ... 업데이트
        val tvRecommendTime : TextView = itemView.findViewById(R.id.tv_recommend_time) // 추천하는 날짜
        val btnGreat : CheckBox = itemView.findViewById(R.id.btn_great) // 좋아요 버튼(선호도)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_recommend_time, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recommendDate = recommendDateList[position]
        holder.tvRecommend.text = "추천 ${position+1}" // 추천 1
        holder.tvRecommendTime.text = recommendDate.recommendDate
        holder.btnGreat.text = recommendDate.likeCount.toString()

        holder.itemView.setOnClickListener {
            itemClickListener.onRecommendTimeItemClicked(position) // 약속 페이지로 넘어가는 로직 작성 필요
        }
    }

    override fun getItemCount(): Int {
        return recommendDateList.size
    }

    fun updateData(recommendDate: List<RecommendDate>) {
        this.recommendDateList = recommendDate
    }


}