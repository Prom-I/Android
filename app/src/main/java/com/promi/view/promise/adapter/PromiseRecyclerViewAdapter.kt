package com.promi.view.promise.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.data.remote.model.Promise

// 그룹에 포함된 약속 목록들
class PromiseRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var promiseList : List<Promise> = emptyList()

    // 약속 타입 정의
    private val PROGRESS = 0 // 진행중인 약속
    private val DONE = 1 // 끝난 약속

    // 리스트 아이템 설정
    fun setList(itemList : List<Promise>){
        this.promiseList = itemList
    }

    // 가져온 뷰에서 사용되는 레이아웃 정의
    inner class ProgressPomiseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPromisePeriod: TextView = itemView.findViewById(R.id.tv_promise_period) // tv_promise_period
        val tvPromiseName : TextView = itemView.findViewById(R.id.tv_promise_name) // tv_promise_name
        val constraintDday : ConstraintLayout = itemView.findViewById(R.id.constraint_dday) // constraint_dday => d-day를 보여주기위한 뷰
        val tvDdayCount : TextView = itemView.findViewById(R.id.tv_dday_count) // tv_dday_count => d-day가 실질적으로 표시되는 부분
    }

    inner class EndedPromiseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvPromisePeriod: TextView = itemView.findViewById(R.id.tv_promise_period) // tv_promise_period
        val tvPromiseName : TextView = itemView.findViewById(R.id.tv_promise_name) // tv_promise_name
        val constraintDday : ConstraintLayout = itemView.findViewById(R.id.constraint_dday) // constraint_dday => d-day를 보여주기위한 뷰
        val tvDdayCount : TextView = itemView.findViewById(R.id.tv_dday_count) // tv_dday_count => d-day가 실질적으로 표시되는 부분
    }

    // 남아있는 기간으로, 아이템 타입 정의(아직 진행중인 약속인지, 끝난 약속인지)
    // 아이템의 타입에따라 약속/끝난 약속을 리턴
    override fun getItemViewType(position: Int): Int {
        Log.d("PromiseRecyclerView","getItemViewType Called")
        return if (promiseList[position].promiseDday <= 0){
            Log.d("PromiseRecyclerView","DONE")
            DONE // D-day가 0이하라면 끝난 약속임
        } else{
            Log.d("PromiseRecyclerView","PROGRESS")
            PROGRESS // 1이상이라면 진행중인 약속임
        }
    }


    // 뷰 레이아웃 가져와서 ViewHolder생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val progressPromiseView = LayoutInflater.from(context).inflate(R.layout.item_promise,parent,false)
        val endedPromiseView = LayoutInflater.from(context).inflate(R.layout.item_recommend_time,parent,false)

        // 타입을 보고 리턴할 뷰홀더 결정
        return if(viewType == PROGRESS){ // 진행중이라면
            ProgressPomiseViewHolder(progressPromiseView)
        } else { // 진행중이지 않다면
            EndedPromiseViewHolder(endedPromiseView)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val promise = promiseList[position]

        // 타입을 보고 결정
        if(holder.itemViewType == PROGRESS){
            // 다운 캐스팅 필요(현재는 ViewHolder타입이므로)
            val progressPromiseViewHolder = holder as ProgressPomiseViewHolder
            progressPromiseViewHolder.apply {
                tvPromiseName.text = promise.promiseName
                tvPromisePeriod.text = promise.promiseDate // 약속 기간
                if(promise.promiseDday <=5){
                    tvDdayCount.text = "D-${promise.promiseDday}"
                } else{ // 0보다 높은 경우에는 디데이를 표시하지 않음
                    constraintDday.visibility = View.GONE // 안보이게 설정
                }
            }
        } else if(holder.itemViewType == DONE){ // 끝난 약속의 경우
            val endedPromiseViewHolder = holder as EndedPromiseViewHolder // DownCasting
            endedPromiseViewHolder.apply {
                tvPromiseName.text = promise.promiseName
                tvPromisePeriod.text = promise.promiseDate
                tvDdayCount.text = "완료"
            }
        }
    }

    override fun getItemCount(): Int {
        return promiseList.size
    }

    fun updateData(newPromises: List<Promise>) {
        promiseList = newPromises
    }


}