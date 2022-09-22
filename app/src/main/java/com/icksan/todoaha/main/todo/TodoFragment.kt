package com.icksan.todoaha.main.todo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.icksan.todoaha.R
import com.icksan.todoaha.core.data.Resource
import com.icksan.todoaha.core.domain.model.Todo
import com.icksan.todoaha.core.ui.TodoListAdapter
import com.icksan.todoaha.databinding.FragmentTodoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoViewModel by viewModel()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

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
            getTodo()
            bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.buttomSheetLayout)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//            binding.fabCreateTodo.setOnClickListener {
//                Log.d("TAG_TEST", "CLICKED")
//            }
            setView(null)
        }
    }

    private fun getTodo() {
        val todoData = viewModel.listTodo()
        todoData.observe(viewLifecycleOwner) { todo ->
            if (todo != null) {
                when (todo) {
                    is Resource.Loading<*> -> {
                        if(todo.data.toString() == "null") {
                            setViewCondition(
                                progress = false,
                                error = true,
                                empty = "empty.json"
                            )
                        } else {
                            setViewCondition(
                                progress = true,
                                error = false,
                                empty = "error.json"
                            )
                        }
                        Log.d("TAG_RESULT_LOADING", todo.message.toString())
                    }
                    is Resource.Success<*> -> {
                        setViewCondition(
                            progress = false,
                            error = false,
                            empty = "error.json"
                        )
                        setView(todo.data)
                        Log.d("TAG_RESULT_HASILL", todo.data.toString())
                    }
                    is Resource.Error<*> -> {
                        val message = todo.message.toString()
                        setViewCondition(
                            progress = false,
                            error = true,
                            empty = "error.json"
                        )
                        Log.d("TAG_RESULT_ERRR", message)
                    }
                }
            }
        }
    }

    private fun setView(data: List<Todo>?) {
        with(binding) {
            fabCreateTodo.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            bottomSheet.btnSubmit.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                getTodo()
            }
            val scoreboardAdapter = TodoListAdapter()
            scoreboardAdapter.setData(data)
            with(rvTodo) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = scoreboardAdapter
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