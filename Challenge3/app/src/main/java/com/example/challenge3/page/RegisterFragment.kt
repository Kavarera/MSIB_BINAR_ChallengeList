package com.example.challenge3.page

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.challenge3.R
import com.example.challenge3.databinding.FragmentKeranjangBinding
import com.example.challenge3.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding:FragmentRegisterBinding


    private var isPasswordVisible=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        //for clickable icon
        binding.etPasswordRegister.compoundDrawablesRelative[2].let {
            it.setBounds(0,0,it.intrinsicWidth,it.intrinsicHeight)
            binding.etPasswordRegister.setOnTouchListener{ _,event->
                if(event.action == MotionEvent.ACTION_UP &&
                    event.rawX >= binding
                        .etPasswordRegister.right-(it.bounds.width()+25)){
                    if(!isPasswordVisible){
                        binding.etPasswordRegister
                            .inputType=InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        isPasswordVisible = true
                        return@setOnTouchListener true
                    }
                    isPasswordVisible=false
                    binding.etPasswordRegister
                        .inputType= InputType
                        .TYPE_TEXT_VARIATION_PASSWORD or InputType
                        .TYPE_CLASS_TEXT
                    return@setOnTouchListener true
                }
                false
            }
        }




        return binding.root
    }
}