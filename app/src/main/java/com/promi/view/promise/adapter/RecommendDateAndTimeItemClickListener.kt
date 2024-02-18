package com.promi.view.promise.adapter

import com.promi.data.remote.model.RecommendTimeDetail

interface RecommendDateAndTimeItemClickListener {
    fun onRecommendDateAndTimeItemClicked(recommendTimeDetail : List<RecommendTimeDetail>)
}