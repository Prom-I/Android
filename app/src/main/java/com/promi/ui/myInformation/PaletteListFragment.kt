package com.promi.ui.myInformation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.databinding.FragmentPaletteListBinding
import com.promi.recyclerview.palette.PaletteRecyclerViewAdapter


// 전체 형관펜 목록과, 사용자가 즐겨찾기한 형관펜 목록을 볼 수 있는 프레그먼트
class PaletteListFragment : Fragment() {

    private lateinit var binding : FragmentPaletteListBinding

    private lateinit var paletteRecyclerView : RecyclerView
    private lateinit var paletteRecyclerViewAdapter : PaletteRecyclerViewAdapter
    private lateinit var myInformationViewModel : MyInformationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaletteListBinding.inflate(layoutInflater)

        myInformationViewModel =
            ViewModelProvider(this)[MyInformationViewModel::class.java]

        // Recyclerview init
        paletteRecyclerView = binding.recyclerviewPalette
        recyclerviewInit()


        // LiveData Observer 설정 => 변화 발생시 반영
        myInformationViewModel.myPalette.observe(viewLifecycleOwner, Observer { items ->
            paletteRecyclerViewAdapter.setPaletteList(items)
        })

        // 뒤로가기
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }

    fun recyclerviewInit(){
        paletteRecyclerView.layoutManager = LinearLayoutManager(context) // 화면에 부착
        // 어댑터 생성
        paletteRecyclerViewAdapter = PaletteRecyclerViewAdapter(myInformationViewModel)
        // 어댑터 부착
        paletteRecyclerView.adapter = paletteRecyclerViewAdapter
    }
}