package com.rbc.accounts.result

import com.rbc.rbcaccountlibrary.Transaction

sealed class AccountAdditionalTransactionsResult {
    class Success(val transactions : List<Transaction>) : AccountAdditionalTransactionsResult()
    object Failure : AccountAdditionalTransactionsResult()
}