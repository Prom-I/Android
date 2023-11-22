package com.promi.ui.promise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promi.R
import com.promi.databinding.FragmentPromiseFinalSettingBinding

class PromiseFinalSettingFragment : Fragment() {

    private lateinit var binding : FragmentPromiseFinalSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPromiseFinalSettingBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        return binding.root
    }
}