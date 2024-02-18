package com.promi.view.promise

import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentRecommendTimeDetailBinding
import com.promi.view.promise.adapter.RecommendTimeDetailRecyclerViewAdapter
import com.promi.view.promise.adapter.VerticalSpaceItemDecoration
import com.promi.viewmodel.promise.RecommendTimeDetailViewModel

// 추천 날짜에 연관된 시간들 리스트 형식으로 제공
// 약속에 참여 가능한 멤버들, 불가능한 멤버들 보여주기

class RecommendTimeDetailFragment : BaseFragment<FragmentRecommendTimeDetailBinding>(R.layout.fragment_recommend_time_detail) {

    private lateinit var recommendTimeDetailRecyclerView: RecyclerView
    private lateinit var recommendTimeDetailRecyclerViewAdapter: RecommendTimeDetailRecyclerViewAdapter

    private lateinit var recommendTimeDetailViewModel : RecommendTimeDetailViewModel

    override fun initStartView() {
        (activity as MainActivity).setToolbar(true, "")
        addToToolbar()

        // 뷰 모델 가져오기
        recommendTimeDetailViewModel = ViewModelProvider(requireActivity())[RecommendTimeDetailViewModel::class.java]

        // observe data
        recommendTimeDetailViewModel.recommendDate.observe(viewLifecycleOwner) { recommendTime ->
            recommendTimeDetailRecyclerViewAdapter.updateData(recommendTime)
        }

    }

    // * 데이터 바인딩 설정.
    override fun initDataBinding() {
        recommendTimeDetailRecyclerView = binding.recyclerviewRecommendTimeDetail
    }

    // * 바인딩 이후에 할 일을 여기에 구현. * 그 외에 설정할 것이 있으면 이곳에서 설정. * 클릭 리스너도 이곳에서 설정.
    override fun initAfterBinding() {
        initRecyclerView()
    }

    private fun initRecyclerView(){
        recommendTimeDetailRecyclerViewAdapter = RecommendTimeDetailRecyclerViewAdapter()
        recommendTimeDetailRecyclerView.adapter = recommendTimeDetailRecyclerViewAdapter // 어뎁터 부착
        recommendTimeDetailRecyclerView.layoutManager = LinearLayoutManager(this.context)
        recommendTimeDetailRecyclerView.addItemDecoration(VerticalSpaceItemDecoration(dipToPx(20f,requireContext())))
    }

    // 설정 버튼(이미지 버튼)을 포함한 툴바로 생성
    private fun addToToolbar() {
        // 툴바 찾기
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        val layout: LinearLayout = toolbar.findViewById(R.id.layout_toolbar_btn)

        // 이미지 버튼으로 생성
        val imageButton = ImageButton(requireContext())

        // 이미지 버튼 설정
        imageButton.apply {
            setImageResource(R.drawable.baseline_more_horiz_24)
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))

            // ImageButton에 LayoutParams 설정
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // 너비
                LinearLayout.LayoutParams.WRAP_CONTENT  // 높이
            ).apply {
                marginEnd = resources.getDimensionPixelSize(R.dimen.margin_btn_right) // 오른쪽 마진 설정
            }
            layoutParams = params
        }

        // 툴바의 기존 뷰들을 제거하고 이미지 버튼 추가
        layout.removeAllViews()
        layout.addView(imageButton)

        // 이미지 버튼에 클릭 이벤트 추가
        imageButton.setOnClickListener {
            // 클릭 시 실행될 코드
        }

    }

}