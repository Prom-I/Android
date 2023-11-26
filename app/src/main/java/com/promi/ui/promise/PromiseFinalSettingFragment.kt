package com.promi.ui.promise

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promi.databinding.FragmentPromiseNameBinding

class PromiseFinalSettingFragment : Fragment() {

    private lateinit var binding : FragmentPromiseNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //fragment_promise_name.xml
        binding = FragmentPromiseNameBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        //tv_len_promise_name
        binding.etvPromiseName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val length = p0?.length
                binding.tvLenPromiseName.text = "${length}/30"
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        return binding.root
    }
}