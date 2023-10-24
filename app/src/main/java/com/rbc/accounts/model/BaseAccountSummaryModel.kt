package com.rbc.accounts.model

import com.rbc.accounts.view.viewHolder.AccountViewHolderType

abstract class BaseAccountSummaryModel {
    abstract val viewType: AccountViewHolderType
    abstract val name: String
}


