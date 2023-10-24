package com.rbc.accounts.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rbc.accounts.databinding.AccountDetailsDateListItemBinding
import com.rbc.accounts.databinding.AccountDetailsTransactionListItemBinding
import com.rbc.accounts.model.AccountTransactionDateType
import com.rbc.accounts.model.AccountTransactionDetailType
import com.rbc.accounts.model.BaseAccountDetailsSummaryModel
import com.rbc.accounts.util.diffutil.TransactionDiffUtilCallback
import com.rbc.accounts.view.viewHolder.AccountDetailsDateListItemViewHolder
import com.rbc.accounts.view.viewHolder.AccountDetailsTransactionListItemViewHolder
import com.rbc.accounts.view.viewHolder.AccountDetailsViewHolderType

class AccountDetailsAdapter
    : ListAdapter<BaseAccountDetailsSummaryModel, RecyclerView.ViewHolder>(TransactionDiffUtilCallback())
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when(viewType) {
            AccountDetailsViewHolderType.DATE.type -> {
                val binding = AccountDetailsDateListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                AccountDetailsDateListItemViewHolder(binding)
            }

            else -> {
                val binding = AccountDetailsTransactionListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                AccountDetailsTransactionListItemViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItem(position).viewType) {
            AccountDetailsViewHolderType.DATE -> {
                (holder as AccountDetailsDateListItemViewHolder)
                    .bind(getItem(position) as AccountTransactionDateType)
            }

            AccountDetailsViewHolderType.TRANSACTION -> {
                (holder as AccountDetailsTransactionListItemViewHolder)
                    .bind(getItem(position) as AccountTransactionDetailType)
            }
        }
    }

    override fun getItemViewType(position: Int) = getItem(position).viewType.type
}