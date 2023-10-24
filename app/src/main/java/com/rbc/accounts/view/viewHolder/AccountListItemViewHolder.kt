package com.rbc.accounts.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.rbc.accounts.databinding.AccountInfoListItemBinding
import com.rbc.accounts.model.AccountInfoSummaryType
import com.rbc.accounts.util.AccountNumberTruncateHelper.truncate
import com.rbc.accounts.util.StringToCurrencyConverter

class AccountListItemViewHolder (private val binding: AccountInfoListItemBinding,
                                 private val onItemClicked: (AccountInfoSummaryType) -> Unit)
    : RecyclerView.ViewHolder(binding.root) {

        fun bind(accountInfo: AccountInfoSummaryType) {
            binding.root.setOnClickListener {
                onItemClicked(accountInfo)
            }
            binding.accountNameTextView.text = accountInfo.name
            binding.accountNumberTextView.text = accountInfo.number.truncate()
            binding.accountValueTextView.text =
                StringToCurrencyConverter.getInstance().format(accountInfo.balance)
        }
}