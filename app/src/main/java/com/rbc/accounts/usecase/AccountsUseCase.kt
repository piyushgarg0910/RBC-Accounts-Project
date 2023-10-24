package com.rbc.accounts.usecase

import com.rbc.accounts.result.AccountAdditionalTransactionsResult
import com.rbc.accounts.result.AccountDetailsResult
import com.rbc.accounts.result.AccountTransactionsResult
import com.rbc.accounts.repo.AccountsRepo
import com.rbc.rbcaccountlibrary.Account
import com.rbc.rbcaccountlibrary.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class AccountsUseCase(private val repo: AccountsRepo) {

    suspend fun getAccountsListUseCase() : List<Account> {
        return repo.getListOfAccounts()
    }

    suspend fun getAccountDetailsUseCase(accountNumber: String, isCreditCard: Boolean):
            AccountDetailsResult {
        return withContext(Dispatchers.IO) {
            val allTransactions = ArrayList<Transaction>()
            val transactionsAsync = async { repo.getTransactions(accountNumber) }
            val additionalTransactionsAsync =
                async {
                    if (isCreditCard) {
                        repo.getAdditionalTransactions(accountNumber)
                    } else {
                        AccountAdditionalTransactionsResult.Failure
                    }
                }

            val transactionsResult = transactionsAsync.await()
            val additionalTransactionsResult = additionalTransactionsAsync.await()

            when(transactionsResult) {
                is AccountTransactionsResult.Success -> {
                    if (transactionsResult.transactions.isEmpty()) {
                        return@withContext AccountDetailsResult.Failure.TransactionsEmpty
                    } else {
                        allTransactions.addAll(transactionsResult.transactions)
                    }
                }

                is AccountTransactionsResult.Failure -> {
                    return@withContext AccountDetailsResult.Failure.UnableToRetrieveTransactions
                }
            }

            when(additionalTransactionsResult) {
                is AccountAdditionalTransactionsResult.Success -> {
                    allTransactions.addAll(additionalTransactionsResult.transactions)
                }

                is AccountAdditionalTransactionsResult.Failure -> {
                    // Noop for now.
                }
            }

            allTransactions.sortByDescending { it.date }
            return@withContext AccountDetailsResult.Success(allTransactions)
        }
    }
}