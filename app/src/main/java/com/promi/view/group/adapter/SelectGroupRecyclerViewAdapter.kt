package com.promi.view.group.adapter

import android.content.Context
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

    // 선택된 아이템의 위치를 추적
    private var selectedPosition: Int = -1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var tvGroupImage : ImageView 그룹 이미지
        var tvGroupName: TextView // 그룹의 이름
        var tvGroupMemberCount: TextView // 전체 인원수
        var btnCheck : CheckBox // 아이템이 존재하는 영역
        init {
            tvGroupName = itemView.findViewById(R.id.tv_group_name)
            tvGroupMemberCount = itemView.findViewById(R.id.tv_group_count)
            btnCheck = itemView.findViewById(R.id.btn_check)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        //화면에 뷰를 붙이기 위해 inflater가 필요
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //아이템 뷰 레이아웃 가져오기
        val view = inflater.inflate(R.layout.item_select_group, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group: Group = groups[position]

        // 체크박스 상태 설정
        holder.btnCheck.isChecked = (position == selectedPosition)

        // 체크박스 클릭 리스너 설정
        holder.btnCheck.setOnClickListener {
            val newPosition = holder.adapterPosition
            if (newPosition != selectedPosition) {
                notifyItemChanged(selectedPosition)  // 이전 선택 해제
                selectedPosition = newPosition  // 새로운 선택
                notifyItemChanged(selectedPosition)  // 새 선택 반영
            }
        }

        holder.tvGroupName.text = group.groupName
        holder.tvGroupMemberCount.text = group.groupMemberCount.toString()

    }

    override fun getItemCount(): Int {
        return groups.size
    }

    fun updateData(groups: List<Group>) {
        this.groups = groups
    }


}