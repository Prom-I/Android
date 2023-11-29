package com.promi.recyclerview.palette

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.ui.myInformation.MyInformationViewModel

// viewModel을 매개변수로 받아, 사용자가 즐겨찾기에 등록할경우 값 변경
class PaletteRecyclerViewAdapter(
    private var viewModel : MyInformationViewModel)
    : RecyclerView.Adapter<PaletteRecyclerViewAdapter.PaletteViewHolder>(){

    private var paletteList = emptyList<Palette>()

    // 사용자가 체크한 파레트 => 임시로 값을 넣어뒀다가 확인 누르면 통째로 값 넘기기
    private var selectedPalette = emptyList<Palette>()

    fun setPaletteList(paletteList : List<Palette>){
        this.paletteList = paletteList
    }

    inner class PaletteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        // 아이템의 색상 예시들(원)의 레이아웃들
        var colorList = emptyList<CardView>()
        val btnPaletteSelect = itemView.findViewById<CheckBox>(R.id.btn_palette_select)//btn_palette_select
        val tvPaletteTitle = itemView.findViewById<TextView>(R.id.tv_palette_title)//tv_palette_title
        val color1 = itemView.findViewById<CardView>(R.id.cardview_color_1)//cardview_color_1
        val color2 = itemView.findViewById<CardView>(R.id.cardview_color_2)//cardview_color_2
        val color3 = itemView.findViewById<CardView>(R.id.cardview_color_3)//cardview_color_3
        val color4 = itemView.findViewById<CardView>(R.id.cardview_color_4)//cardview_color_4
        val color5 = itemView.findViewById<CardView>(R.id.cardview_color_5)//cardview_color_5
        val color6 = itemView.findViewById<CardView>(R.id.cardview_color_6)//cardview_color_6
        val color7 = itemView.findViewById<CardView>(R.id.cardview_color_7)//cardview_color_7
        val color8 = itemView.findViewById<CardView>(R.id.cardview_color_8)//cardview_color_8
        val color9 = itemView.findViewById<CardView>(R.id.cardview_color_9)//cardview_color_9
        val color10 = itemView.findViewById<CardView>(R.id.cardview_color_10)//cardview_color_10

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaletteViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_palette,parent,false) //어디에 붙일것인지
        return PaletteViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaletteViewHolder, position: Int) {
        // 값 할당
        val palette = paletteList[position]

        holder.tvPaletteTitle.text = palette.paletteTitle // 제목 지정

        // 선택하면 임시로 배열에 삽입해두고, 사용자가 확인 누를경우 즐겨찾기 배열에 통째로 추가
        holder.btnPaletteSelect.setOnClickListener {
            // 임시 배열에 삽입하는 로직 작성 필요
        }
    }

    override fun getItemCount(): Int {
        return paletteList.size
    }


}