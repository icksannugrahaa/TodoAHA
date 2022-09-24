package com.icksan.todoaha.main.todo

import com.icksan.todoaha.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.snackbar.Snackbar
import com.icksan.todoaha.core.data.Resource
import com.icksan.todoaha.core.domain.model.Todo
import com.icksan.todoaha.core.ui.TodoListAdapter
import com.icksan.todoaha.core.utils.GlobalUtils
import com.icksan.todoaha.core.utils.GlobalUtils.showToast
import com.icksan.todoaha.databinding.FragmentTodoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoViewModel by viewModel()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private var currentItemSelected = 0
    private var currentIdSelected = 0
    private var currentItem = Todo(null, null, null, null, null)
    private var todoData = ArrayList<Todo>()
    private lateinit var dialogUtils: GlobalUtils
    private lateinit var todoAdapter: TodoListAdapter

    companion object {
        const val EXTRA_TODO_DATA = "extra_todo"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            getTodo(true)
            setView()
            setForm(null)
        }
    }

    private fun setForm(data: Todo?) {
        binding.bottomSheet.tietTitle.setText(data?.title ?: "")
        binding.bottomSheet.tietDescription.setText(data?.description ?: "")
        binding.bottomSheet.btnSubmit.text = if (data!=null) getString(R.string.update_todo) else getString(R.string.create_todo)
        currentIdSelected = if(data!=null) data.id!! else 0
    }

    private fun getTodo(needRefresh: Boolean = true) {
        val get = viewModel.listTodo()
        get.observe(viewLifecycleOwner) { todo ->
            if (todo != null) {
                when (todo) {
                    is Resource.Loading<*> -> {
                        setViewCondition(
                            progress = true,
                            error = false,
                            empty = "error.json"
                        )
                    }
                    is Resource.Success<*> -> {
                        if(todo.data?.isEmpty() == true) {
                            setViewCondition(
                                progress = false,
                                error = true,
                                empty = "empty.json"
                            )
                        } else {
                            setViewCondition(
                                progress = false,
                                error = false,
                                empty = "error.json"
                            )
                            if(needRefresh) {
                                setRecycleView(todo.data)
                            } else {
                                todoData.clear()
                                todoData.addAll(todo.data!!)
                                todoAdapter.setData(todoData)
                            }
                        }
                        get.removeObservers(viewLifecycleOwner)
                    }
                    is Resource.Error<*> -> {
                        setViewCondition(
                            progress = false,
                            error = true,
                            empty = "error.json"
                        )
                        get.removeObservers(viewLifecycleOwner)
                    }
                }
            }
        }
    }

    private fun storeTodo(isUpdate: Boolean = false) {
        with(binding) {
            val title = bottomSheet.tietTitle.text
            val description = bottomSheet.tietDescription.text
            if(title.toString() == "") {
                bottomSheet.tilTitle.error = getString(R.string.field_required)
            } else {
                bottomSheet.tilTitle.error = ""
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                val todo = Todo(
                    title = title.toString(),
                    description = description.toString(),
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                    id = if (isUpdate) currentIdSelected else null
                )
                val storeOrUpdate = if (isUpdate) viewModel.updateTodo(todo) else viewModel.storeTodo(todo)
                storeOrUpdate.observe(viewLifecycleOwner) { data ->
                    if (data != null) {
                        when (data) {
                            is Resource.Loading<*> -> {
                                showToast("Todo Stored !", requireContext())
                            }
                            is Resource.Success<*> -> {
                                setForm(null)
                                storeOrUpdate.removeObservers(viewLifecycleOwner)
                            }
                            is Resource.Error<*> -> {
                                storeOrUpdate.removeObservers(viewLifecycleOwner)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun deleteTodo(todo: Todo) {
        val delete = viewModel.deleteTodo(todo)
        delete.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                when (data) {
                    is Resource.Loading<*> -> {
                        showToast("Todo Deleted !", requireContext())
                    }
                    is Resource.Success<*> -> {
                        setForm(null)
                        getTodo(false)
                        delete.removeObservers(viewLifecycleOwner)
                    }
                    is Resource.Error<*> -> {
                        delete.removeObservers(viewLifecycleOwner)
                    }
                }
            }
        }
    }

    private fun setView() {
        with(binding) {
            fabCreateTodo.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                bottomSheet.tietTitle.requestFocus()
                setForm(null)
            }
            val keyboard: InputMethodManager =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.buttomSheetLayout)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            bottomSheet.btnSubmit.setOnClickListener {
                if(bottomSheet.btnSubmit.text.equals("Create Todo")) {
                    storeTodo(false)
                } else {
                    storeTodo(true)
                }
            }
            bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            keyboard.hideSoftInputFromWindow(view?.windowToken, 0)
                            getTodo(false)
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            keyboard.hideSoftInputFromWindow(view?.windowToken, 0)
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            keyboard.showSoftInput(
                                binding.bottomSheet.tietTitle,
                                InputMethodManager.SHOW_IMPLICIT
                            )
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
        }
    }

    private fun setRecycleView(data: List<Todo>?) {
        with(binding) {
            // RecycleView
            todoAdapter = TodoListAdapter()
            todoAdapter.setData(data)
            todoAdapter.onItemClick = {
                Log.d("TAG_CLICKED", "CLICKED")
                Intent(requireContext(), TodoDetailActivity::class.java).apply {
                    this.putExtra(EXTRA_TODO_DATA, it)
                    startActivity(this)
                }
            }
            with(rvTodo) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = todoAdapter
                if (data != null) {
                    todoData.clear()
                    todoData.addAll(data)
                    val swipeCallback = object: SwipeCallback(requireContext()) {
                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            when(direction) {
                                ItemTouchHelper.LEFT -> {
                                    currentItemSelected = viewHolder.absoluteAdapterPosition
                                    currentItem = todoData[viewHolder.absoluteAdapterPosition]
                                    todoData.removeAt(currentItemSelected)
                                    todoAdapter.setData(todoData)
                                    Snackbar.make(binding.rvTodo, "Delete ${currentItem.title}", Snackbar.LENGTH_LONG)
                                        .setAction(
                                            "Undo"
                                        ) {
                                            if(viewHolder.absoluteAdapterPosition < 0) {
                                                todoData.add(currentItem)
                                            } else {
                                                todoData.add(viewHolder.absoluteAdapterPosition, currentItem)
                                            }
                                            todoAdapter.setData(todoData)
                                        }.addCallback(object: Snackbar.Callback() {
                                            override fun onDismissed(
                                                transientBottomBar: Snackbar?,
                                                event: Int
                                            ) {
                                                if (event == DISMISS_EVENT_TIMEOUT) {
                                                    deleteTodo(currentItem)
                                                }
                                                super.onDismissed(transientBottomBar, event)
                                            }
                                        }).show()
                                }
                                ItemTouchHelper.RIGHT -> {
                                    setForm(todoData[viewHolder.bindingAdapterPosition])
                                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                                    bottomSheet.tietTitle.requestFocus()
                                    todoAdapter.setData(todoData)
                                }
                            }
                        }
                    }
                    val touchHelper = ItemTouchHelper(swipeCallback)
                    touchHelper.attachToRecyclerView(this)
                }
            }
        }
    }

    private fun setViewCondition(progress: Boolean, error: Boolean, empty: String) {
        with(binding) {
            progressBar.visibility = if (progress) View.VISIBLE else View.GONE
            viewError.root.visibility = if (error) View.VISIBLE else View.GONE
            viewError.tvError.text = if(empty == "error.json") getString(R.string.something_wrong) else getString(R.string.empty)
            viewError.lavAnim.setAnimation(empty)
        }
    }
}