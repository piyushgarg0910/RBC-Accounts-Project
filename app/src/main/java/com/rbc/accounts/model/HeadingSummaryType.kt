package com.rbc.accounts.model

import com.rbc.accounts.view.viewHolder.AccountViewHolderType

data class HeadingSummaryType(
    override val viewType: AccountViewHolderType,
    override val name: String
) : BaseAccountSummaryModel()
