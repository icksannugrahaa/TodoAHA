package com.icksan.todoaha.core.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.icksan.todoaha.R
import com.icksan.todoaha.core.domain.model.Todo
import com.icksan.todoaha.databinding.ItemListTodoBinding
import com.icksan.todoaha.main.todo.SwipeCallback


class TodoListAdapter: RecyclerView.Adapter<TodoListAdapter.ItemViewHolder>() {
    private var listData = ArrayList<Todo>()
    var onItemClick: ((Todo) -> Unit)? = null

    fun setData(newListData: List<Todo>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_todo, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListTodoBinding.bind(itemView)
        fun bind(data: Todo) {
            with(binding) {
                tvTodoTitle.text = data.title
                tvTodoDescription.text = data.description
            }
        }
        init {
            binding.root.setOnClickListener{
                onItemClick?.invoke(listData[bindingAdapterPosition])
            }
        }
    }
}