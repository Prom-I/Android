package com.promi.view.friend

import androidx.fragment.app.Fragment
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentAddFriendsBinding


/**
 * A simple [Fragment] subclass.
 * Use the [AddFriendsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFriendsFragment : BaseFragment<FragmentAddFriendsBinding>(R.layout.fragment_add_friends) {
    override fun initStartView() {
        super.initStartView()

        (activity as MainActivity).setToolbar(true, "친구")
    }
}