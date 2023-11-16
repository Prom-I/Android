package com.promi.ui.friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promi.R
import com.promi.databinding.FragmentMyFriendListBinding

class MyFriendListFragment : Fragment() {

    private lateinit var binding : FragmentMyFriendListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyFriendListBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return binding.root
    }

}