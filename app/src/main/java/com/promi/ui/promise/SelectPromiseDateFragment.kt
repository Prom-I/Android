package com.promi.ui.promise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promi.R
import com.promi.databinding.FragmentCreatePromiseBinding
import com.promi.databinding.FragmentSelectPromiseDateBinding

class SelectPromiseDateFragment : Fragment() {

    private lateinit var binding: FragmentSelectPromiseDateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectPromiseDateBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment


        return binding.root
    }

}