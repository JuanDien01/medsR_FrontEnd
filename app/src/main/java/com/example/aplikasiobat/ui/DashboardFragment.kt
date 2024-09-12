package com.example.aplikasiobat.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasiobat.R
import com.example.aplikasiobat.adapter.ObatPasienAdapter
import com.example.aplikasiobat.api.response.dashboard.ObatPasien.Data
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import com.example.aplikasiobat.api.service.Status
import com.example.aplikasiobat.databinding.FragmentDashboardBinding
import com.example.aplikasiobat.viewmodel.DashboardViewModel
import com.example.aplikasiobat.viewmodel.MainViewModel
import com.example.aplikasiobat.viewmodel.MainViewModelFactory
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private val dashboardViewModel: DashboardViewModel by activityViewModels()
    private lateinit var semuaAdapter: ObatPasienAdapter
    private lateinit var belumDiminumAdapter: ObatPasienAdapter
    private lateinit var sudahDiminumAdapter: ObatPasienAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val apiHelper = ApiHelper(ApiClient.instance)
        val viewModelFactory = MainViewModelFactory(apiHelper)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        // Set greeting message based on current time
        val now = LocalDateTime.now()
        setTime(now.hour)

        return binding.root
    }

    private fun setTime(hours: Int) {
        binding.welcomMsg.text = when (hours) {
            in 0..6 -> "Selamat Malam 🌙"
            in 7..12 -> "Selamat Pagi ☀️"
            in 13..15 -> "Selamat Siang 🌞"
            in 16..19 -> "Selamat Sore 🌅"
            else -> "Selamat Malam 🌙"
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashboardViewModel.userId.observe(viewLifecycleOwner) { userId ->
            // Ensure that userId is valid before using it
            if (userId != null) {
                getObatPasienSemua(userId)
                setupChipListeners(userId)
            } else {
                Toast.makeText(context, "no data after login", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe fullName and update UI
        dashboardViewModel.fullName.observe(viewLifecycleOwner) { fullName ->
            binding.edtFullName.text = fullName
        }

        // Initial chip selection
        binding.chipSemua.isChecked = true

        setupRecyclerViews()
        binding.ivSettings.setOnClickListener {
            it.findNavController().navigate(R.id.action_dashboardFragment_to_settingsFragment)
        }
    }

    private fun setupRecyclerViews() {
        semuaAdapter = ObatPasienAdapter { obatPasien ->
            handleItemClick(obatPasien)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = semuaAdapter

        belumDiminumAdapter = ObatPasienAdapter { obatPasien ->
            handleItemClick(obatPasien)
        }

        sudahDiminumAdapter = ObatPasienAdapter { obatPasien ->
            handleItemClick(obatPasien)
        }
    }

    private fun handleItemClick(obatPasien: Data) {
        val highlightedHours = obatPasien.waktuMulaiMinumObat
        val action =
            DashboardFragmentDirections.actionDashboardFragmentToDetailReminderFragment(obatPasien,highlightedHours)
        findNavController().navigate(action)
    }

    private fun setupChipListeners(userId: Int) {
        binding.chipSemua.setOnClickListener { getObatPasienSemua(userId) }
        binding.chipBelumDiminum.setOnClickListener { getObatPasienBelumDiminum(userId) }
        binding.chipSudahDiminum.setOnClickListener { getObatPasienSudahDiminum(userId) }
    }

    private fun getObatPasienSemua(userId: Int) {
        mainViewModel.getObatPasien(userId).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.serverTerputus.visibility = View.GONE
                    val data = resource.data?.data
                    if (data.isNullOrEmpty()) {
                        binding.recyclerView.visibility = View.GONE
                        binding.noJadwal.visibility = View.VISIBLE
                    } else {
                        // Get the current time
                        val currentTime = LocalTime.now()

                        // Sort the data by the closest waktuMinumObat to the current time
                        val sortedData = data.sortedBy { item ->
                            val waktuMinumObat = LocalTime.parse(item.waktuMulaiMinumObat)
                            Duration.between(currentTime, waktuMinumObat).abs()
                        }

                        binding.recyclerView.visibility = View.VISIBLE
                        binding.noJadwal.visibility = View.GONE
                        semuaAdapter.submitList(sortedData)
                        binding.recyclerView.adapter = semuaAdapter
                    }
                    binding.loading.visibility = View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(context, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                    binding.loading.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.serverTerputus.visibility = View.VISIBLE
                }

                Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.noJadwal.visibility = View.GONE
                }
            }
        }
    }


    private fun getObatPasienBelumDiminum(userId: Int) {
        mainViewModel.getObatPasienBelumDiminum(userId).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    val data = resource.data?.data
                    if (data.isNullOrEmpty()) {
                        binding.recyclerView.visibility = View.GONE
                        binding.noJadwal.visibility = View.VISIBLE
                    } else {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.noJadwal.visibility = View.GONE
                        belumDiminumAdapter.submitList(data)
                        binding.recyclerView.adapter = belumDiminumAdapter
                    }
                    binding.loading.visibility = View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(context, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                    binding.loading.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.serverTerputus.visibility = View.VISIBLE
                }

                Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.noJadwal.visibility = View.GONE
                }
            }
        }
    }

    private fun getObatPasienSudahDiminum(userId: Int) {
        mainViewModel.getObatPasienSudahDiminum(userId).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    val data = resource.data?.data
                    if (data.isNullOrEmpty()) {
                        binding.recyclerView.visibility = View.GONE
                        binding.noJadwal.visibility = View.VISIBLE
                    } else {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.noJadwal.visibility = View.GONE
                        sudahDiminumAdapter.submitList(data)
                        binding.recyclerView.adapter = sudahDiminumAdapter
                    }
                    binding.loading.visibility = View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(context, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                    binding.loading.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.serverTerputus.visibility = View.VISIBLE
                }

                Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.noJadwal.visibility = View.GONE
                }
            }
        }
    }


//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}

