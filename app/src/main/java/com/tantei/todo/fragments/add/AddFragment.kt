package com.tantei.todo.fragments.add

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tantei.todo.R
import com.tantei.todo.base.BaseFragmentVB
import com.tantei.todo.data.models.Priority
import com.tantei.todo.data.models.State
import com.tantei.todo.data.models.TodoData
import com.tantei.todo.data.viewmodel.ToDoViewModel
import com.tantei.todo.databinding.FragmentAddBinding
import com.tantei.todo.fragments.SharedViewModel

class AddFragment : BaseFragmentVB<FragmentAddBinding>() {

    private val todoViewModel: ToDoViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddBinding {
        return FragmentAddBinding.inflate(inflater, container, false)
    }

    override fun initView(view: View?) {
        super.initView(view)
        setHasOptionsMenu(true)
    }

    override fun initEvent(view: View?) {
        super.initEvent(view)
        binding.spinner.onItemSelectedListener = sharedViewModel.listener
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.meun_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val title = binding.editTextTitle.text.toString()
        val priority = binding.spinner.selectedItem.toString()
        val desc = binding.editTextTextMultiLine.text.toString()

        val validation = sharedViewModel.verifyDataFromUser(title, desc)
        if (!validation) {
            return
        }

        val data = TodoData(0, title, sharedViewModel.parsePriority(priority), desc, State.NEW)
        todoViewModel.insertData(data)
        Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }
}