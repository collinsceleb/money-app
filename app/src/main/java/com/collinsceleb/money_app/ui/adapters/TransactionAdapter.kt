package com.collinsceleb.money_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.collinsceleb.money_app.databinding.ItemTransactionBinding
import com.collinsceleb.money_app.model.Transaction
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.tvSourceAccount.text = "From: ${transaction.sourceAccount}"
            binding.tvDestinationAccount.text = "To: ${transaction.destinationAccount}"
            binding.tvAmount.text = "Amount: ${transaction.amount}"
            binding.tvTimestamp.text = formatTimestamp(transaction.timestamp)

        }

        private fun formatTimestamp(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }
}