package com.rbc.accounts.viewmodel.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbc.accounts.model.AccountTransactionDateType
import com.rbc.accounts.model.AccountTransactionDetailType
import com.rbc.accounts.model.BaseAccountDetailsSummaryModel
import com.rbc.accounts.result.AccountDetailsResult
import com.rbc.accounts.usecase.AccountsUseCase
import com.rbc.accounts.util.CalendarToDateStringConverter
import com.rbc.accounts.view.viewHolder.AccountDetailsViewHolderType
import com.rbc.rbcaccountlibrary.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountDetailsFragmentViewModel@Inject constructor(
    private val accountsUseCase: AccountsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState : StateFlow<UiState> = _uiState.asStateFlow()

    fun getAccountDetails(accountNumber: String?, isCreditCard: Boolean?) {
        if (accountNumber == null || isCreditCard == null) {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        isError = true,
                        isLoading = false,
                        errorType = ErrorType.INCORRECT_ACCOUNT_INFORMATION,
                        transactions = emptyList()
                    )
                }
            }
            return
        }
        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    isError = false,
                    errorType = ErrorType.NO_ERROR,
                    transactions = emptyList()
                )
            }

            val transactionResult = accountsUseCase
                .getAccountDetailsUseCase(accountNumber, isCreditCard)

            _uiState.update {
                when (transactionResult) {
                    is AccountDetailsResult.Success -> {
                        val transactionsMap = LinkedHashMap<String, ArrayList<Transaction>>()
                        transactionResult.transactions.forEach { transaction ->
                            val transactionDay = CalendarToDateStringConverter.getInstance().formatToDateListItem(transaction.date.timeInMillis)
                            if (transactionsMap.containsKey(transactionDay)) {
                                transactionsMap[transactionDay]?.add(transaction)
                                transactionsMap[transactionDay]?.sortByDescending { datedTransactions ->
                                    datedTransactions.date
                                }
                            } else {
                                transactionsMap[transactionDay] = arrayListOf(transaction)
                            }
                        }

                        val transactionsSummary = ArrayList<BaseAccountDetailsSummaryModel>()
                        transactionsMap.forEach { (t, u) ->
                            transactionsSummary.add(AccountTransactionDateType(t, AccountDetailsViewHolderType.DATE))
                            u.forEach { transaction ->
                                transactionsSummary
                                    .add(
                                        AccountTransactionDetailType(
                                            CalendarToDateStringConverter.getInstance().format(transaction.date.timeInMillis),
                                            AccountDetailsViewHolderType.TRANSACTION,
                                            transaction.amount,
                                            transaction.description
                                        )
                                    )
                            }
                        }

                        it.copy(
                            isLoading = false,
                            isError = false,
                            transactions = transactionsSummary,
                            errorType = ErrorType.NO_ERROR
                        )
                    }

                    is AccountDetailsResult.Failure.TransactionsEmpty -> {
                        it.copy(
                            isLoading = false,
                            isError = true,
                            transactions = emptyList(),
                            errorType = ErrorType.NO_TRANSACTIONS
                        )
                    }

                    is AccountDetailsResult.Failure.UnableToRetrieveTransactions -> {
                        it.copy(
                            isLoading = false,
                            isError = true,
                            transactions = emptyList(),
                            errorType = ErrorType.UNABLE_TO_RETRIEVE_TRANSACTIONS
                        )
                    }
                }
            }
        }
    }

    fun handleAccountNumberVisibility() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isAccountNumberVisible = !it.isAccountNumberVisible
                )
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val transactions: List<BaseAccountDetailsSummaryModel> = emptyList(),
        val isError: Boolean = false,
        val errorType: ErrorType = ErrorType.NO_ERROR,
        val isAccountNumberVisible: Boolean = false
    )

    enum class ErrorType {
        NO_ERROR,
        NO_TRANSACTIONS,
        UNABLE_TO_RETRIEVE_TRANSACTIONS,
        INCORRECT_ACCOUNT_INFORMATION
    }
}