package com.example.challenge3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.challenge3.databinding.ActivityMainBinding
import com.example.challenge3.page.MenuFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val suppFragManager = supportFragmentManager.beginTransaction()
        val mainMenu = MenuFragment()
        suppFragManager.add(R.id.fragContainer,mainMenu).commit()
    }
}