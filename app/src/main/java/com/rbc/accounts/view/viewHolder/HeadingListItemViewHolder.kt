package com.rbc.accounts.view.viewHolder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.rbc.accounts.R
import com.rbc.accounts.databinding.HeadingListItemBinding
import com.rbc.accounts.model.HeadingSummaryType
import com.rbc.rbcaccountlibrary.AccountType

class HeadingListItemViewHolder(private val binding: HeadingListItemBinding)
    : ViewHolder(binding.root) {

        fun bind(heading: HeadingSummaryType) {
            binding.accountTypeHeadingTextView.text =
                when(heading.name) {
                    AccountType.CHEQUING.name -> {
                        ContextCompat.getString(binding.root.context, R.string.chequing_account_type)
                    }
                    AccountType.CREDIT_CARD.name -> {
                        ContextCompat.getString(binding.root.context, R.string.credit_card_account_type)
                    }
                    AccountType.LOAN.name -> {
                        ContextCompat.getString(binding.root.context, R.string.loan_account_type)
                    }
                    AccountType.MORTGAGE.name -> {
                        ContextCompat.getString(binding.root.context, R.string.mortgage_account_type)
                    }
                    else -> { null }
                }
        }
}