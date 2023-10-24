package com.rbc.accounts.model

import com.rbc.accounts.view.viewHolder.AccountDetailsViewHolderType
import java.util.Calendar

abstract class BaseAccountDetailsSummaryModel {
    abstract val date: String
    abstract val viewType: AccountDetailsViewHolderType
}