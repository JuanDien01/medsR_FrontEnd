package com.example.aplikasiobat.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.aplikasiobat.R
import com.example.aplikasiobat.databinding.FragmentConfirmNewPasswordSuccessBinding
import com.example.aplikasiobat.databinding.FragmentForgotPasswordEmailBinding

class ConfirmNewPasswordSuccessFragment : Fragment() {
    private var _binding: FragmentConfirmNewPasswordSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConfirmNewPasswordSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLanjutKeHalamanLogin.setOnClickListener {
            findNavController().navigate(R.id.action_confirmNewPasswordSuccessFragment_to_loginFragment)
        }
    }


}