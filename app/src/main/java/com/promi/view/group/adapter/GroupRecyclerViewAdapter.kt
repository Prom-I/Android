package com.promi.view.group.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.data.remote.model.Group

//삽입과 삭제, 수정이 필요하므로 전달받는 데이터 리스트를 MutableList타입으로 받아야함
class GroupRecyclerViewAdapter(
    private var navController: NavController,
    private var groups: MutableList<Group>):
    RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var tvGroupImage : ImageView 그룹 이미지
        var tvGroupName: TextView // 그룹의 이름
        var tvGroupMemberCount: TextView // 전체 인원수
        // 삭제 버튼
        var btnDelete : Button // 삭제 버튼
        init { //innerClass의 생성자에 해당 => 뷰의 레이아웃 가져오기 => 화면에 붙이기 위한 하나의 뷰를 만드는 과정에 해당
            tvGroupName = itemView.findViewById(R.id.tv_group_name)
            tvGroupMemberCount = itemView.findViewById(R.id.tv_group_count)
            btnDelete = itemView.findViewById(R.id.btn_delete)
        }
    }


    // 뷰 홀더 생성 => 재활용 하기 위한 뷰 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        //화면에 뷰를 붙이기 위해 inflater가 필요
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //아이템 뷰 레이아웃 가져오기
        val view = inflater.inflate(R.layout.item_group, parent, false)

        return ViewHolder(view)
    }

    // 뷰 홀더에 위치한 뷰에 값 할당(항목 초기화)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group: Group = groups[position]
        holder.tvGroupName.text = group.groupName
        holder.tvGroupMemberCount.text = group.groupMemberCount.toString()

        // 아이템 클릭 이벤트 작성
        holder.itemView.setOnClickListener {
            // 그룹 화면으로 이동
            // 번들에 그룹 이름 넘겨서 보냄(추후 그룹 아이디 등을 보내서 약속목록을 가져오는 로직 필요?)
            val bundle = Bundle()
            bundle.putString("groupName", holder.tvGroupName.text as String)
            navController.navigate(R.id.action_navigation_promise_to_groupFragment,bundle)
        }

        // 삭제 버튼 이벤트
        holder.btnDelete.setOnClickListener {
            // 아이템 삭제 로직 작성 필요
        }
    }

    override fun getItemCount(): Int {
        return groups.size
    }

}