package com.example.aplikasiobat.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.aplikasiobat.R
import com.example.aplikasiobat.api.request.RegisterUserBiodataRequest
import com.example.aplikasiobat.api.response.register.userbiodata.RegisterUserBiodataResponse
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.databinding.FragmentRegisterBinding
import com.example.aplikasiobat.databinding.FragmentWelcomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.masuk.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnDaftar.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_biodataFragment)
        }
    }
}