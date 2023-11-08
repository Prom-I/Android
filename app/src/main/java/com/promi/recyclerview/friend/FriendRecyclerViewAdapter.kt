package com.promi.recyclerview.friend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.promi.R

class FriendRecyclerViewAdapter(private var friendItems: List<Friend>) :
    RecyclerView.Adapter<FriendRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val friendProfileImage: ImageView = itemView.findViewById(R.id.iv_profile)
        val friendName: TextView = itemView.findViewById(R.id.tv_id)
        val friendCode: TextView = itemView.findViewById(R.id.tv_code)
        val checkBox: CheckBox = itemView.findViewById(R.id.btn_check)

        init {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                // Handle check box change
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend: Friend = friendItems[position]
        holder.friendName.text = friend.friendName
        holder.friendCode.text = friend.friendCode.toString()
        // Set the state of the check box if needed
    }

    override fun getItemCount(): Int {
        return friendItems.size
    }

    // 데이터를 업데이트하는 메서드
    fun updateData(newFriends: List<Friend>) {
        friendItems = newFriends
        notifyDataSetChanged()
    }
}
