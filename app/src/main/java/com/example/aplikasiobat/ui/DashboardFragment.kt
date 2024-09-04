package com.example.aplikasiobat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasiobat.R
import com.example.aplikasiobat.adapter.ObatPasienAdapter
import com.example.aplikasiobat.api.response.dashboard.GetObatPasienResponse
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import com.example.aplikasiobat.api.service.Resource
import com.example.aplikasiobat.api.service.Status
import com.example.aplikasiobat.databinding.FragmentDashboardBinding
import com.example.aplikasiobat.viewmodel.MainViewModel
import com.example.aplikasiobat.viewmodel.MainViewModelFactory
import com.google.gson.Gson
import java.time.LocalDateTime


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
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
            in 0..6 -> "Selamat Malam"
            in 7..12 -> "Selamat Pagi"
            in 13..15 -> "Selamat Siang"
            in 16..19 -> "Selamat Sore"
            else -> "Selamat Malam"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getInt("userId") ?: return
        val fullName = arguments?.getString("Fullname") ?: "Tamu"

        binding.edtFullName.text = fullName
        binding.chipSemua.isChecked = true

        setupRecyclerViews()
        setupChipListeners(userId)
        getObatPasienSemua(userId)
    }

    private fun setupRecyclerViews() {
        // Setup adapter for Semua
        semuaAdapter = ObatPasienAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = semuaAdapter

        // Setup adapter for Belum Diminum
        belumDiminumAdapter = ObatPasienAdapter()

        // Setup adapter for Sudah Diminum
        sudahDiminumAdapter = ObatPasienAdapter()
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
                    val data = resource.data?.data
                    if (data.isNullOrEmpty()) {
                        binding.recyclerView.visibility = View.GONE
                        binding.noJadwal.visibility = View.VISIBLE
                    } else {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.noJadwal.visibility = View.GONE
                        semuaAdapter.submitList(data)
                        binding.recyclerView.adapter = semuaAdapter
                    }
                    binding.loading.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(context, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                    binding.loading.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
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
                }
                Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.noJadwal.visibility = View.GONE
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

