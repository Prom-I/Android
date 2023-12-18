package com.promi.view.promise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promi.databinding.FragmentPromiseSettingBinding

class PromiseSettingFragment : Fragment() {
    private lateinit var binding : FragmentPromiseSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPromiseSettingBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        return binding.root
    }
}