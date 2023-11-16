package com.promi.ui.main

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.promi.R
import com.promi.base.BaseBottomSheetDialog
import com.promi.databinding.DialogCategoryBinding
import com.promi.ui.calendar.CalendarAdapter

class CategoryDialog:BaseBottomSheetDialog<DialogCategoryBinding> (R.layout.dialog_category) {
    private lateinit var categoryAdapter: CategoryAdapter
    override fun initStartView() {
        super.initStartView()

        categoryAdapter = CategoryAdapter()
        categoryAdapter.itemList.add("1")

        binding.listCategory.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        categoryAdapter.setItemClickListener(object: CategoryAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                AddTodoDialog().show(parentFragmentManager, "AddTodoDialog")
            }
        })
    }
}