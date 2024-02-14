package com.promi.view.promise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.promi.R

// 하루(세로로 긴 한개)에 대한 시간 리사이클러뷰
class PromiseTimeOneDayRecyclerViewAdapter(private val timeSize : Int)
    : RecyclerView.Adapter<PromiseTimeOneDayRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        // 포함돼있는 값 없음 => 색상만 변경해주면 됨
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        // '시간 한개'에 해당
        val view = LayoutInflater.from(context).inflate(R.layout.item_promise_time, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return timeSize
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 배경 색 설정은 여기서 해주면 될 듯?
        // 리사이클러뷰의 생성자 혹은 설정자를 통해서 어떤 칸에 어떤색인지를 입력받아서 위치별로 색 칠해주는 작업 필요
    }


}