package com.promi.ui.myInformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.promi.R
import com.promi.databinding.FragmentMyInformationBinding

class MyInformationFragment : Fragment() {

    private var _binding: FragmentMyInformationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val MyInformationViewModel =
            ViewModelProvider(this)[MyInformationViewModel::class.java]
        _binding = FragmentMyInformationBinding.inflate(inflater, container, false)


        // 친구 목록 클릭시
        binding.linearLayoutUserInfoButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_information_to_myFriendListFragment)
        }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}