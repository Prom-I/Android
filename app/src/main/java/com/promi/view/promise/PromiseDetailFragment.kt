package com.promi.view.promise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promi.R
import com.promi.databinding.FragmentPromiseDetailBinding

class PromiseDetailFragment : Fragment() {

    private lateinit var binding : FragmentPromiseDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPromiseDetailBinding.inflate(layoutInflater)



        return binding.root
    }
}