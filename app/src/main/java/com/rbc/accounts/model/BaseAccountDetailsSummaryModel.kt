package com.rbc.accounts.model

import com.rbc.accounts.view.viewHolder.AccountDetailsViewHolderType
import java.util.Calendar

abstract class BaseAccountDetailsSummaryModel {
    abstract val date: Calendar
    abstract val viewType: AccountDetailsViewHolderType
}