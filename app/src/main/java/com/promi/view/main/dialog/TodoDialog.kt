package com.promi.view.main.dialog

import com.promi.R
import com.promi.base.BaseDialogFragment
import com.promi.databinding.DialogTodoBinding

class TodoDialog(private val year: String, private val month: String, private val date: String) :BaseDialogFragment<DialogTodoBinding> (R.layout.dialog_todo) {
    override fun initDataBinding() {
        super.initDataBinding()

        val format: String = String.format(getString(R.string.todo_month_date_format), year, month, date,)
        binding.tvTodoDate.text = format
    }
    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.btnAddTodo.setOnClickListener {
            CategoryDialog().show(parentFragmentManager, "CategoryDialog")
        }
    }
}