package com.collinsceleb.money_app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.collinsceleb.money_app.R
import com.collinsceleb.money_app.model.Account

class AccountAdapter :
    ListAdapter<Account, AccountAdapter.AccountViewHolder>(AccountDiffCallback()) {

    class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val accountNumberTextView: TextView = itemView.findViewById(R.id.tvAccountNumber)
        private val accountNameTextView: TextView = itemView.findViewById(R.id.tvAccountHolder)
        private val accountTypeTextView: TextView = itemView.findViewById(R.id.tvAccountType)
        private val accountBalanceTextView: TextView = itemView.findViewById(R.id.tvBalance)

        fun bind(account: Account) {
            accountNumberTextView.text = account.accountNumber
            accountNameTextView.text = account.accountHolder
            accountTypeTextView.text = account.accountType
            accountBalanceTextView.text = String.format(account.accountBalance.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false)
        return AccountViewHolder(itemView)
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