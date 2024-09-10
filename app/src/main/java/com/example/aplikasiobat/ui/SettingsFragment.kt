package com.example.aplikasiobat.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasiobat.R
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import com.example.aplikasiobat.api.service.Status
import com.example.aplikasiobat.databinding.FragmentSettingsBinding
import com.example.aplikasiobat.databinding.FragmentWelcomeBinding
import com.example.aplikasiobat.viewmodel.DashboardViewModel
import com.example.aplikasiobat.viewmodel.MainViewModel
import com.example.aplikasiobat.viewmodel.MainViewModelFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding?= null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        val apiHelper = ApiHelper(ApiClient.instance)
        val viewModelFactory = MainViewModelFactory(apiHelper)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardViewModel.idPengguna.observe(viewLifecycleOwner){idPengguna ->
            Log.d("idPengguna", "onViewCreated: $idPengguna")
            if (idPengguna != null){
                getUserBiodataById(idPengguna)
            }else{
                Toast.makeText(context, "no data idPengguna", Toast.LENGTH_SHORT).show()
            }
        }
//        getUserBiodataById()
    }
    private fun getUserBiodataById(idPengguna : Int){
        mainViewModel.getUserBiodataById(idPengguna).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    Toast.makeText(context, "Sukses ambil user biodata by id", Toast.LENGTH_SHORT).show()
                    val namaLengkap = resource.data?.data?.namaPengguna ?: "Tamu"
                    val noTelfon = resource.data?.data?.noTelfon ?: 0
                    val email = resource.data?.data?.email ?: "Tamu"
                    val tanggalLahir = resource.data?.data?.tanggalLahir ?: "Tamu"
                    val jenisKelamin = resource.data?.data?.jenisKelamin ?: "Tamu"
                    val alamat = resource.data?.data?.alamat ?: "Tamu"
                    binding.edtNamaPenggunaSettings.setText(namaLengkap)
                    binding.edtNoTelponSettings.setText(noTelfon.toString())
                    binding.edtEmailSettings.setText(email)
                    binding.edtTanggalInputSettings.setText(tanggalLahir)
                    binding.edtJenisKelaminSettings.setText(jenisKelamin)
                    binding.edtAlamatSettings.setText(alamat)

                    // Set up click listener to show DatePickerDialog
                    binding.edtTanggalInputSettings.setOnClickListener {
                        showDatePickerDialog(tanggalLahir)
                    }

//                    Log.d("biodata", "getUserBiodataById: $resource")
                }
                Status.ERROR -> {

                }
                Status.LOADING -> {

                }
            }

        }
    }
    private fun showDatePickerDialog(currentDate: String) {
        val calendar = Calendar.getInstance()

        // Date format to match "5 Nov 2001"
        val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())

        // If there's already a date set, parse it and set it to the calendar
        if (currentDate.isNotEmpty() && currentDate != "Tamu") {
            try {
                val parsedDate = dateFormat.parse(currentDate)
                parsedDate?.let {
                    calendar.time = it
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format the selected date to "5 Nov 2001"
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                val formattedDate = dateFormat.format(selectedCalendar.time)
                binding.edtTanggalInputSettings.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

}