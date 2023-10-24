package com.rbc.accounts.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.rbc.accounts.databinding.AccountDetailsListItemBinding
import com.rbc.accounts.util.CalendarToDateStringConverter
import com.rbc.accounts.util.StringToCurrencyConverter
import com.rbc.rbcaccountlibrary.Transaction

class AccountDetailsListItemViewHolder(private val binding: AccountDetailsListItemBinding)
    : RecyclerView.ViewHolder(binding.root){

    fun bind(transaction: Transaction) {
        binding.transactionDateTextView.text =
            CalendarToDateStringConverter.getInstance().format(transaction.date.timeInMillis)
        binding.transactionDescriptionTextView.text = transaction.description
        binding.transactionValueTextView.text =
            StringToCurrencyConverter.getInstance().format(transaction.amount)
    }
}