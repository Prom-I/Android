package com.promi.view.group.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.data.remote.model.Group
import com.promi.viewmodel.group.GroupViewModel

class SelectGroupRecyclerViewAdapter(
    var viewModel : GroupViewModel):
    RecyclerView.Adapter<SelectGroupRecyclerViewAdapter.ViewHolder>(){

    private var groups : List<Group> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var tvGroupImage : ImageView 그룹 이미지
        var tvGroupName: TextView // 그룹의 이름
        var tvGroupMemberCount: TextView // 전체 인원수
        var btnCheck : CheckBox // 아이템이 존재하는 영역
        init { //innerClass의 생성자에 해당 => 뷰의 레이아웃 가져오기 => 화면에 붙이기 위한 하나의 뷰를 만드는 과정에 해당
            tvGroupName = itemView.findViewById(R.id.tv_group_name)
            tvGroupMemberCount = itemView.findViewById(R.id.tv_group_count)
            btnCheck = itemView.findViewById(R.id.btn_check)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("SelectGroupFragment Error #4","${groups}")
        val context = parent.context
        //화면에 뷰를 붙이기 위해 inflater가 필요
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //아이템 뷰 레이아웃 가져오기
        val view = inflater.inflate(R.layout.item_select_group, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("SelectGroupFragment Error #4","${groups}")
        val group: Group = groups[position]
        holder.tvGroupName.text = group.groupName
        holder.tvGroupMemberCount.text = group.groupMemberCount.toString()

        // 하나가 선택되면 다른 값은 선택되지 않아야함 => 어떻게 처리할지 고민
    }

    override fun getItemCount(): Int {
        Log.d("SelectGroupFragment Error #3","${groups.size}")
        return groups.size
    }

    fun updateData(groups: List<Group>) {
        //Log.d("SelectGroupFragment Error #2","${groups}")
        this.groups = groups
        Log.d("SelectGroupFragment Error #2","${this.groups}")
    }


}