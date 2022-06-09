package com.tantei.todo.fragments.update

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.tantei.todo.R
import com.tantei.todo.base.BaseFragmentVB
import com.tantei.todo.data.models.Priority
import com.tantei.todo.databinding.FragmentUpdateBinding
import com.tantei.todo.fragments.SharedViewModel


class UpdateFragment : BaseFragmentVB<FragmentUpdateBinding>() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUpdateBinding {
        return FragmentUpdateBinding.inflate(inflater, container, false)
    }


    override fun initView(view: View?) {
        super.initView(view)
        setHasOptionsMenu(true)

        binding.editTextTitle.setText(args.currentItem.title)
        binding.editTextTextMultiLine.setText(args.currentItem.description)
        binding.spinner.setSelection(parsePriority(args.currentItem.priority))
        binding.spinner.onItemSelectedListener = sharedViewModel.listener
    }

    override fun initEvent(view: View?) {
        super.initEvent(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    private fun parsePriority(priority: Priority): Int {
        return when(priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }
}