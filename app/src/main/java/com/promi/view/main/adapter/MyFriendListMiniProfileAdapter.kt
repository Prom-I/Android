package com.promi.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.promi.databinding.ItemMiniProfileBinding
import com.promi.data.remote.model.Friend


// 메인 화면에서 내 친구 목록을 띄우는 어뎁터
class MyFriendListMiniProfileAdapter()
    : ListAdapter<Friend, MyFriendListMiniProfileAdapter.MyFriendListItemViewHolder>(
    MyFriendListMiniProfileDiffUtil
){

    // View Holder
    inner class MyFriendListItemViewHolder(private val binding: ItemMiniProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(friend: Friend){

            with(binding){
                tvName.text = friend.friendName
                btnClear.visibility = View.GONE
                //이미지뷰 넣어야함
            }

        }
    }

    // 뷰 홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFriendListItemViewHolder {
        val binding = ItemMiniProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyFriendListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyFriendListItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        object MyFriendListMiniProfileDiffUtil : DiffUtil.ItemCallback<Friend>() {
            override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
                return oldItem.friendCode == newItem.friendCode
            }
        }
    }

}