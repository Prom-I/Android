package com.promi.view.promise.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.R

// 며칠을 그릴것인지 입력받아서, 그만큼 그린다.(daySize)
// one_day를 daysize 개수만큼 그려야함(세로를 가로 개 만큼)
class PromiseTimeRecyclerViewAdapter(
    private val timeSize: Int,
    private val daySize: Int,
    private val timeItemSize : Double)
    : RecyclerView.Adapter<PromiseTimeRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // 하루에 대한 리사이클러뷰를 내부에 갖고 있음 => '시간 한개' 를 timeSize만큼 그려주는 용도임
        val oneDayRecyclerView : RecyclerView = itemView.findViewById(R.id.recyclerview_promise_time_one_day)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        // 하루를 반복해서 그릴거니까 하루를 받아옴
        val view = LayoutInflater.from(context).inflate(R.layout.item_promise_time_one_day, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return daySize
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("TimeItemWidthSize : ","${timeItemSize}")

        // 세로 시간 그리기
        // 각 ViewHolder의 RecyclerView에 대한 어댑터 설정
        holder.oneDayRecyclerView.adapter = PromiseTimeOneDayRecyclerViewAdapter(timeSize,timeItemSize.toInt()) // 셀 한개에 대한 사이즈 넘기기
        holder.oneDayRecyclerView.layoutManager =
            LinearLayoutManager(holder.itemView.context) // 세로로 그리기('하루'에 대한 리사이클러뷰에서 시간은 세로로 그려진다.)

        // other codes ..
    }
}
