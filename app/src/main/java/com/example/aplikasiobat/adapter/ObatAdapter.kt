package com.example.aplikasiobat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasiobat.api.response.dashboard.Data
import com.example.aplikasiobat.databinding.ItemObatBinding

class ObatPasienAdapter(private val obatPasienList: List<Data>) :
    RecyclerView.Adapter<ObatPasienAdapter.ObatPasienViewHolder>() {

    inner class ObatPasienViewHolder(val binding: ItemObatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(obatPasienItem: Data) {
            binding.namaObat.text = obatPasienItem.namaObat
            binding.dosisObat.text = obatPasienItem.dosisObat
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObatPasienViewHolder {
        val binding = ItemObatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ObatPasienViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ObatPasienViewHolder, position: Int) {
        holder.bind(obatPasienList[position])
    }

    override fun getItemCount(): Int = obatPasienList.size
}
