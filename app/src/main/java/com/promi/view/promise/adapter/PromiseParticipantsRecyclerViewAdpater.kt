package com.promi.view.promise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.promi.databinding.ItemPromiseMemberBinding


// 약속 참여자 목록을 표시하기 위한 리사이클러뷰
// PromiseDetailFragment에서 사용
class PromiseParticipantsRecyclerViewAdapter(private val participants: List<String>) :
    RecyclerView.Adapter<PromiseParticipantsRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPromiseMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // View binding
        fun bind(participant: String) {
            binding.tvMemberName.text = participant
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPromiseMemberBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return participants.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(participants[position])
    }


}