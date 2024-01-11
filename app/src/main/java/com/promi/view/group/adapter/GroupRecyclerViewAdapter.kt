package com.promi.view.group.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.data.remote.model.Group
import com.promi.viewmodel.promise.PromiseViewModel

//삽입과 삭제, 수정이 필요하므로 전달받는 데이터 리스트를 MutableList타입으로 받아야함
class GroupRecyclerViewAdapter(
    private var navController: NavController,
    var viewModel : PromiseViewModel):
    RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder>(){

    private var groups = emptyList<Group>()

    // 현재 스와이프된 아이템의 위치 => 하나의 아이템만 스와이프 될 수 있도록
    private var currentlySwipedPosition: Int = -1

    // 어뎁터에서 사용할 리스트 값 할당
    fun setGroupList(groups : List<Group>){
        this.groups = groups
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var tvGroupImage : ImageView 그룹 이미지
        var tvGroupName: TextView // 그룹의 이름
        var tvGroupMemberCount: TextView // 전체 인원수
        var constraintItemContainer : ConstraintLayout // 아이템이 존재하는 영역
        // 삭제 버튼
        var btnDelete : Button // 삭제 버튼
        init { //innerClass의 생성자에 해당 => 뷰의 레이아웃 가져오기 => 화면에 붙이기 위한 하나의 뷰를 만드는 과정에 해당
            tvGroupName = itemView.findViewById(R.id.tv_group_name)
            tvGroupMemberCount = itemView.findViewById(R.id.tv_group_count)
            // 이 영역이 클릭된 경우에만 아이템 클릭 이벤트 활성화(삭제 메뉴 영역과 분리하기 위해)
            constraintItemContainer = itemView.findViewById(R.id.constraint_item_container) // 아이템 영역
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
        holder.itemView.scrollTo(0, 0) // 스와이프 상태 초기화 (삭제 이후 뷰 아이템의 스와이프 메뉴 활성화를 초기화)

        val group: Group = groups[position]
        holder.tvGroupName.text = group.groupName
        holder.tvGroupMemberCount.text = group.groupMemberCount.toString()

        // 아이템 클릭 이벤트 작성 => 아이템 영역이 클릭된 경우에 한해서
        holder.constraintItemContainer.setOnClickListener {

            // 현재 위치가 스와이프된 위치와 같지 않을 경우에만 네비게이션 이벤트 허용
            if (currentlySwipedPosition != position) {
                // 그룹 화면으로 이동
                // 번들에 그룹 이름 넘겨서 보냄(추후 그룹 아이디 등을 보내서 약속목록을 가져오는 로직 필요?
                val bundle = Bundle().apply {
                    putString("groupName", holder.tvGroupName.text as String)
                }
                navController.navigate(R.id.action_navigation_promise_to_groupFragment,bundle)
            } else {
                Log.d("Swipe State", "${viewModel.getItemSwipeState()}")
            }
        }

        // 삭제 버튼 이벤트
        holder.btnDelete.setOnClickListener {
            // 아이템 삭제 로직 작성 필요
            Log.d("Swipe Delete","$position")
            viewModel.deleteGroup(group)
        }
    }

    override fun getItemCount(): Int {
        return groups.size
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