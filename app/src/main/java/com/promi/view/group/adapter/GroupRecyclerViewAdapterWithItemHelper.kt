package com.promi.view.group.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.data.remote.model.Group
import com.promi.util.ItemTouchHelperListener

//삽입과 삭제, 수정이 필요하므로 전달받는 데이터 리스트를 MutableList타입으로 받아야함
class GroupRecyclerViewAdapterWithItemHelper(
    private var navController: NavController,
    private var groups: MutableList<Group>):
    RecyclerView.Adapter<GroupRecyclerViewAdapterWithItemHelper.ViewHolder>(),
    ItemTouchHelperListener {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //그룹의 이미지를 변경하기 위함
        var tv_group_name: TextView // 그룹의 이름을 보여주기 위함
        var tv_group_member_count: TextView // 전체 인원수를 보여주기 위함
        init { //innerClass의 생성자에 해당 => 뷰의 레이아웃 가져오기 => 화면에 붙이기 위한 하나의 뷰를 만드는 과정에 해당
            tv_group_name = itemView.findViewById(R.id.tv_group_name)
            tv_group_member_count = itemView.findViewById(R.id.tv_group_count)
        }
    }


    //뷰 홀더 생성 => 재활용 하기 위한 뷰 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        //화면에 뷰를 붙이기 위해 inflater가 필요
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //아이템 뷰 레이아웃 가져오기
        val view = inflater.inflate(R.layout.item_group, parent, false)

        return ViewHolder(view)
    }

    //뷰 홀더에 위치한 뷰에 값 할당(항목 초기화)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group: Group = groups[position]
        holder.tv_group_name.text = group.groupName
        holder.tv_group_member_count.text = group.groupMemberCount.toString()

        //아이템 클릭 이벤트 작성
        holder.itemView.setOnClickListener {
            // 그룹 화면으로 이동
            // 번들에 그룹 이름 넘겨서 보냄(추후 그룹 아이디 등을 보내서 약속목록을 가져오는 로직 필요?)
            val bundle = Bundle()
            bundle.putString("groupName", holder.tv_group_name.text as String)
            navController.navigate(R.id.action_navigation_promise_to_groupFragment,bundle)
        }
    }

    override fun getItemCount(): Int {
        return groups.size
    }


    //## ItemTouchHelperListner interface implements ##

    //from_position에서 to_postion으로 아이템의 위치 이동
    //groups의 Group item에 대하여 사용자의 움직임에 맞춰서 배열에서의 위치도 변경해줘야함
    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Log.d("onItemMove","onMove")
        val group = groups[fromPosition]
        // 리스트 갱신
        groups.removeAt(fromPosition)
        groups.add(toPosition, group)
        // fromPosition에서 toPosition으로 아이템 이동 공지
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    //사용자가 아이템을 Swipe(왼쪽 혹은 오른쪽으로 밀때)할때 호출됨
    override fun onItemSwipe(position: Int) {
        // 리스트 아이템 삭제
        groups.removeAt(position) //해당 포지션의 아이템 삭제
        // 아이템 삭제되었다고 공지
        notifyItemRemoved(position)
    }

}