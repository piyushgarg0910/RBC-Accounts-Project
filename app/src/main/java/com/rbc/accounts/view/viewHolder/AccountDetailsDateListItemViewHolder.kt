package com.rbc.accounts.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.rbc.accounts.databinding.AccountDetailsDateListItemBinding
import com.rbc.accounts.model.AccountTransactionDateType
import com.rbc.accounts.util.CalendarToDateStringConverter

class AccountDetailsDateListItemViewHolder(private val binding: AccountDetailsDateListItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

        fun bind(dateItem: AccountTransactionDateType) {
            binding.accountTransactionDateTextView.text = dateItem.date
        }
}