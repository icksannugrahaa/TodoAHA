package com.icksan.todoaha.main.todo

import android.R
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.icksan.todoaha.core.domain.model.Todo
import com.icksan.todoaha.core.utils.GlobalUtils.getDate
import com.icksan.todoaha.databinding.ActivityTodoDetailBinding

class TodoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoDetailBinding

    companion object {
        const val EXTRA_TODO_DATA = "extra_todo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val todoData = intent.getParcelableExtra<Todo>(EXTRA_TODO_DATA)
        supportActionBar?.title = todoData?.title
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.white)))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        with(binding) {
            tvTitle.text = todoData?.title
            tvDescription.text = todoData?.description
            tvDate.text = getDate(todoData?.createdAt!!, "dd/MM/yyyy hh:mm:ss")
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}