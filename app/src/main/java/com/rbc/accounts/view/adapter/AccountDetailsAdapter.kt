package com.rbc.accounts.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.rbc.accounts.databinding.AccountDetailsListItemBinding
import com.rbc.accounts.util.diffutil.TransactionDiffUtilCallback
import com.rbc.accounts.view.viewHolder.AccountDetailsListItemViewHolder
import com.rbc.rbcaccountlibrary.Transaction

class AccountDetailsAdapter
    : ListAdapter<Transaction, AccountDetailsListItemViewHolder>(TransactionDiffUtilCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccountDetailsListItemViewHolder {
        val binding = AccountDetailsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return AccountDetailsListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountDetailsListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}