package com.promi.ui.main

import com.promi.R
import com.promi.base.BaseBottomSheetDialog
import com.promi.databinding.DialogAddTodoBinding

class AddTodoDialog:BaseBottomSheetDialog<DialogAddTodoBinding> (R.layout.dialog_add_todo) {
    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.btnAdd.setOnClickListener {
            dismiss()
        }
    }
}