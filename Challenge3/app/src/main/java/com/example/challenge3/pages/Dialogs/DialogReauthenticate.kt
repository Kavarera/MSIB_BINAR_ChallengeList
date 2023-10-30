package com.example.challenge3.pages.Dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.challenge3.databinding.FragmentDialogReauthenticateBinding
import com.example.challenge3.models.interfaces.DialogReauthenticateListener


class DialogReauthenticate : DialogFragment() {

    private lateinit var binding:FragmentDialogReauthenticateBinding
    private var listener:DialogReauthenticateListener?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME,android.R.style.ThemeOverlay_Material_Dialog_Alert)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogReauthenticateBinding.inflate(
            inflater,
            container,
            false
        )

        binding.btnConfirm.setOnClickListener {
            listener?.onDataReceived(
                binding.etPasswordReauthenticate.text.toString()
            )
            dismiss()
        }




        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setStyle(STYLE_NO_FRAME, android.R.style.ThemeOverlay_Material_Dialog_Alert)
        }
    }

    fun setListener(listener: DialogReauthenticateListener){
        this.listener=listener
    }

}