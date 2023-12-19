package com.promi.view.main.dialog

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.promi.R
import com.promi.base.BaseBottomSheetDialog
import com.promi.databinding.DialogCategoryBinding
import com.promi.view.main.adapter.CategoryAdapter

class CategoryDialog(private val month: String, private val date: String, private val day: String):BaseBottomSheetDialog<DialogCategoryBinding> (R.layout.dialog_category) {
    private lateinit var categoryAdapter: CategoryAdapter
    override fun initStartView() {
        super.initStartView()

        categoryAdapter = CategoryAdapter()
        categoryAdapter.itemList = arrayListOf("1", "2", "3", "4", "1", "2", "3", "4", "1", "2")

        binding.listCategory.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        categoryAdapter.setItemClickListener(object: CategoryAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                AddTodoDialog(month, date, day).show(parentFragmentManager, "AddTodoDialog")
            }
        })
    }
}