package com.rbc.accounts.model

import com.rbc.accounts.view.viewHolder.AccountSummaryViewHolderType

abstract class BaseAccountSummaryModel {
    abstract val viewType: AccountSummaryViewHolderType
    abstract val name: String
}


