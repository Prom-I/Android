package com.promi.recyclerview.palette

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.promi.R
import com.promi.ui.myInformation.MyInformationViewModel

// viewModel을 매개변수로 받아, 사용자가 즐겨찾기에 등록할경우 값 변경
class PaletteRecyclerViewAdapter(
    private var viewModel : MyInformationViewModel)
    : RecyclerView.Adapter<PaletteRecyclerViewAdapter.PaletteViewHolder>(){

    private var paletteList = emptyList<Palette>()

    // 사용자가 체크한 파레트 => 임시로 값을 넣어뒀다가 확인 누르면 통째로 값 넘기기
    private var selectedPalette = mutableListOf<Palette>() // 수정가능

    // 사용자가 지금까지 선택해둔 파레트들을 반환 => 'MY 형관펜'에 저장하기 위함
    fun getSelectedPalette() : MutableList<Palette>{
        return selectedPalette
    }

    fun setPaletteList(paletteList : List<Palette>){
        this.paletteList = paletteList
        notifyDataSetChanged()
    }

    inner class PaletteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        // 아이템의 색상 예시들(원)의 레이아웃들
        var colorList = mutableListOf<CardView>()
        val btnPaletteSelect: CheckBox = itemView.findViewById(R.id.btn_palette_select)//btn_palette_select
        val tvPaletteTitle: TextView = itemView.findViewById(R.id.tv_palette_title)//tv_palette_title
        private val color1: CardView = itemView.findViewById(R.id.cardview_color_1)//cardview_color_1
        private val color2: CardView = itemView.findViewById(R.id.cardview_color_2)//cardview_color_2
        private val color3: CardView = itemView.findViewById(R.id.cardview_color_3)//cardview_color_3
        private val color4: CardView = itemView.findViewById(R.id.cardview_color_4)//cardview_color_4
        private val color5: CardView = itemView.findViewById(R.id.cardview_color_5)//cardview_color_5
        private val color6: CardView = itemView.findViewById(R.id.cardview_color_6)//cardview_color_6
        private val color7: CardView = itemView.findViewById(R.id.cardview_color_7)//cardview_color_7
        private val color8: CardView = itemView.findViewById(R.id.cardview_color_8)//cardview_color_8
        private val color9: CardView = itemView.findViewById(R.id.cardview_color_9)//cardview_color_9
        private val color10: CardView = itemView.findViewById(R.id.cardview_color_10)//cardview_color_10

        init {
            colorList.add(color1)
            colorList.add(color2)
            colorList.add(color3)
            colorList.add(color4)
            colorList.add(color5)
            colorList.add(color6)
            colorList.add(color7)
            colorList.add(color8)
            colorList.add(color9)
            colorList.add(color10)
        }

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

        // 각 카드뷰에 색상 할당
        val context = holder.itemView.context
        palette.colorList.forEachIndexed { index, colorName ->
            if (index < holder.colorList.size) {
                val colorId = context.resources.getIdentifier(colorName, "color", context.packageName)
                val color = ContextCompat.getColor(context, colorId)
                val colorStateList = ColorStateList.valueOf(color)
                holder.colorList[index].backgroundTintList = colorStateList
            }
        }

        // 선택하면 임시로 배열에 삽입해두고, 사용자가 확인 누를경우 즐겨찾기 배열에 통째로 추가
        holder.btnPaletteSelect.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // 체크 상태일 때의 배경
                holder.itemView.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.shape_palette_selected)
                selectedPalette.add(palette) // 임시 배열에 삽입 => '추가하기'를 눌러서 'MY 형관펜'에 삽입
                Log.d("Favorite Palette",selectedPalette.toString())
            } else {
                // 체크 해제 상태일 때의 배경
                holder.itemView.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.shape_palette_unselected)
                selectedPalette.remove(palette) // 즐겨찾기 목록에서 제거
                Log.d("Favorite Palette",selectedPalette.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return paletteList.size
    }


}