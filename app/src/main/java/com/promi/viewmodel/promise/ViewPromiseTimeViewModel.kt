package com.promi.viewmodel.promise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promi.data.remote.model.RecommendDate

class ViewPromiseTimeViewModel : ViewModel() {

    // 시간 관련 데이터
    // 날짜 하나의 길이만큼 생성 후, 숫자로 색상 여부 기록?
    // 예를 들어 연한 민트색이면 0, 민트색이면 1, 어두운 민트색이면 2
    // [0,0,0,1,1,1,2,0,0,0..] 이런식으로 배열 생성 후, PromiseTimeRecyclerViewAdpater로 넘기고,
    // 다시 PromiseTimeOneDayRecyclerView로 넘겨서 각 날짜별로 색상 칠하는 로직이 어떨지..?

    // 추천 시간 리스트
    private val _recommendDate = MutableLiveData<List<RecommendDate>>()
    val recommendDate: LiveData<List<RecommendDate>> = _recommendDate // 관측 가능

    init {
        // 더미 데이터
        val recommendDateDummyData = listOf(
            RecommendDate("08/28 (월)",3),
            RecommendDate("08/28 (월)",3),
            RecommendDate("08/28 (월)",3)
        )
        _recommendDate.value = recommendDateDummyData

    }

}