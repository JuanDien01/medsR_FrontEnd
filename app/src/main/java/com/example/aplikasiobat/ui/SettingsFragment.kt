package com.example.aplikasiobat.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aplikasiobat.R
import com.example.aplikasiobat.api.request.UpdateBiodataRequest
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import com.example.aplikasiobat.api.service.Status
import com.example.aplikasiobat.databinding.FragmentSettingsBinding
import com.example.aplikasiobat.viewmodel.DashboardViewModel
import com.example.aplikasiobat.viewmodel.MainViewModel
import com.example.aplikasiobat.viewmodel.MainViewModelFactory
import com.google.android.material.button.MaterialButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val apiHelper = ApiHelper(ApiClient.instance)
        val viewModelFactory = MainViewModelFactory(apiHelper)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Find the Toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        // Set the Toolbar as the support action bar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // Set the custom title
        (activity as AppCompatActivity).supportActionBar?.title = "Pengaturan Akun"

        // Enable back button
        val navController = findNavController()
        toolbar.setupWithNavController(navController)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        requireActivity().addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_toolbar, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_ubah_sandi -> {
                            val bundle = Bundle().apply {
                                putString("username", username)
                            }
                            findNavController().navigate(R.id.action_settingsFragment_to_updatePasswordFragment, bundle)
                            true
                        }

                        R.id.action_logout -> {
                            showLogoutConfirmationDialog()
                            true
                        }

                        else -> false
                    }
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )

        dashboardViewModel.idPengguna.observe(viewLifecycleOwner) { idPengguna ->
            Log.d("idPengguna", "onViewCreated: $idPengguna")
            if (idPengguna != null) {
                getUserBiodataById(idPengguna)
            } else {
                Toast.makeText(context, "no data idPengguna", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun showLogoutConfirmationDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.logout_pop_up, null)
        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)

        val dialog = builder.create()

        dialogView.findViewById<MaterialButton>(R.id.btnBatal).setOnClickListener {
            dialog.dismiss() // Dismiss the dialog
        }

        dialogView.findViewById<MaterialButton>(R.id.btnKeluar).setOnClickListener {
            // Perform logout operation
            dashboardViewModel.clearUserData()
            findNavController().navigate(R.id.welcomeFragment)
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun getUserBiodataById(idPengguna: Int) {
        mainViewModel.getUserBiodataById(idPengguna).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    val namaLengkap = resource.data?.data?.namaPengguna ?: "Tamu"
                    username = resource.data?.data?.userName?:"Tamu"
                    val noTelfon = resource.data?.data?.noTelfon ?: 0
                    val email = resource.data?.data?.email ?: "Tamu"
                    val tanggalLahir = resource.data?.data?.tanggalLahir ?: "Tamu"
                    val jenisKelamin = resource.data?.data?.jenisKelamin ?: "Tamu"
                    val alamat = resource.data?.data?.alamat ?: "Tamu"
                    binding.edtNamaPenggunaSettings.setText(namaLengkap)
                    binding.edtUsernameSettigs.setText(username)
                    binding.edtNoTelponSettings.setText(noTelfon.toString())
                    binding.edtEmailSettings.setText(email)
                    binding.edtTanggalInputSettings.setText(tanggalLahir)
                    binding.edtJenisKelaminSettings.setText(jenisKelamin)
                    binding.edtAlamatSettings.setText(alamat)

                    // Set up click listener to show DatePickerDialog
                    binding.edtTanggalInputSettings.setOnClickListener {
                        showDatePickerDialog(tanggalLahir)
                    }

                    binding.btnSimpanSettings.setOnClickListener {
                        val requestUpdate = UpdateBiodataRequest(
                            id = idPengguna,
                            alamat = binding.edtAlamatSettings.text.toString(),
                            email = binding.edtEmailSettings.text.toString(),
                            jenisKelamin = binding.edtJenisKelaminSettings.text.toString(),
                            namaPengguna = binding.edtNamaPenggunaSettings.text.toString(),
                            nomorTelfon = binding.edtNoTelponSettings.text.toString(),
                            tanggalLahir = binding.edtTanggalInputSettings.text.toString()
                        )
                        updateUserBiodata(requestUpdate)
                    }
                }

                Status.ERROR -> {

                }

                Status.LOADING -> {

                }
            }

        }
    }
    private fun updateUserBiodata(userBiodataRequest: UpdateBiodataRequest){
        mainViewModel.updateUserBiodata(userBiodataRequest).observe(viewLifecycleOwner){ resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    Toast.makeText(context, "Sukses update user biodata", Toast.LENGTH_SHORT)
                        .show()
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