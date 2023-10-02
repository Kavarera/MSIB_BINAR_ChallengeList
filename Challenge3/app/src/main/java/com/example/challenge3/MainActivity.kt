package com.example.challenge3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.challenge3.databinding.ActivityMainBinding
import com.example.challenge3.models.EnumListFragment
import com.example.challenge3.page.MenuFragment
import com.example.challenge3.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_home->{
                    viewModel.switchFragment(EnumListFragment.HOME)
                    true
                }
                else -> false
            }
        }
        viewModel.currentFragment.observe(this) { fragmentId ->
            when (fragmentId) {
                EnumListFragment.HOME -> switchToFragment(MenuFragment())
                else -> false
            }

        }

    }

    private fun switchToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragContainer,fragment).commit()
        Log.d("Nav","Switch Fragment To ${fragment.javaClass.simpleName}")
    }

}