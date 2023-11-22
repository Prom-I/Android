package com.promi.ui.friend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentAddFriendsBinding
import com.promi.databinding.FragmentMyInformationBinding
import com.promi.ui.myInformation.MyInformationViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AddFriendsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFriendsFragment : BaseFragment<FragmentAddFriendsBinding>(R.layout.fragment_add_friends) {


    override fun initStartView() {
        super.initStartView()

        binding.icAddFriendBack.setOnClickListener{
            navController.popBackStack()
        }
    }


}