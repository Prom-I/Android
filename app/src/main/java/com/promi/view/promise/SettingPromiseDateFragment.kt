package com.promi.view.promise

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import com.google.android.material.datepicker.MaterialDatePicker
import com.promi.MainActivity
import com.promi.R
import com.promi.base.BaseFragment
import com.promi.databinding.FragmentSettingPromiseDateBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class SettingPromiseDateFragment : BaseFragment<FragmentSettingPromiseDateBinding>(R.layout.fragment_setting_promise_date){
    private lateinit var dateRangePicker: MaterialDatePicker<Pair<Long, Long>>
    var isDateRangePickerVisible: Boolean = false

    private lateinit var startDate: String
    private lateinit var endDate: String
    var startTime: LocalTime = LocalTime.of(8, 0)
    var endTime: LocalTime = LocalTime.of(9, 0)
    var isChecked: Boolean = false
    var isTimePickerVisible: Boolean = false

    enum class SelectedTime {
        NONE,
        START,
        END
    }
    var selectedTime: SelectedTime = SelectedTime.NONE

    override fun initStartView() {
        super.initStartView()

        (activity as MainActivity).setToolbar(true, "그룹 이름")
        addToToolbar()

        binding.tvSelectStartTime.text = getFormattedTime(startTime.hour, startTime.minute)
        binding.tvSelectEndTime.text = getFormattedTime(endTime.hour, endTime.minute)
    }

    override fun initDataBinding() {
        super.initDataBinding()

        binding.test = this

        binding.tvSelectPromiseDate.setOnClickListener {
            isDateRangePickerVisible = true
            dateRangePicker.show(childFragmentManager, "date_range_picker")
            binding.invalidateAll()
        }

        binding.switchIsAllDay.setOnClickListener {
            isChecked = !isChecked
            selectedTime = SelectedTime.NONE
            isTimePickerVisible = false
            binding.invalidateAll()
        }

        binding.tvSelectStartTime.setOnClickListener {
            if (selectedTime == SelectedTime.START)
                isTimePickerVisible = !isTimePickerVisible
            else
                isTimePickerVisible = true

            selectedTime = SelectedTime.START
            binding.invalidateAll()
        }

        binding.tvSelectEndTime.setOnClickListener {
            if (selectedTime == SelectedTime.END)
                isTimePickerVisible = !isTimePickerVisible
            else
                isTimePickerVisible = true

            selectedTime = SelectedTime.END
            binding.invalidateAll()
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        setDateRangePicker()
        setTimePicker()
    }

    private fun getFormattedTime(hour: Int, minute: Int): String {
        val time = LocalTime.of(hour, minute)
        // Creating a DateTimeFormatter for 12-hour format
        val formatter = DateTimeFormatter.ofPattern("h:mm")
        // Formatting the LocalTime
        val formattedTime = time.format(formatter)
        val timePeriod = getAmPm(time)

        return getString(R.string.promise_time_format, formattedTime, timePeriod)
    }

    private fun getAmPm(time: LocalTime) : String {
        val isAM = time.isBefore(LocalTime.NOON)
        return if (isAM) "AM" else "PM"
    }

    private fun setDateRangePicker() {
        dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setSelection(
                Pair(
                    MaterialDatePicker.todayInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds() + (7 * 24 * 60 * 60 * 1000) // 7일을 밀리초로 변환
                )
            )
            .setTheme(com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialCalendar)
            .build()

        dateRangePicker.selection?.let { setDateRange(it) }

        dateRangePicker.addOnPositiveButtonClickListener {
            setDateRange(it)
        }

        // DateRangePicker가 닫힐 때의 이벤트 처리
        dateRangePicker.addOnDismissListener {
            isDateRangePickerVisible = false
            binding.invalidateAll()
        }
    }

    private fun setDateRange(selection: Pair<Long, Long>) {
        val dateFormat = getString(R.string.promise_date_format)
        val calendar = Calendar.getInstance()

        calendar.timeInMillis = selection.first ?: 0
        startDate = SimpleDateFormat(dateFormat, Locale.getDefault()).format(calendar.time).toString()
        calendar.timeInMillis = selection.second ?: 0
        endDate = SimpleDateFormat(dateFormat, Locale.getDefault()).format(calendar.time).toString()

        val dateRange = getString(R.string.promise_date_range_format).format(startDate, endDate)

        binding.tvSelectPromiseDate.text = dateRange
    }

    private fun setTimePicker() {
        binding.timePicker.setOnTimeChangedListener { view, hour, minute ->
            val time = getFormattedTime(hour, minute)
            when (selectedTime) {
                SelectedTime.START -> {
                    binding.tvSelectStartTime.text = time
                    startTime = LocalTime.of(hour, minute)
                }

                SelectedTime.END -> {
                    binding.tvSelectEndTime.text = time
                    endTime = LocalTime.of(hour, minute)
                }

                else -> {}
            }
        }
    }

    private fun addToToolbar() {
        // 툴바 찾기
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        val layout: LinearLayout = toolbar.findViewById(R.id.layout_toolbar_btn)

        val customButton = Button(requireContext())

        // 버튼 생성
        customButton.apply{
            text = "확인"
            textSize = 17f
            setTextColor(ContextCompat.getColor(context, R.color.mainBlack))
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
        }

        // 툴바에 버튼 추가
        layout.removeAllViews()
        layout.addView(customButton)

        // 버튼에 클릭 이벤트 추가
        customButton.setOnClickListener {
            navController.navigate(R.id.action_selectPromiseDateFragment_to_settingPromiseTimeFragment)
        }
    }

}