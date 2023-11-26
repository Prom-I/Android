package com.promi.recyclerview.friend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.promi.R


// 내 친구 목록을 확인 하기 위한 리사이클러뷰
class MyFriendListRecyclerViewAdapter :
    RecyclerView.Adapter<MyFriendListRecyclerViewAdapter.MyFriendListItemViewHolder>(){

    // MyFriendList by MyInformationViewModel(ui-myinformation-MyInformationViewModel)
    private var myFriendList : List<Friend> = emptyList()

    fun setItemList(items: List<Friend>){
        myFriendList = items
        notifyDataSetChanged()
    }

    // View Holder
    class MyFriendListItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        /*// Friend item  DTO
        var friendImagePath : String, // 이미지
        var friendName: String, // 친구 이름
        var friendCode : Int // 친구 아이디*/
        val friendName = itemView.findViewById<TextView>(R.id.tv_friend_name) // 친구 이름
        val friendCode = itemView.findViewById<TextView>(R.id.tv_friend_code) // 친구 아이디
    }

    // 뷰 홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFriendListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_friend,parent,false)
        return MyFriendListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myFriendList.size
    }

    override fun onBindViewHolder(holder: MyFriendListItemViewHolder, position: Int) {
        val friend = myFriendList[position]
        holder.apply {
            friendName.text = friend.friendName
            friendCode.text = "#${friend.friendCode.toString()}"
        }
    }


}