package com.example.aplikasiobat.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasiobat.R
import com.example.aplikasiobat.api.response.dashboard.detailObatPasien.Data
import com.example.aplikasiobat.databinding.ItemJamBinding

class JamMinumObatAdapter(
    private val onItemClick: (Data) -> Unit
) : ListAdapter<Data, JamMinumObatAdapter.JamMinumObatViewHolder>(JamMinumDiffCallback()) {

    private var highlightedHours: String? = null

    fun setHighlightedHours(hours: String) {
        highlightedHours = hours
        notifyDataSetChanged() // Refresh the adapter to apply changes
    }

    inner class JamMinumObatViewHolder(val binding: ItemJamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Set the click listener on the root view of the item
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    // Update highlightedHours based on the clicked item
                    setHighlightedHours(item.waktuMulaiMinumObat)
                    // Call the onItemClick callback
                    onItemClick(item)
                }
            }
        }

        fun bind(detailObatPasienItem: Data) {
            binding.tvJam.text = detailObatPasienItem.waktuMulaiMinumObat

            // Set colors based on the 'sudahMinumObat' status
            when (detailObatPasienItem.sudahMinumObat) {
                "true" -> {
                    binding.cardJam.strokeColor = ContextCompat.getColor(binding.root.context, R.color.success_border)
                    binding.cardJam.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.success_border))
                    binding.tvJam.setTextColor(ContextCompat.getColor(binding.root.context, R.color.success_main))
                }
                "false" -> {
                    binding.cardJam.strokeColor = ContextCompat.getColor(binding.root.context, R.color.danger_border)
                    binding.cardJam.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.danger_border))
                    binding.tvJam.setTextColor(ContextCompat.getColor(binding.root.context, R.color.danger_main))
                }
                null -> {
                    binding.cardJam.strokeColor = ContextCompat.getColor(binding.root.context, R.color.neutral100)
                    binding.cardJam.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.neutral10))
                    binding.tvJam.setTextColor(ContextCompat.getColor(binding.root.context, R.color.neutral100))
                }
            }

            // Change card elevation based on whether the item is highlighted
            if (highlightedHours != null && detailObatPasienItem.waktuMulaiMinumObat == highlightedHours) {
                binding.cardJam.cardElevation = 24f // Set elevation for active state
            } else {
                binding.cardJam.cardElevation = 2f // Set default elevation
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JamMinumObatViewHolder {
        val binding = ItemJamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JamMinumObatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JamMinumObatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}







