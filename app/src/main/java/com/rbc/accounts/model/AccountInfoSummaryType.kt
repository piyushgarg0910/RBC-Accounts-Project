package com.rbc.accounts.model

import com.rbc.accounts.view.viewHolder.AccountViewHolderType

data class AccountInfoSummaryType(
    override val viewType: AccountViewHolderType,
    override val name: String,
    val number: String,
    val balance: String,
    val type: Int
) : BaseAccountSummaryModel()