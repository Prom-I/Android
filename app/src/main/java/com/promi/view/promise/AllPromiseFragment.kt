package com.promi.view.promise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promi.R
import com.promi.databinding.FragmentAllPromiseBinding
import com.promi.databinding.FragmentAllPromisesAndGroupsBinding

class AllPromiseFragment : Fragment() {

    private lateinit var binding: FragmentAllPromiseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllPromiseBinding.inflate(layoutInflater)


        return binding.root
    }

}