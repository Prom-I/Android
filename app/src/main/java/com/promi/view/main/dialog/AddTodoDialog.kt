package com.promi.view.main.dialog

import com.promi.R
import com.promi.base.BaseBottomSheetDialog
import com.promi.databinding.DialogAddTodoBinding

class AddTodoDialog(private val month: String, private val date: String, private val day: String): BaseBottomSheetDialog<DialogAddTodoBinding> (R.layout.dialog_add_todo) {
    override fun initDataBinding() {
        super.initDataBinding()

        binding.tvAddTodoDate.text = String.format(getString(R.string.todo_month_date_format), month, date, day)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.btnAdd.setOnClickListener {
            dismiss()
        }
    }
}