package com.tantei.todo.fragments.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tantei.todo.R
import com.tantei.todo.data.models.Priority
import com.tantei.todo.data.models.TodoData
import com.tantei.todo.fragments.list.ListFragmentDirections
import javax.inject.Inject


class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var dataList = emptyList<TodoData>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentData = dataList[position]

        val rowBackground = holder.itemView.findViewById<ConstraintLayout>(R.id.row_background)
        rowBackground.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentData)
            holder.itemView.findNavController().navigate(action)
        }
        val titleText = holder.itemView.findViewById<TextView>(R.id.title_text)
        val descText = holder.itemView.findViewById<TextView>(R.id.desc_text)
        val priorityIndicator = holder.itemView.findViewById<CardView>(R.id.priority_indicator)

        titleText.text = currentData.title
        descText.text = currentData.description
        when(currentData.priority) {
            Priority.HIGH -> priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
            Priority.MEDIUM -> priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
            Priority.LOW -> priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(todoData: List<TodoData>) {
        val todoDiffUtil = ToDoDiffUtil(dataList, todoData)
        val todoDiffResult = DiffUtil.calculateDiff(todoDiffUtil)
        this.dataList = todoData
        todoDiffResult.dispatchUpdatesTo(this)
    }
}