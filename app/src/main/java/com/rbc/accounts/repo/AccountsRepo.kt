package com.rbc.accounts.repo

import com.rbc.accounts.result.AccountAdditionalTransactionsResult
import com.rbc.accounts.result.AccountTransactionsResult
import com.rbc.rbcaccountlibrary.Account
import com.rbc.rbcaccountlibrary.AccountProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountsRepo {

    suspend fun getListOfAccounts(): List<Account> =
        withContext(Dispatchers.IO) {
            AccountProvider.getAccountsList()
        }

    fun getTransactions(accountNumber: String): AccountTransactionsResult {
        return try {
            AccountTransactionsResult.Success(AccountProvider.getTransactions(accountNumber))
        } catch (ex: Exception) {
            AccountTransactionsResult.Failure
        }
    }

    fun getAdditionalTransactions(
        accountNumber: String
    ): AccountAdditionalTransactionsResult {
        return try {
            AccountAdditionalTransactionsResult.Success(AccountProvider.getAdditionalCreditCardTransactions(accountNumber))
        } catch (ex: Exception) {
            AccountAdditionalTransactionsResult.Failure
        }
    }
}