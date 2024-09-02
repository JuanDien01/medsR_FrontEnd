package com.example.aplikasiobat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasiobat.R
import com.example.aplikasiobat.api.response.dashboard.Data
import com.example.aplikasiobat.databinding.ItemObatBinding

class ObatPasienAdapter(private val obatPasienList: List<Data>) :
    RecyclerView.Adapter<ObatPasienAdapter.ObatPasienViewHolder>() {

    inner class ObatPasienViewHolder(val binding: ItemObatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(obatPasienItem: Data) {
            binding.namaObat.text = obatPasienItem.namaObat
            binding.dosisObat.text = obatPasienItem.dosisObat
            binding.aturanObat.text = obatPasienItem.waktuMinumObat

            when (obatPasienItem.sudahMinumObat) {
                "true" -> {
                    binding.cardColor.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.success_border))
                    binding.aturanObat.setBackgroundResource(R.drawable.green_rounded_background)
                    binding.aturanObat.setTextColor(ContextCompat.getColor(binding.root.context, R.color.neutral10))
                    binding.namaObat.setTextColor(ContextCompat.getColor(binding.root.context, R.color.success_main))
                    binding.dosisObat.setTextColor(ContextCompat.getColor(binding.root.context, R.color.success_main))
                    binding.card.strokeColor = ContextCompat.getColor(binding.root.context, R.color.success_border)
                }
                "false" -> {
                    binding.cardColor.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.danger_border))
                    binding.aturanObat.setBackgroundResource(R.drawable.red_rounded_background)
                    binding.aturanObat.setTextColor(ContextCompat.getColor(binding.root.context, R.color.neutral10))
                    binding.namaObat.setTextColor(ContextCompat.getColor(binding.root.context, R.color.neutral100))
                    binding.dosisObat.setTextColor(ContextCompat.getColor(binding.root.context, R.color.neutral90))
                    binding.card.strokeColor = ContextCompat.getColor(binding.root.context, R.color.danger_border)
                }
                null ->{
                    binding.cardColor.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.neutral10))
                    binding.aturanObat.setBackgroundResource(R.drawable.neutral_rounded_background)
                    binding.aturanObat.setTextColor(ContextCompat.getColor(binding.root.context, R.color.neutral100))
                    binding.namaObat.setTextColor(ContextCompat.getColor(binding.root.context, R.color.neutral100))
                    binding.dosisObat.setTextColor(ContextCompat.getColor(binding.root.context, R.color.neutral90))
                }
            }
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
