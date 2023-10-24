package com.rbc.accounts.model

import com.rbc.accounts.view.viewHolder.AccountDetailsViewHolderType
import java.util.Calendar

data class AccountTransactionDateType(
    override val date: String,
    override val viewType: AccountDetailsViewHolderType
) : BaseAccountDetailsSummaryModel()