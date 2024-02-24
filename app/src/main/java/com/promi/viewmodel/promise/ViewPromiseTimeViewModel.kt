package com.promi.viewmodel.promise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promi.data.remote.model.Group
import com.promi.data.remote.model.RecommendDate

class ViewPromiseTimeViewModel : ViewModel() {
    // 추천 시간 리스트
    private val recommendDateList = mutableListOf<RecommendDate>()
    private val _recommendDateLiveData : MutableLiveData<List<RecommendDate>> = MutableLiveData()
    val recommendDate: LiveData<List<RecommendDate>> = _recommendDateLiveData // 관측 가능


    private val groupList = mutableListOf<Group>()
    private val _groupLiveData: MutableLiveData<List<Group>> = MutableLiveData()
    val groupLiveData: LiveData<List<Group>> = _groupLiveData

    init {
        // 더미 데이터
        recommendDateList.add(RecommendDate("08/28 (월)",3))
        recommendDateList.add(RecommendDate("08/29 (화)",2))
        recommendDateList.add(RecommendDate("08/30 (수)",4))
        _recommendDateLiveData.value = recommendDateList

    }

    // 그룹 삭제 이벤트
    fun deleteRecommend(recommendDate: RecommendDate){
        recommendDateList.remove(recommendDate)
        _recommendDateLiveData.value = recommendDateList.toList()
    }

}