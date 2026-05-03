package com.uzuu.admin.feature.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uzuu.admin.databinding.ItemScanHistoryBinding
import com.uzuu.admin.domain.model.ScanHistory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ScanHistoryAdapter : ListAdapter<ScanHistory, ScanHistoryAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemScanHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemScanHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScanHistory) {
            binding.apply {
                txtTicketCode.text = "Mã vé: ${item.ticketCode}"
                txtTicketName.text = "Vé: ${item.ticketName ?: "N/A"}"
                txtGuestName.text = "Khách: ${item.guestName ?: "N/A"}"
                txtEventName.text = "Sự kiện: ${item.eventName ?: "N/A"}"
                txtTime.text = formatTime(item.checkInTime)
                txtStatus.text = when (item.status) {
                    "SUCCESS" -> "✓ Thành công"
                    "FAILED" -> "✗ Thất bại"
                    else -> item.status
                }
                txtStatus.setTextColor(
                    if (item.status == "SUCCESS") {
                        binding.root.context.getColor(android.R.color.holo_green_dark)
                    } else {
                        binding.root.context.getColor(android.R.color.holo_red_dark)
                    }
                )
                if (!item.message.isNullOrBlank()) {
                    txtMessage.text = item.message
                    txtMessage.visibility = android.view.View.VISIBLE
                } else {
                    txtMessage.visibility = android.view.View.GONE
                }
            }
        }

        private fun formatTime(timestamp: Long): String {
            val date = Date(timestamp)
            val format = SimpleDateFormat("HH:mm:ss - dd/MM/yyyy", Locale("vi"))
            return format.format(date)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ScanHistory>() {
        override fun areItemsTheSame(oldItem: ScanHistory, newItem: ScanHistory) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ScanHistory, newItem: ScanHistory) =
            oldItem == newItem
    }
}
