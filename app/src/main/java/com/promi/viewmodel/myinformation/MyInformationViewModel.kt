package com.promi.viewmodel.myinformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promi.data.remote.model.Friend
import com.promi.recyclerview.friend.Friend
import com.promi.recyclerview.palette.Palette

class MyInformationViewModel : ViewModel() {

    // 내 친구 목록
    private val _myFriends = MutableLiveData<List<Friend>>() //외부 수정 방지용
    val myFriends: LiveData<List<Friend>> = _myFriends

    // 내가 보유중인 형관펜 목록
    private val _palettes = MutableLiveData<List<Palette>>() //외부 수정 방지용
    val myPalette: LiveData<List<Palette>> = _palettes

    // 즐겨찾기 파레트
    private val _myFavoritePalettes = MutableLiveData<List<Palette>>() //외부 수정 방지용
    val myFavoritePalette: LiveData<List<Palette>> = _myFavoritePalettes

    // 원본 데이터를 저장해두기 위한변수(검색 이후에 되돌아 올때)
    private var allMyFriendsList: List<Friend> = listOf()

    init {
        // 초기 친구 목록 설정(더미 데이터)
        // 추후, 서버로부터 친구 목록 얻어오는 코드로 변경 필요
        val initialList = listOf(
            Friend("최강",555),
            Friend("남윤형",666),
            Friend("김지원",777),
        )

        val colorList = listOf(
            "palette_color1","palette_color2",
            "palette_color3","palette_color4",
            "palette_color5","palette_color6",
            "palette_color7","palette_color8",
            "palette_color9","palette_color10",
        )

        // 테스트용 색상 더미데이터2
        val colorList2 = listOf(
            "palette_color11", "palette_color12",
            "palette_color13", "palette_color14",
            "palette_color15", "palette_color16",
            "palette_color17", "palette_color18",
            "palette_color19", "palette_color20"
        )


        // 내 파레트(더미 데이터)
        val paletteList = listOf(
            Palette("나의 파레트 #1",colorList),
            Palette("나의 파레트 #2",colorList2),
            Palette("나의 파레트 #3",colorList),
            Palette("나의 파레트 #4",colorList2),
        )

        _myFriends.value = initialList //친구데이터의 MutableLiveData에 친구 정보 기입

        _palettes.value =  paletteList // 더미데이터(파레트 목록들)

        allMyFriendsList = _myFriends.value ?: listOf()

    }


    // 친구 검색 결과를 _myFriends의 값으로 반환
    // allMyFriendList에 전체 친구의 이름이 기록되어 있기 때문에, 검색결과를 반환해도 원본으 수정되지 않음
    fun searchFriend(name:String){
        //전체 친구 리스트에서 해당 이름을 포함하고 있는 이름으로 반환
        _myFriends.value = allMyFriendsList.filter {
            it.friendName.contains(name,ignoreCase = true)
        }
    }

    fun listInit(){
        _myFriends.value = allMyFriendsList
    }

    // 즐겨찾기에 파레트 추가
    fun addPaletteToFavorite(selectedPalettes: List<Palette>) {
        val currentFavorites = _myFavoritePalettes.value ?: listOf()
        _myFavoritePalettes.value = currentFavorites + selectedPalettes
    }


}