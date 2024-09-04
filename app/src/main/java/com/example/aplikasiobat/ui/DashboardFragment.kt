package com.example.aplikasiobat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasiobat.R
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

        setupChipListeners(userId)
        getObatPasienSemua(userId)
    }

    private fun setupChipListeners(userId: Int) {
        binding.chipSemua.setOnClickListener { getObatPasienSemua(userId) }
        binding.chipBelumDiminum.setOnClickListener { getObatPasienBelumDiminum(userId) }
        binding.chipSudahDiminum.setOnClickListener { getObatPasienSudahDiminum(userId) }
    }

    private fun getObatPasienSemua(userId: Int) {
        fetchData(
            call = { mainViewModel.getObatPasien(userId) },
            fragmentClass = SemuaFragment::class.java,
            dataKey = "obatPasienData"
        )
    }

    private fun getObatPasienBelumDiminum(userId: Int) {
        fetchData(
            call = { mainViewModel.getObatPasienBelumDiminum(userId) },
            fragmentClass = BelumDiminumFragment::class.java,
            dataKey = "obatPasienDataBelumDiminum"
        )
    }

    private fun getObatPasienSudahDiminum(userId: Int) {
        fetchData(
            call = { mainViewModel.getObatPasienSudahDiminum(userId) },
            fragmentClass = SudahDiminumFragment::class.java,
            dataKey = "obatPasienDataSudahDiminum"
        )
    }

    private fun fetchData(
        call: () -> LiveData<Resource<GetObatPasienResponse>>,
        fragmentClass: Class<out Fragment>,
        dataKey: String
    ) {
        call().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.loading.visibility = View.GONE
                    binding.fragmentContainer.visibility = View.VISIBLE
                    resource.data?.let { data ->
                        val bundle = Bundle().apply {
                            putString(dataKey, Gson().toJson(data))
                        }
                        val fragment = fragmentClass.newInstance().apply {
                            arguments = bundle
                        }

                        // Begin the transaction
                        childFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                android.R.anim.fade_in,
                                android.R.anim.fade_out,
                                android.R.anim.fade_in,
                                android.R.anim.fade_out
                            )
                            .replace(R.id.fragment_container, fragment)
                            .commit()
                    }
                }

                Status.ERROR -> {
                    Toast.makeText(context, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                    binding.loading.visibility = View.GONE
                }

                Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.fragmentContainer.visibility = View.GONE
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
