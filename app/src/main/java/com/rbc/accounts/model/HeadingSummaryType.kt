package com.rbc.accounts.model

import com.rbc.accounts.view.viewHolder.AccountSummaryViewHolderType

data class HeadingSummaryType(
    override val viewType: AccountSummaryViewHolderType,
    override val name: String
) : BaseAccountSummaryModel()
