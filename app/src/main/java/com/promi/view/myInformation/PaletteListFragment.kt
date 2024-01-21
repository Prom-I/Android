package com.promi.ui.myInformation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.databinding.FragmentPaletteListBinding
import com.promi.view.myInformation.adapter.PaletteRecyclerViewAdapter
import com.promi.viewmodel.myinformation.MyInformationViewModel


// 전체 형관펜 목록과, 사용자가 즐겨찾기한 형관펜 목록을 볼 수 있는 프레그먼트
class PaletteListFragment : Fragment() {

    private lateinit var binding : FragmentPaletteListBinding

    private lateinit var paletteRecyclerView : RecyclerView
    private lateinit var paletteRecyclerViewAdapter : PaletteRecyclerViewAdapter
    private lateinit var myInformationViewModel : MyInformationViewModel

    private lateinit var tvPenList : TextView
    private lateinit var tvMyPenList : TextView

    private lateinit var viewPenList : View
    private lateinit var viewMyPenList : View

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

        tvPenList = binding.tvPenList
        tvMyPenList = binding.tvMyPenList
        viewPenList = binding.viewPenListSelected
        viewMyPenList = binding.viewMyPenListSelected


        // 회색, 검은색 아이디 찾아두기 => 텍스트 선택시 색상변경을 위해
        val gray = context?.resources?.getIdentifier("Gray3", "color", context?.packageName)
        val black = context?.resources?.getIdentifier("black", "color", context?.packageName)
        val grayColor = gray?.let { id ->
            context?.let { ctx ->
                ContextCompat.getColor(ctx, id)
            }
        }
        val blackColor = black?.let{ id ->
            context?.let{ctx->
                ContextCompat.getColor(ctx,id)
            }
        }


        // 형관펜 클릭시
        tvPenList.setOnClickListener {
            viewPenList.visibility = View.VISIBLE
            viewMyPenList.visibility = View.GONE

            // 텍스트 색상 변경
            grayColor?.let { tvMyPenList.setTextColor(it)}
            blackColor?.let { tvPenList.setTextColor(it)}

            // 현재 리사이클러뷰 어뎁터의 펜 목록들을 지우고(빈 배열로 변경)
            paletteRecyclerViewAdapter.setPaletteList(emptyList())

            // 리사이클러뷰 어뎁터에 보여줄 리스트를 전체 펜 목록으로 변경
            myInformationViewModel.myPalette.value?.let { paletteList ->
                paletteRecyclerViewAdapter.setPaletteList(paletteList)
            }
        }

        // MY형관펜 클릭시
        tvMyPenList.setOnClickListener {
            viewMyPenList.visibility = View.VISIBLE
            viewPenList.visibility = View.GONE

            // 텍스트 색상 변경
            grayColor?.let { tvPenList.setTextColor(it)}
            blackColor?.let { tvMyPenList.setTextColor(it)}

            // 현재 리사이클러뷰 어뎁터의 펜 목록들을 지우고(빈 배열로 변경)
            paletteRecyclerViewAdapter.setPaletteList(emptyList())

            // 리사이클러뷰 어뎁터에 보여줄 리스트를 MY형관펜 목록으로 변경
            myInformationViewModel.myFavoritePalette.value?.let{
                myFavoriteList -> paletteRecyclerViewAdapter.setPaletteList(myFavoriteList)
            }

        }
        // 선택 항목 추가하기
        binding.tvAddSelectedPalette.setOnClickListener {
            // 사용자가 선택한 펜 목록
            val selectedPen = paletteRecyclerViewAdapter.getSelectedPalette()

            // 다이얼로그 띄우고, MY형관펜에 값 삽입
            // 나중에 커스텀 다이얼로그로 변경할 것
            AlertDialog.Builder(context)
                .setTitle("알림")
                .setMessage("저장이 완료되었습니다.")
                .create()
                .show()

            // 즐겨찾기 목록에 추가
            myInformationViewModel.addPaletteToFavorite(selectedPen)

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