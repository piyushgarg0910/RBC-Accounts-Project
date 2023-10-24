package com.rbc.accounts.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.rbc.accounts.databinding.AccountDetailsTransactionListItemBinding
import com.rbc.accounts.model.AccountTransactionDetailType
import com.rbc.accounts.util.CalendarToDateStringConverter
import com.rbc.accounts.util.StringToCurrencyConverter

class AccountDetailsTransactionListItemViewHolder(private val binding: AccountDetailsTransactionListItemBinding)
    : RecyclerView.ViewHolder(binding.root){

    fun bind(transaction: AccountTransactionDetailType) {
        binding.transactionDateTextView.text =
            CalendarToDateStringConverter.getInstance().format(transaction.date.timeInMillis)
        binding.transactionDescriptionTextView.text = transaction.description
        binding.transactionValueTextView.text =
            StringToCurrencyConverter.getInstance().format(transaction.amount)
    }
}