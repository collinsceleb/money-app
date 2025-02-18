package com.collinsceleb.money_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.collinsceleb.money_app.databinding.ItemAccountBinding
import com.collinsceleb.money_app.model.Account

class AccountAdapter :
    ListAdapter<Account, AccountAdapter.AccountViewHolder>(AccountDiffCallback()) {

    class AccountViewHolder(private val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(account: Account) {
            binding.tvAccountNumber.text = account.accountNumber
            binding.tvAccountHolder.text = account.accountHolder
            binding.tvAccountType.text = account.accountType
            binding.tvBalance.text = String.format(account.accountBalance.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding =
            ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val currentAccount = getItem(position)
        holder.bind(currentAccount)
    }
}

class AccountDiffCallback : DiffUtil.ItemCallback<Account>() {
    override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem.accountNumber == newItem.accountNumber
    }

    override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem == newItem
    }
}