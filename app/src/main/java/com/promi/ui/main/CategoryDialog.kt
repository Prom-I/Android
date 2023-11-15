package com.promi.ui.main

import androidx.recyclerview.widget.GridLayoutManager
import com.promi.R
import com.promi.base.BaseBottomSheetDialog
import com.promi.databinding.DialogCategoryBinding

class CategoryDialog:BaseBottomSheetDialog<DialogCategoryBinding> (R.layout.dialog_category) {
    private lateinit var categoryAdapter: CategoryAdapter
    override fun initStartView() {
        super.initStartView()

        categoryAdapter = CategoryAdapter()

        binding.listCategory.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }
}