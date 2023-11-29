package com.promi.ui.myInformation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promi.R
import com.promi.databinding.FragmentPaletteListBinding


// 전체 형관펜 목록과, 사용자가 즐겨찾기한 형관펜 목록을 볼 수 있는 프레그먼트
class PaletteListFragment : Fragment() {

    private lateinit var binding : FragmentPaletteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaletteListBinding.inflate(layoutInflater)

        return binding.root
    }
}