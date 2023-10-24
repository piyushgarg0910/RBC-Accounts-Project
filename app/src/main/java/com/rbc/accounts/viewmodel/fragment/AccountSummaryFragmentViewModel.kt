package com.rbc.accounts.viewmodel.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbc.accounts.model.AccountInfoSummaryType
import com.rbc.accounts.model.BaseAccountSummaryModel
import com.rbc.accounts.model.HeadingSummaryType
import com.rbc.accounts.usecase.AccountsUseCase
import com.rbc.accounts.view.viewHolder.AccountSummaryViewHolderType
import com.rbc.rbcaccountlibrary.Account
import com.rbc.rbcaccountlibrary.AccountType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountSummaryFragmentViewModel @Inject constructor(
    private val accountsUseCase: AccountsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState : StateFlow<UiState> = _uiState.asStateFlow()

    fun getAccounts() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            val accounts = accountsUseCase.getAccountsListUseCase()

            val accountsMap = HashMap<AccountType, ArrayList<Account>>()
            accounts.forEach { account ->
                if (accountsMap.containsKey(account.type)) {
                    accountsMap[account.type]?.add(account)
                } else {
                    accountsMap[account.type] = arrayListOf(account)
                }
            }

            val accountSummaryList = ArrayList<BaseAccountSummaryModel>()
            accountsMap.forEach { (t, u) ->
                accountSummaryList.add(HeadingSummaryType(AccountSummaryViewHolderType.HEADING, t.name))
                u.forEach { account ->
                    accountSummaryList.add(AccountInfoSummaryType(AccountSummaryViewHolderType.ACCOUNT,
                        account.name, account.number, account.balance, account.type.ordinal))
                }
            }

            _uiState.update {
                if (accounts.isEmpty()) {
                    it.copy(
                        isLoading = false,
                        accounts = emptyList(),
                        isError = true
                    )
                } else {
                    it.copy(
                        isLoading = false,
                        accounts = accountSummaryList,
                        isError = false
                    )
                }
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val accounts: List<BaseAccountSummaryModel> = emptyList(),
        val isError: Boolean = false
    )
}