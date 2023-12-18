package com.promi.di

import android.content.Context
import com.promi.data.remote.api.NetworkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {
    val Any.TAG: String
        get() {
            val tag = javaClass.simpleName
            return if (tag.length <= 23) tag else tag.substring(0, 23)
        }

    private var retrofit: Retrofit? = null
    private var networkService: NetworkService? = null

    companion object {  // DCL 적용한 싱글톤 구현
        var instance: NetworkModule? = null
        fun getInstance(context: Context?): NetworkModule? {
            if (instance == null) {
                @Synchronized
                if (instance == null)
                    instance = NetworkModule()
            }
            return instance
        }
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/posts")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        networkService = retrofit?.create(NetworkService::class.java)
    }

//    fun getTest() {
//        val _response: Call<ArrayList<Test>>? = networkService?.getTest()
//        _response?.enqueue(object : Callback<ArrayList<Test>> {
//            override fun onResponse(
//                call: Call<ArrayList<Test>>,
//                response: Response<ArrayList<Test>>
//            ) {
//                if (response.isSuccessful) {
//                    val result: ArrayList<Test> = response.body()!!
//                    Log.d("TAG_TEST_RESULT", result[0].toString())
//                } else {
//                    Log.d("TAG_TEST_RESULT", "통신 에러: "+response.code().toString())
//                }
//            }
//
//            override fun onFailure(call: Call<ArrayList<Test>>, t: Throwable) {
//                t.printStackTrace()
//                Log.d("TAG_TEST_RESULT", "통신 실패: "+_response.toString())
//            }
//        })
//    }
}