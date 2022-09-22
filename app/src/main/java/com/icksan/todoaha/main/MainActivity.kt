package com.icksan.todoaha.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.icksan.todoaha.R
import com.icksan.todoaha.databinding.ActivityMainBinding
import com.icksan.todoaha.main.maps.MapsFragment
import com.icksan.todoaha.main.todo.TodoFragment

class MainActivity : AppCompatActivity() {
    private var currentSelectItemId = R.id.btn_nav_todo
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val SAVED_STATE_CURRENT_TAB_KEY = "CurrentTabKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFragmentView(savedInstanceState)
    }

    private fun setFragmentView(savedInstanceState: Bundle?) {
        val todoFragment = TodoFragment()
        val mapsFragment = MapsFragment()

        if (savedInstanceState != null) {
            currentSelectItemId = savedInstanceState.getInt(SAVED_STATE_CURRENT_TAB_KEY)
        }

        when(currentSelectItemId) {
            R.id.btn_nav_todo -> setCurrentFragment(todoFragment, currentSelectItemId)
            R.id.btn_nav_maps -> setCurrentFragment(mapsFragment, currentSelectItemId)
        }

        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.btn_nav_todo -> setCurrentFragment(todoFragment, it.itemId)
                R.id.btn_nav_maps -> setCurrentFragment(mapsFragment, it.itemId)
                else -> throw AssertionError()
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment, itemId: Int) {
        currentSelectItemId = itemId
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).disallowAddToBackStack().commit()
    }
}