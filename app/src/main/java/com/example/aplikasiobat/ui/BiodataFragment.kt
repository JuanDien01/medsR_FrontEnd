package com.example.aplikasiobat.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.aplikasiobat.R
import com.example.aplikasiobat.databinding.FragmentBiodataBinding
import com.example.aplikasiobat.databinding.FragmentRegisterBinding
import com.google.android.material.datepicker.MaterialDatePicker


class BiodataFragment : Fragment() {

    private var _binding: FragmentBiodataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBiodataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .build()
        binding.edtTanggalInput.setOnClickListener {
            datePicker.show(parentFragmentManager, "DATE_PICKER")
        }
        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate = datePicker.headerText
            binding.edtTanggalInput.setText(selectedDate)
        }
        binding.btnDaftar.setOnClickListener {
            it.findNavController().navigate(R.id.action_biodataFragment_to_loginFragment)
        }
    }
}


