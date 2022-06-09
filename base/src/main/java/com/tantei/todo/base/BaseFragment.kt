package com.tantei.todo.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initEvent(view)
    }

    open fun initView(view: View?) {}
    open fun initEvent(view: View?) {}
}