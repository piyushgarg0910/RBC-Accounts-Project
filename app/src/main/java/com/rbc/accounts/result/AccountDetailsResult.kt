package com.rbc.accounts.result

import com.rbc.rbcaccountlibrary.Transaction

sealed class AccountDetailsResult {

    class Success(val transactions: List<Transaction>) : AccountDetailsResult()

    sealed class Failure : AccountDetailsResult() {
        object TransactionsEmpty : AccountDetailsResult()
        object UnableToRetrieveTransactions: AccountDetailsResult()
    }

}
