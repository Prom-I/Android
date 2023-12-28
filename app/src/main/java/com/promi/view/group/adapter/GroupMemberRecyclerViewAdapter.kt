package com.promi.view.group.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.data.remote.model.GroupMember
import com.promi.data.remote.model.Promise

// 그룹에 소속된 사용자 목록을 보여주기 위힘
class GroupMemberRecyclerViewAdapter
    : RecyclerView.Adapter<GroupMemberRecyclerViewAdapter.ItemViewHolder>(){

    private var groupMemberList : List<GroupMember> = emptyList() // 그룹에 소속된 사용자 목록

    inner class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val memberName : TextView = itemView.findViewById(R.id.tv_user_name) // 사용자 이름
        val memberImage : ImageView = itemView.findViewById(R.id.iv_member_image) // iv_member_image 사용자 이미지
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // 아이템 뷰의 레이아웃 지정
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group_member_list,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // 데이터 바인딩 수행
        val member = groupMemberList[position]
        holder.memberName.text = member.memberName // 이름 지정

        // 아이템을 꾹 누를 경우, 다이얼로그 띄우는 기능 추가 필요
    }

    override fun getItemCount(): Int {
        return groupMemberList.size
    }

    fun updateData(groupMemberList: List<GroupMember>) {
        this.groupMemberList = groupMemberList
    }
}