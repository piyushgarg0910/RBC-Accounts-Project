package com.rbc.accounts.viewmodel.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbc.accounts.result.AccountDetailsResult
import com.rbc.accounts.usecase.AccountsUseCase
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
                        it.copy(
                            isLoading = false,
                            isError = false,
                            transactions = transactionResult.transactions,
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
        val transactions: List<Transaction> = emptyList(),
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