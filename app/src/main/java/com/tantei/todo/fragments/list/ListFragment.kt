package com.tantei.todo.fragments.list

import android.app.AlertDialog
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import com.tantei.todo.R
import com.tantei.todo.base.BaseFragmentVB
import com.tantei.todo.data.models.TodoData
import com.tantei.todo.data.viewmodel.ToDoViewModel
import com.tantei.todo.databinding.FragmentListListBinding
import com.tantei.todo.fragments.SharedViewModel
import com.tantei.todo.fragments.list.adapter.ListAdapter
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

private const val TAG = "ListFragment"

/**
 * A fragment representing a list of Items.
 */

@AndroidEntryPoint
class ListFragment : BaseFragmentVB<FragmentListListBinding>(), SearchView.OnQueryTextListener {

    private val adapter: ListAdapter by lazy { ListAdapter() }
    private val toDoViewModel: ToDoViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListListBinding {
        return FragmentListListBinding.inflate(inflater, container, false)
    }
    // INIT view & event
    override fun initView(view: View?) {
        super.initView(view)
        setHasOptionsMenu(true)
        setupRecycleView()
    }
    private fun setupRecycleView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        // recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }

        swipeToDelete(recyclerView)
    }
    override fun initEvent(view: View?) {
        super.initEvent(view)
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        binding.listLayout.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }
    }
    override fun initObserve() {
        super.initObserve()
        toDoViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "initObserve: getAllData $it")
            adapter.setData(it)
            sharedViewModel.checkIfDatabaseEmpty(it)
        })
        sharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.noDataImageView.visibility = View.VISIBLE
                binding.noDataTextView.visibility = View.VISIBLE
            } else {
                binding.noDataImageView.visibility = View.GONE
                binding.noDataTextView.visibility = View.GONE
            }
        })
    }
    // OptionsMenu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_delete_all -> deleteAll()
            R.id.menu_prioity_high -> {
                toDoViewModel.sortByHighPriority.observe(viewLifecycleOwner, Observer { adapter.setData(it) })
            }
            R.id.menu_prioity_low -> toDoViewModel.sortByLowPriority.observe(this, Observer { adapter.setData(it) })
        }
        return super.onOptionsItemSelected(item)
    }
    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setPositiveButton("Yes") { _, _ ->
                toDoViewModel.deleteAll()
                Toast.makeText(requireContext(), "Successfully Removed Everything!", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("No") { _, _ -> }
            setTitle("Delete Everything?")
            setMessage("Are you sure you want to remove everything?")
            create().show()
        }
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"
        toDoViewModel.searchDatabase(searchQuery).observe(this, Observer {
            adapter.setData(it)
        })
    }
    // list
    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.dataList[viewHolder.bindingAdapterPosition]
                // Delete item
                toDoViewModel.deleteData(itemToDelete)
                adapter.notifyItemRemoved(viewHolder.bindingAdapterPosition)
                // Restore Deleted item
                restoreDeletedData(viewHolder.itemView, itemToDelete)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
    private fun restoreDeletedData(view: View, deleteItem: TodoData) {
        val snackBar = Snackbar.make(view, "Deleted ${deleteItem.title}", Snackbar.LENGTH_LONG)
        snackBar.setAction("Undo") {
            toDoViewModel.insertData(deleteItem)
        }
        snackBar.show()
    }

}