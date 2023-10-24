package com.rbc.accounts.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rbc.accounts.databinding.AccountInfoListItemBinding
import com.rbc.accounts.databinding.HeadingListItemBinding
import com.rbc.accounts.model.AccountInfoSummaryType
import com.rbc.accounts.model.BaseAccountSummaryModel
import com.rbc.accounts.model.HeadingSummaryType
import com.rbc.accounts.view.viewHolder.AccountListItemViewHolder
import com.rbc.accounts.view.viewHolder.AccountSummaryViewHolderType
import com.rbc.accounts.view.viewHolder.HeadingListItemViewHolder

class AccountListAdapter(private val onItemClicked: (AccountInfoSummaryType) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val accountList = ArrayList<BaseAccountSummaryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            AccountSummaryViewHolderType.HEADING.type -> {
                val binding = HeadingListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeadingListItemViewHolder(binding)
            }

            else -> {
                val binding = AccountInfoListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                AccountListItemViewHolder(binding, onItemClicked)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(accountList[position].viewType) {
            AccountSummaryViewHolderType.HEADING -> {
                (holder as HeadingListItemViewHolder)
                    .bind(accountList[position] as HeadingSummaryType)
            }

            AccountSummaryViewHolderType.ACCOUNT -> {
                (holder as AccountListItemViewHolder)
                    .bind(accountList[position] as AccountInfoSummaryType)
            }
        }
    }

    override fun getItemViewType(position: Int) = accountList[position].viewType.type

    override fun getItemCount() = accountList.size

    fun updateAdapter(accounts: List<BaseAccountSummaryModel>) {
        accountList.clear()
        accountList.addAll(accounts)
        notifyItemInserted(0)
    }
}