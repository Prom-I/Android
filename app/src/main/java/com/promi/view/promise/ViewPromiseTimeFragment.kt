package com.promi.view.promise

import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentViewPromiseTimeBinding
import com.promi.view.promise.adapter.TimeTextViewRecyclerViewAdapter

class ViewPromiseTimeFragment : BaseFragment<FragmentViewPromiseTimeBinding>(R.layout.fragment_view_promise_time) {

    // 시간 텍스트뷰 리사이클러뷰(왼쪽에 시간 보여주는 용도)
    private lateinit var timeTextViewRecyclerView : RecyclerView
    private lateinit var timeTextViewRecyclerViewAdapter : TimeTextViewRecyclerViewAdapter
    // 시간 리사이클러뷰(핵심 기능, 민트색으로 시간정보를 보여주는 부분)
    private lateinit var promiseTimeRecyclerView : RecyclerView
    // 추천 약속 시간
    private lateinit var recommendTimeRecyclerView : RecyclerView

    private var timeSize : Int = 10 // 시간 범위(2차원 배열의 높이, 몇시부터 몇시까지인지)
    private var daySize : Int = 0 // 며칠을 보여줄 것인지(promiseTimeRecyclerView에서 사용, 2차원 배열의 가로)

    override fun initStartView() {
        (activity as MainActivity).setToolbar(true, "1차 회의")
        addToToolbar()

        // 뷰 모델 가져오기
    }

    // * 데이터 바인딩 설정.
    override fun initDataBinding() {
        timeTextViewRecyclerView = binding.recyclerviewTimeText
        promiseTimeRecyclerView = binding.recyclerviewViewPromiseTime
        recommendTimeRecyclerView = binding.recyclerviewRecommendTime
    }

    // * 바인딩 이후에 할 일을 여기에 구현. * 그 외에 설정할 것이 있으면 이곳에서 설정. * 클릭 리스너도 이곳에서 설정.
    override fun initAfterBinding() {
        initRecyclerView()
    }

    private fun initRecyclerView(){
        initTimeTextRecyclerView()
        initPromiseTimeRecyclerView()
        initRecommendTimeRecyclerView()
    }

    private fun initTimeTextRecyclerView(){
        // timeSize만큼 리사이클러뷰 그리기
        timeTextViewRecyclerViewAdapter = TimeTextViewRecyclerViewAdapter(timeSize,"09:00")
        timeTextViewRecyclerView.adapter = timeTextViewRecyclerViewAdapter // 어뎁터 부착
        timeTextViewRecyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    private fun initPromiseTimeRecyclerView(){

    }

    private fun initRecommendTimeRecyclerView() {

    }


    private fun addToToolbar() {
        // 툴바 찾기
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        val layout: LinearLayout = toolbar.findViewById(R.id.layout_toolbar_btn)

        val customButton = Button(requireContext())

        // 버튼 생성
        customButton.apply{
            text = "확인"
            textSize = 17f
            setTextColor(ContextCompat.getColor(context, R.color.mainBlack))
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
        }

        // 툴바에 버튼 추가
        layout.removeAllViews()
        layout.addView(customButton)

        // 버튼에 클릭 이벤트 추가
        customButton.setOnClickListener {
            //navController.navigate(R.id.action_selectGroupFragment_to_selectPromiseDateFragment)
        }
    }

}