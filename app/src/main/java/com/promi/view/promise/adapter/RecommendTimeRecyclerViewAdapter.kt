package com.promi.view.promise.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.data.remote.model.RecommendDate
import com.promi.view.promise.ViewPromiseTimeFragment
import com.promi.viewmodel.promise.ViewPromiseTimeViewModel
import java.util.PrimitiveIterator

// 그룹에 포함된 약속 목록들
class RecommendTimeRecyclerViewAdapter(
    private val itemClickListener : RecommendTimeItemClickListener,
    var viewModel : ViewPromiseTimeViewModel
)
    : RecyclerView.Adapter<RecommendTimeRecyclerViewAdapter.ViewHolder>(){

    private var recommendDateList : List<RecommendDate> = emptyList()

    private var currentlySwipedPosition: Int = -1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRecommend : TextView = itemView.findViewById(R.id.tv_recommend) // 추천 1,추천 2, ... 업데이트
        val tvRecommendTime : TextView = itemView.findViewById(R.id.tv_recommend_time) // 추천하는 날짜
        val btnGreat : CheckBox = itemView.findViewById(R.id.btn_great) // 좋아요 버튼(선호도)
        val constraintItemContainer : ConstraintLayout = itemView.findViewById(R.id.constraint_item_container)
        val btnDelete : Button = itemView.findViewById(R.id.btn_delete) // 삭제버튼
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_recommend_time, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.scrollTo(0, 0) // 스와이프 상태 초기화 (삭제 이후 뷰 아이템의 스와이프 메뉴 활성화를 초기화)
        val recommendDate = recommendDateList[position]
        holder.tvRecommend.text = "추천 ${position+1}" // 추천 1
        holder.tvRecommendTime.text = recommendDate.recommendDate
        holder.btnGreat.text = recommendDate.likeCount.toString()

        holder.constraintItemContainer.setOnClickListener {
            // 현재 위치가 스와이프된 위치와 같지 않을 경우에만 네비게이션 이벤트 허용
            if (currentlySwipedPosition != position) {
                itemClickListener.onRecommendTimeItemClicked(position) // 약속 페이지로 넘어가는 로직 작성 필요
            }
        }
        // 삭제 기능 구현 필요
        holder.btnDelete.setOnClickListener {
            Log.d("Swipe Delete","$position")
            viewModel.deleteRecommend(recommendDate)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return recommendDateList.size
    }

    fun updateData(recommendDate: List<RecommendDate>) {
        this.recommendDateList = recommendDate
        //notifyDataSetChanged()
    }

    // 상태 추적 초기화
    fun swipedPositionInit(){
        currentlySwipedPosition = -1
    }

    // 스와이프 상태 업데이트 메소드
    fun updateSwipedPosition(newPosition: Int) {
        val previousPosition = currentlySwipedPosition
        currentlySwipedPosition = newPosition

        // 이전에 스와이프된 아이템의 상태를 원래대로 복구
        if (previousPosition != -1 && previousPosition != newPosition) {
            notifyItemChanged(previousPosition)
        }
    }


}