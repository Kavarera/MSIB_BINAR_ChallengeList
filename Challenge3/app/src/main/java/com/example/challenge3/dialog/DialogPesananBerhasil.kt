package com.example.challenge3.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.challenge3.R
import com.example.challenge3.databinding.FragmentDialogPesananBerhasilBinding
import com.example.challenge3.databinding.FragmentKonfirmasiPesananBinding


class DialogPesananBerhasil : DialogFragment() {
    private lateinit var binding : FragmentDialogPesananBerhasilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Style untuk dialog
        setStyle(STYLE_NO_FRAME, android.R.style.ThemeOverlay_Material_Dialog_Alert)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDialogPesananBerhasilBinding.inflate(inflater,container,false)
        binding.btnKembaliKeHome.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.keranjangFragment,null)
            dismiss()
        }
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            // Set style untuk dialog
            setStyle(STYLE_NO_FRAME, android.R.style.ThemeOverlay_Material_Dialog_Alert)
        }
    }




}