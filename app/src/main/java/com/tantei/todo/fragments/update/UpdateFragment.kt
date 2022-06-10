package com.tantei.todo.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tantei.todo.R
import com.tantei.todo.base.BaseFragmentVB
import com.tantei.todo.data.models.Priority
import com.tantei.todo.data.models.State
import com.tantei.todo.data.models.TodoData
import com.tantei.todo.data.viewmodel.ToDoViewModel
import com.tantei.todo.databinding.FragmentUpdateBinding
import com.tantei.todo.fragments.SharedViewModel


class UpdateFragment : BaseFragmentVB<FragmentUpdateBinding>() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val toDoViewModel: ToDoViewModel by activityViewModels()

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
        binding.spinner.setSelection(sharedViewModel.parsePriorityToInt(args.currentItem.priority))
        binding.spinner.onItemSelectedListener = sharedViewModel.listener
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save) {
            updateItem()
        } else if (item.itemId == R.id.menu_delete) {
            deleteItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setPositiveButton("Yes") { _, _ ->
                toDoViewModel.deleteData(args.currentItem)
                Toast.makeText(requireContext(), "Successfully Removed: ${args.currentItem.title}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            setNegativeButton("No") { _, _ -> }
            setTitle("Delete ${args.currentItem.title}?")
            setMessage("Are you sure you want to remove ${args.currentItem.title}?")
            create().show()
        }
    }

    private fun updateItem() {
        val title = binding.editTextTitle.text.toString()
        val priority = binding.spinner.selectedItem.toString()
        val desc = binding.editTextTextMultiLine.text.toString()

        val validation = sharedViewModel.verifyDataFromUser(title, desc)
        if (!validation) {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val updateItem = TodoData(args.currentItem.id, title, sharedViewModel.parsePriority(priority), desc, State.NEW)
        toDoViewModel.updateData(updateItem)
        Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }
}