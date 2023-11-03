package com.example.challenge3.pages.MainPages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.challenge3.R
import com.example.challenge3.databinding.ActivityMainBinding
import com.example.challenge3.models.enumclass.EnumListFragment
import com.example.challenge3.util.viewmodels.MainActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel :MainActivityViewModel by inject()
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        FirebaseAuth.getInstance().signOut()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = this.findNavController(R.id.fragContainer)
        navController.popBackStack()
        navController.navigate(R.id.loginFragment)
        binding.bottomNavBar.setupWithNavController(navController)
        binding.bottomNavBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_home ->{
                    viewModel.switchFragment(EnumListFragment.HOME)
                    true
                }
                R.id.nav_keranjang ->{
                    viewModel.switchFragment(EnumListFragment.KERANJANG)
//                    navController.navigate(R.id.action_menuFragment_to_keranjangFragment)
                    true
                }

                R.id.nav_profile ->{
                    viewModel.switchFragment(EnumListFragment.PROFILE)
                    true
                }
                else -> false
            }
        }
        viewModel.currentFragment.observe(this) { fragmentId ->
            switchToFragment(fragmentId)

        }
        viewModel.bottomNavStat.observe(this){ visibility->
            if(visibility){
                binding.bottomNavBar.visibility=View.VISIBLE
            }
            else{
                binding.bottomNavBar.visibility=View.GONE
            }
        }

    }

    private fun switchToFragment(fragment: EnumListFragment) {
        when(fragment){
            EnumListFragment.HOME->{
                navController.popBackStack()
                navController.navigate(R.id.menuFragment,null)
            }
            EnumListFragment.KERANJANG->{
                navController.popBackStack()
                navController.navigate(R.id.keranjangFragment,null)
            }

            EnumListFragment.PROFILE->{
                navController.popBackStack()
                navController.navigate(R.id.profileFragment,null)
            }
            else -> println("Error No Fragment")
        }
    }

}