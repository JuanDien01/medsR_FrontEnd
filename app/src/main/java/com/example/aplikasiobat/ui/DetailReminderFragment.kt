package com.example.aplikasiobat.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasiobat.R
import com.example.aplikasiobat.adapter.JamMinumObatAdapter
import com.example.aplikasiobat.api.request.DetailObatPasienRequest
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import com.example.aplikasiobat.api.service.Status
import com.example.aplikasiobat.databinding.FragmentDetailReminderBinding
import com.example.aplikasiobat.viewmodel.MainViewModel
import com.example.aplikasiobat.viewmodel.MainViewModelFactory


class DetailReminderFragment : Fragment() {

    private var _binding: FragmentDetailReminderBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: JamMinumObatAdapter

    private val args: DetailReminderFragmentArgs by navArgs()
    private var idObatPasien:Int ?= null
    private var highlightedHours: String?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailReminderBinding.inflate(inflater, container, false)
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
        (activity as AppCompatActivity).supportActionBar?.title = "Detail Reminder"

        // Enable back button
        val navController = findNavController()
        toolbar.setupWithNavController(navController)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val obatPasien = args.dataDetail
        highlightedHours = args.highlightedHours
        Log.d("asdadada", "onViewCreated: $highlightedHours")
        if (obatPasien.sudahMinumObat == "true") {
            binding.sudahMinumBtn.isEnabled = false
        }
        idObatPasien = obatPasien.idObatPasien
        setupRecyclerView()
//        Log.d("detailData", "onCreateView: $obatPasien")
        binding.tvNamaObatDetailReminder.text = obatPasien.namaObat
        binding.tvDosisObatDetailReminder.text = obatPasien.dosisObat
        binding.tvAturanPenggunaanObat.text = obatPasien.aturanPenggunaanObat
        binding.tvTanggalPemberianObat.text = obatPasien.tanggalDiberikan
        binding.catatan.text = obatPasien.catatan
        val request = DetailObatPasienRequest(obatPasien.idObat, obatPasien.idUser)
        binding.sudahMinumBtn.setOnClickListener {
            updateSudahMinum(idObatPasien!!)
            findNavController().navigate(R.id.action_detailReminderFragment_to_dashboardFragment)
        }
        getDetailObatPasien(request)

    }

    private fun setupRecyclerView() {
        adapter = JamMinumObatAdapter {
            buttonStateByHours(it.sudahMinumObat,it.idObatPasien)
            Log.d("currHours", "setupRecyclerView: $idObatPasien")
        }

        binding.rvJamMinumObat.adapter = adapter

        binding.rvJamMinumObat.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvJamMinumObat.adapter = adapter

        adapter.setHighlightedHours(highlightedHours!!)
    }

    private fun getDetailObatPasien(request: DetailObatPasienRequest) {
        mainViewModel.getObatPasienByUserIdAndObatId(request)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        val sortedList = resource.data?.data?.sortedBy {
                            // Assuming waktuMulaiMinumObat is in the format "HH:mm"
                            // Adjust the parsing if necessary
                            val timeParts =
                                it.waktuMulaiMinumObat.split(":")?.map { it.toInt() } ?: listOf(
                                    0,
                                    0
                                )
                            timeParts[0] * 60 + timeParts[1] // Convert hours and minutes to total minutes
                        }
                        adapter.submitList(sortedList)
                        val hasTakenMedicine =
                            sortedList?.all { it.sudahMinumObat == "true" } ?: false
                        if (hasTakenMedicine) {
                            binding.sudahMinum.visibility =
                                View.VISIBLE // Show view if any medicine has been taken
                        } else {
                            binding.sudahMinum.visibility =
                                View.GONE // Hide view if no medicine has been taken
                        }

                        // Example of handling specific `sudahMinumObat` values
                        if (sortedList?.any { it.sudahMinumObat == "false" } == true) {
                            binding.telatMinum.visibility =
                                View.VISIBLE // Show view if all medicines have not been taken
                        } else {
                            binding.telatMinum.visibility = View.GONE // Hide view otherwise
                        }
                    }

                    Status.ERROR -> {

                    }

                    Status.LOADING -> {

                    }
                }
            }
    }

    private fun updateSudahMinum(idObatPasien: Int) {
        mainViewModel.updateSudahMinum(idObatPasien,"true").observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    Toast.makeText(context, "Semoga Lekas Sembuh !", Toast.LENGTH_SHORT).show()
                }

                Status.ERROR -> {
                    Toast.makeText(context, "Gagal! Harap Coba Sebentar Lagi!", Toast.LENGTH_SHORT)
                        .show()
                }

                Status.LOADING -> {

                }
            }
        }
    }

    private fun buttonStateByHours(status: String?, idObatPasien: Int) {
        when (status) {
            "true" -> {
                binding.sudahMinumBtn.isEnabled = false
                this.idObatPasien = idObatPasien
            }

            "false" -> {
                binding.sudahMinumBtn.isEnabled = true
                this.idObatPasien = idObatPasien
            }

            null -> {
                binding.sudahMinumBtn.isEnabled = true
                this.idObatPasien = idObatPasien
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}