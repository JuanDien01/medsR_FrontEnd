package com.example.aplikasiobat.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.aplikasiobat.R
import com.example.aplikasiobat.api.data.Biodata
import com.example.aplikasiobat.databinding.FragmentBiodataBinding
import com.example.aplikasiobat.databinding.FragmentRegisterBinding
import com.example.aplikasiobat.viewmodel.AuthViewModel
import com.google.android.material.datepicker.MaterialDatePicker


class BiodataFragment : Fragment() {

    private var _binding: FragmentBiodataBinding? = null
    private val binding get() = _binding!!

    private val authViewModel:AuthViewModel by activityViewModels()

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
            authViewModel.setData(setData())
            it.findNavController().navigate(R.id.action_biodataFragment_to_registerFragment)
        }
    }
    private fun setData():Biodata{
        val namaPengguna = binding.edtNamaPengguna.editText!!.text
        val tanggalLahir = binding.edtTanggal.editText!!.text
        val jenisKelamin = binding.edtJenisKelamin.editText!!.text
        val alamat = binding.edtAlamat.editText!!.text
        val noTelf = binding.edtNoTelpon.editText!!.text
        val email = binding.edtEmail.editText!!.text
        val newBiodata = Biodata(
            nama_pengguna = namaPengguna.toString(),
            tanggal_lahir = tanggalLahir.toString(),
            jenis_kelamin = jenisKelamin.toString(),
            alamat = alamat.toString(),
            no_telfon = noTelf.toString(),
            email = email.toString()
        )
        return newBiodata
    }

}


