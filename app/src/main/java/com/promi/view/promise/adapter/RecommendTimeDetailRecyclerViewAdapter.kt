package com.promi.view.promise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.data.remote.model.RecommendTimeDetail

// 추천 약속 시간 상세 페이지
// 가능한 친구
// 불가능한 친구
class RecommendTimeDetailRecyclerViewAdapter()
    : RecyclerView.Adapter<RecommendTimeDetailRecyclerViewAdapter.ViewHolder>(){

    // 해당 리사이클러뷰 아이템 리스트
    private var recommendTimeDetailList : List<RecommendTimeDetail> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRecommendTime : TextView = itemView.findViewById(R.id.tv_recommend_time) // 9:00~10:00 약속 추천시간
        val tvAbleFriendCount : TextView = itemView.findViewById(R.id.tv_able_friend_count) // 가능한 사람 수
        var recyclerViewAbleFriend : RecyclerView = itemView.findViewById(R.id.recyclerview_able_friend) // 약속에 참여 가능한 사람
        val tvDisableFriendCount : TextView = itemView.findViewById(R.id.tv_disable_friend_count) // 불가능한 사람 수
        var recyclerViewDisableFriend : RecyclerView = itemView.findViewById(R.id.recyclerview_disable_friend) // 약속 참여 불가능한 사람
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_recommended_time, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recommendTimeDetail = recommendTimeDetailList[position]
        val ableFriendList = recommendTimeDetail.ableFriendList
        val disableFriendList = recommendTimeDetail.disableFriendList

        holder.tvRecommendTime.text = recommendTimeDetail.recommendTime // 추천 시간
        // 가능한 친구
        holder.tvAbleFriendCount.text = ableFriendList.size.toString()
        holder.recyclerViewAbleFriend.apply {
            adapter = PromiseParticipantsRecyclerViewAdapter(ableFriendList)
            layoutManager = GridLayoutManager(context, 5) // 한 줄에 5개의 아이템이 보이도록 설정
        }
        // 불가능한 친구
        holder.tvDisableFriendCount.text = disableFriendList.size.toString()
        holder.recyclerViewDisableFriend.apply {
            adapter = PromiseParticipantsRecyclerViewAdapter(disableFriendList)
            layoutManager = GridLayoutManager(context, 5) // 한 줄에 5개의 아이템이 보이도록 설정
        }
    }


    override fun getItemCount(): Int {
        return recommendTimeDetailList.size
    }

    fun updateData(recommendTimeDetailList: List<RecommendTimeDetail>) {
        this.recommendTimeDetailList = recommendTimeDetailList
    }


}