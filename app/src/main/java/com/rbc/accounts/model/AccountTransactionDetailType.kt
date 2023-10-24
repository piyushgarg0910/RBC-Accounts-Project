package com.rbc.accounts.model

import com.rbc.accounts.view.viewHolder.AccountDetailsViewHolderType
import java.util.Calendar

data class AccountTransactionDetailType(
    override val date: String,
    override val viewType: AccountDetailsViewHolderType,
    val amount: String,
    val description: String
) : BaseAccountDetailsSummaryModel()