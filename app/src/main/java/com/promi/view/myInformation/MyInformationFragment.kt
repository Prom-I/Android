package com.promi.view.myInformation

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentMyInformationBinding
import com.promi.viewmodel.myinformation.MyInformationViewModel

class MyInformationFragment : BaseFragment<FragmentMyInformationBinding>(R.layout.fragment_my_information) {
    private val viewModel by lazy {
        ViewModelProvider(this)[MyInformationViewModel::class.java]
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        // 친구 목록 클릭시
        binding.constraintBtnFriendList.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_information_to_myFriendListFragment)
        }

        // 프로필 수정 클릭시
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_information_to_editProfileFragment)
        }

        // 형관펜 목록 클릭시
        binding.constraintBtnPaletteList.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_information_to_paletteListFragment)
        }
    }

}