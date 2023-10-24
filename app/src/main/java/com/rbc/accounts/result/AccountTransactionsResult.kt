package com.rbc.accounts.result

import com.rbc.rbcaccountlibrary.Transaction

sealed class AccountTransactionsResult {
    class Success (val transactions: List<Transaction>): AccountTransactionsResult()
    object Failure : AccountTransactionsResult()
}
