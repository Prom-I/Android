package com.promi.view.promise

import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentViewPromiseTimeBinding
import com.promi.view.promise.adapter.PromiseTimeRecyclerViewAdapter
import com.promi.view.promise.adapter.RecommendTimeItemClickListener
import com.promi.view.promise.adapter.RecommendTimeRecyclerViewAdapter
import com.promi.view.promise.adapter.TimeTextViewRecyclerViewAdapter
import com.promi.view.promise.adapter.VerticalSpaceItemDecoration
import com.promi.viewmodel.group.GroupViewModel
import com.promi.viewmodel.promise.ViewPromiseTimeViewModel

class ViewPromiseTimeFragment : BaseFragment<FragmentViewPromiseTimeBinding>(R.layout.fragment_view_promise_time),RecommendTimeItemClickListener {

    // 시간 텍스트뷰 리사이클러뷰(왼쪽에 시간 보여주는 용도)
    private lateinit var timeTextViewRecyclerView : RecyclerView
    private lateinit var timeTextViewRecyclerViewAdapter : TimeTextViewRecyclerViewAdapter
    // 시간 리사이클러뷰(핵심 기능, 민트색으로 시간정보를 보여주는 부분)
    private lateinit var promiseTimeRecyclerView : RecyclerView
    private lateinit var promiseTimeRecyclerViewAdapter : PromiseTimeRecyclerViewAdapter
    // 추천 약속 시간
    private lateinit var recommendTimeRecyclerView : RecyclerView
    private lateinit var recommendTimeRecyclerViewAdapter: RecommendTimeRecyclerViewAdapter

    private lateinit var viewPromiseTimeViewModel : ViewPromiseTimeViewModel

    // 나중에 번들을 통해서 시간, 날짜 정보를 입력받아서 그만큼 그려주는 식으로
    // 디자인 수정해서 일주일로 고정될 경우, 7일씩 잘라서 데이터 불러오면 될듯
    private var timeSize : Int = 12 // 시간 범위(2차원 배열의 높이, 몇시부터 몇시까지인지)
    private var daySize : Int = 7 // 며칠을 보여줄 것인지(promiseTimeRecyclerView에서 사용, 2차원 배열의 가로)

    private var sWidthOneDP : Double = 0.0 // 스크린 1dp의 크기(디자인 기준으로 393이 가로의 크기임)
    private var sHeightOneDP : Double = 0.0


    // 화면 이동 로직 작성 필요
    override fun onRecommendTimeItemClicked(position: Int) {
        Log.d("onRecommendTimeItemClicked : ","$position")
    }

    override fun initStartView() {
        (activity as MainActivity).setToolbar(true, "1차 회의")
        addToToolbar()

        // 뷰 모델 가져오기
        viewPromiseTimeViewModel = ViewModelProvider(requireActivity())[ViewPromiseTimeViewModel::class.java]

        // 약속시간 관련 정보 리사이클러뷰에 바인딩
        // observe data
        viewPromiseTimeViewModel.recommendDate.observe(viewLifecycleOwner) { recommendDate ->
            recommendTimeRecyclerViewAdapter.updateData(recommendDate)
        }
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

        val width = requireContext().getScreenWidth() // 현재 프레임의 가로 길이


        // 아이템 위아래 간격 설정

        // 393 : 310 = width : x
        // x = 310/393 * width
        //val width = requireContext().getScreenWidth() // 현재 프레임의 가로 길이
        val x = (310.0/393)*width
        Log.d("heightLog","${x}")
        // 310 : 277 = x : height
        // height * 310 = 277x
        // height = (277.0/310)*x
        val height = (277.0/310)*x
        Log.d("heightLog","${height}")

        // 시간 개수만큼 나눠서 한 칸 간격 구하기 (*2 +1공식)
        val itemHeight = ((height)/(timeSize*2+1)).toInt()
        timeTextViewRecyclerView.addItemDecoration(VerticalSpaceItemDecoration(itemHeight.toInt()))
    }

    private fun initPromiseTimeRecyclerView(){
        val width = requireContext().getScreenWidth() // 현재 프레임의 가로 길이
        sWidthOneDP = (width/393.0)
        // 393 : 310 = width : x
        // x = 310/393 * width
        // x = 7*time cell
        // time cell = x/7
        // time cell = ((310/393)/7)*width
        val timeItemSize = ((310.0/393)/7)*width

        promiseTimeRecyclerViewAdapter = PromiseTimeRecyclerViewAdapter(timeSize,daySize,timeItemSize)
        promiseTimeRecyclerView.adapter = promiseTimeRecyclerViewAdapter // 어뎁터 부착
        // 가로로 그려줘야함(날짜는 가로로)
        promiseTimeRecyclerView.layoutManager = LinearLayoutManager(this.context,RecyclerView.HORIZONTAL,false)
    }

    private fun initRecommendTimeRecyclerView() {
        recommendTimeRecyclerViewAdapter = RecommendTimeRecyclerViewAdapter(this)
        recommendTimeRecyclerView.adapter = recommendTimeRecyclerViewAdapter
        recommendTimeRecyclerView.layoutManager = LinearLayoutManager(this.context)


        // 위 아래 간격 지정
        val space = dipToPx(16f,requireContext())
        recommendTimeRecyclerView.addItemDecoration(VerticalSpaceItemDecoration(space))

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