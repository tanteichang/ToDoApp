package com.tantei.todo.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.tantei.todo.R
import com.tantei.todo.data.models.Priority

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position) {
                0 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red)) }
                1 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow)) }
                2 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green)) }
            }
        }
        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }


    fun verifyDataFromUser(title: String, desc: String): Boolean {
        return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(desc))
    }
    fun parsePriority(priority: String): Priority {
        return when(priority) {
            "High" -> Priority.HIGH
            "Medium" -> Priority.MEDIUM
            "Low" -> Priority.LOW
            else -> Priority.LOW
        }
    }
}