package com.bignerdranch.android.blocknote

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARGS_DATE = "args_date_note_dialog"

class DatePickerFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dateListener = DatePickerDialog.OnDateSetListener{
            _:DatePicker, year: Int, month: Int, day: Int ->
            val resultDate: Date = GregorianCalendar(year,month,day).time
            targetFragment?.let{ fragment ->
                (fragment as Callbacks).onDateSelected(resultDate)
            }
        }
        val date = arguments?.getSerializable(ARGS_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }
    companion object{
        fun newInstance(date: Date): DatePickerFragment{
            var args = Bundle().apply {
                putSerializable(ARGS_DATE, date)
            }
            return DatePickerFragment().apply {
                arguments = args
            }
        }
    }

    interface Callbacks{
        fun onDateSelected(date: Date)
    }

}