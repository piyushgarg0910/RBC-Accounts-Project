package com.rbc.accounts.model

import com.rbc.accounts.view.viewHolder.AccountSummaryViewHolderType

data class AccountInfoSummaryType(
    override val viewType: AccountSummaryViewHolderType,
    override val name: String,
    val number: String,
    val balance: String,
    val type: Int
) : BaseAccountSummaryModel()