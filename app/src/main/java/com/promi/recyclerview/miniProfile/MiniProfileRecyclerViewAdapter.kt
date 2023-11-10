package com.promi.recyclerview.miniProfile

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.recyclerview.friend.FriendRecyclerViewAdapter
import com.promi.ui.group.FriendViewModel

// 4.아이템을 유지/관리하는 Adapter
class MiniProfileRecyclerViewAdapter(
    var miniProfileItems: List<MiniProfile>,
    var viewModel: FriendViewModel,
    var friendsAdapter: FriendRecyclerViewAdapter
) ://친구,선택된 친구를 관리하기위한 친구 리스트를 받아온다.
    RecyclerView.Adapter<MiniProfileRecyclerViewAdapter.ViewHolder>() { //리사이클러뷰 어댑터를 상속, Generic 값으로 innerClass인 ViewHolder를 넣어줘야함

    //(2) ViewHolder패턴 => View를 Holder에 넣어두었다가 재사용을 하기 위함
    //=> itemView는 onCreateViewHolder에서 전달받은 아이템 뷰의 레이아웃에 해당
    //=> onBindViewHolder에서 view에 groups의 값을 할당함
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 재사용할 뷰의 정보
        var miniProfileImage:ImageView = itemView.findViewById(R.id.img_profile)
        var name : TextView = itemView.findViewById(R.id.tv_name) // 이름
        var eraseBtn : ImageButton = itemView.findViewById(R.id.btn_clear) //삭제 버튼 x
    }

    //아이템 뷰의 레이아웃을 가져와서 화면에 붙임 (1)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        //화면에 뷰를 붙이기 위해 inflater가 필요
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //아이템 뷰 레이아웃 가져오기
        val view = inflater.inflate(R.layout.item_mini_profile, parent, false)
        return ViewHolder(view)
    }


    //(3)
    //itemView에 Array<MiniProfile>의 값을 할당함
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profile : MiniProfile = miniProfileItems[position]
        //holder.miniProfileImage = profile.profileImage 이미지 변경
        holder.name.text = profile.name // 이름

        //오른쪽 위의 삭제버튼 클릭시 배열에서 지워지도록
        holder.eraseBtn.setOnClickListener {
            //삭제 버튼 클릭시 라이브데이터에서 지우기
            viewModel.removeSelectedFriend(profile.id); // 일치하는 아이디를 쓰는 유저 지우기
            friendsAdapter.toggleFriendSelection(profile.id) //선택해제된 친구의 토글 상태 업데이트
        }

    }


    //리사이클러뷰의 아이템의 개수가 총 몇개인지를 리턴
    override fun getItemCount(): Int {
        return miniProfileItems.size
    }

    fun updateData(newMiniProfiles: List<MiniProfile>) {
        Log.d("체크", "업데이트 전 선택된 친구: $miniProfileItems")
        Log.d("체크", "업데이트 후 선택된 친구: $newMiniProfiles")
        miniProfileItems = newMiniProfiles
        notifyDataSetChanged() // 데이터가 변경되었음을 알립니다.
    }



}