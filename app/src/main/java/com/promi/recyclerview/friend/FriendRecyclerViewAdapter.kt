package com.promi.recyclerview.friend

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.ui.group.FriendViewModel

class FriendRecyclerViewAdapter(
    private var friendItems: List<Friend>,
    private val viewModel: FriendViewModel) :
    RecyclerView.Adapter<FriendRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val friendProfileImage: ImageView = itemView.findViewById(R.id.iv_profile)
        val friendName: TextView = itemView.findViewById(R.id.tv_id)
        val friendCode: TextView = itemView.findViewById(R.id.tv_code)
        val checkBox: CheckBox = itemView.findViewById(R.id.btn_check)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend: Friend = friendItems[position]
        holder.friendName.text = friend.friendName
        holder.friendCode.text = friend.friendCode.toString()

        // 리스너를 제거
        holder.checkBox.setOnCheckedChangeListener(null)

        // 체크박스 상태 복원 또는 설정
        holder.checkBox.isChecked = viewModel.selectedFriends.value?.any { it.name == friend.friendName } == true


        // 체크박스 클릭 리스너 설정
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { //뷰모델에 선택된 친구 추가
                Log.d("체크박스","선택됨")
                viewModel.addSelectedFriend(friend)
            } else { //뷰 모델에 선택된 친구 제거
                viewModel.removeSelectedFriend(friend.friendCode) //코드를 매개변수로 넘겨서 일치하는 친구 제거
                Log.d("체크박스","선택취소")
            }
            notifyItemChanged(position)
            //notifyDataSetChanged() //변경사실 알리기
        }
    }

    override fun getItemCount(): Int {
        return friendItems.size
    }

    //체크박스 상태 업데이트용
    fun toggleFriendSelection(friendId: Int) {
        val index = friendItems.indexOfFirst { it.friendCode == friendId }
        if (index != -1) {
            // 선택 상태를 반영
            notifyItemChanged(index)
        }
    }

    // 데이터를 업데이트하는 메서드
    fun updateData(newFriends: List<Friend>) {
        friendItems = newFriends
        notifyDataSetChanged()
    }
}
