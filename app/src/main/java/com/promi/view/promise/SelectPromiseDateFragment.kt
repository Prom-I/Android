package com.promi.view.promise

import com.google.android.material.datepicker.MaterialDatePicker
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentSelectPromiseDateBinding

class SelectPromiseDateFragment : BaseFragment<FragmentSelectPromiseDateBinding>(R.layout.fragment_select_promise_date) {
    var isChecked: Boolean = false
    private var pageIndex = 0

    override fun initStartView() {
        super.initStartView()

        (activity as MainActivity).setToolbar(true, "그룹 이름")

        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialCalendar)
                .build()

        dateRangePicker.show(childFragmentManager, "date_range_picker")
    }

    override fun initDataBinding() {
        super.initDataBinding()

        binding.test = this

        binding.switchIsAllDay.setOnClickListener {
            isChecked = !isChecked
            binding.invalidateAll()
        }
    }
}