package com.tantei.todo.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tantei.todo.R
import com.tantei.todo.base.BaseFragmentVB
import com.tantei.todo.data.viewmodel.ToDoViewModel
import com.tantei.todo.databinding.FragmentListListBinding

/**
 * A fragment representing a list of Items.
 */
class ListFragment : BaseFragmentVB<FragmentListListBinding>() {

    private val adapter: ListAdapter by lazy { ListAdapter() }
    private val toDoViewModel: ToDoViewModel by activityViewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListListBinding {
        return FragmentListListBinding.inflate(inflater, container, false)
    }

    override fun initView(view: View?) {
        super.initView(view)
        setHasOptionsMenu(true)

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

    }

    override fun initEvent(view: View?) {
        super.initEvent(view)
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        binding.listLayout.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }

        toDoViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }
}