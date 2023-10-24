package com.rbc.accounts.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rbc.accounts.R
import com.rbc.accounts.databinding.FragmentAccountDetailsBinding
import com.rbc.accounts.model.BaseAccountDetailsSummaryModel
import com.rbc.accounts.util.AccountNumberTruncateHelper.hidePartialNumber
import com.rbc.accounts.util.StringToCurrencyConverter
import com.rbc.accounts.view.AccountDetailsItemSeparator
import com.rbc.accounts.view.activity.AccountsActivity.ACCOUNT_NUMBER
import com.rbc.accounts.view.activity.AccountsActivity.ACCOUNT_TYPE
import com.rbc.accounts.view.activity.AccountsActivity.ACCOUNT_BALANCE
import com.rbc.accounts.view.activity.AccountsActivity.ACCOUNT_NAME
import com.rbc.accounts.view.adapter.AccountDetailsAdapter
import com.rbc.accounts.viewmodel.fragment.AccountDetailsFragmentViewModel
import com.rbc.rbcaccountlibrary.AccountType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAccountDetailsBinding
    private var accountNumber: String? = null
    private val adapter by lazy {
        AccountDetailsAdapter()
    }
    private val viewModel: AccountDetailsFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountDetailsBinding.inflate(
            layoutInflater,
            container,
            false
        )

        accountNumber = arguments?.getString(ACCOUNT_NUMBER)
        val accountType = arguments?.getInt(ACCOUNT_TYPE)

        setupUi()
        setupRecyclerView()
        setupObservers()

        viewModel.getAccountDetails(accountNumber, accountType == AccountType.CREDIT_CARD.ordinal)

        return binding.root
    }

    private fun setupUi() {
        val accountName = arguments?.getString(ACCOUNT_NAME)
        val accountBalance = arguments?.getString(ACCOUNT_BALANCE)

        binding.accountDetailsNameValue.text = accountName
        binding.accountDetailsNumberValue.text = accountNumber
        binding.accountDetailsBalanceValue.text =
            StringToCurrencyConverter
                .getInstance()
                .format(accountBalance)
    }

    private fun setupRecyclerView() {
        binding.accountDetailsRecyclerView.adapter = adapter
        binding.accountDetailsRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)

        binding.accountDetailsRecyclerView.addItemDecoration(
            AccountDetailsItemSeparator(requireContext())
        )
    }

    private fun setupObservers() {
        binding.accountDetailsNumberVisibilityIcon.setOnClickListener {
            viewModel.handleAccountNumberVisibility()
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->

                if(uiState.isAccountNumberVisible) {
                    showFullAccountNumber()
                } else {
                    showPartialAccountNumber()
                }

                if (uiState.isLoading) {
                    showProgressBar()
                    hideErrorMessage()
                    hideAccountDetails()
                    showAccountNumberIcon()
                } else if (uiState.isError) {
                    showAccountNumberIcon()
                    val error = when (uiState.errorType) {
                        AccountDetailsFragmentViewModel.ErrorType.NO_TRANSACTIONS -> {
                            getString(R.string.no_transactions_for_account)
                        }

                        AccountDetailsFragmentViewModel.ErrorType.UNABLE_TO_RETRIEVE_TRANSACTIONS -> {
                            getString(R.string.unable_to_retrieve_transaction)
                        }

                        AccountDetailsFragmentViewModel.ErrorType.INCORRECT_ACCOUNT_INFORMATION -> {
                            getString(R.string.incorrect_account_information)
                        }

                        else -> {
                            hideAccountNumberIcon()
                            getString(R.string.unknown_error_while_fetching_data)
                        }
                    }
                    showErrorMessage(error)
                    hideProgressBar()
                    hideAccountDetails()
                } else {
                    showAccountNumberIcon()
                    showAccountDetails(uiState.transactions)
                    hideProgressBar()
                    hideErrorMessage()
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.accountDetailsProgressBar.isVisible = true
        binding.accountDetailsLoadingText.isVisible = true
    }

    private fun hideProgressBar() {
        binding.accountDetailsProgressBar.isVisible = false
        binding.accountDetailsLoadingText.isVisible = false
    }

    private fun showErrorMessage(error: String) {
        binding.accountDetailsErrorText.isVisible = true
        binding.accountDetailsErrorText.text = error
    }

    private fun hideErrorMessage() {
        binding.accountDetailsErrorText.isVisible = false
        binding.accountDetailsErrorText.text = null
    }

    private fun showAccountDetails(transactions: List<BaseAccountDetailsSummaryModel>) {
        binding.accountDetailsRecyclerView.isVisible = true
        adapter.submitList(transactions)
    }

    private fun hideAccountDetails() {
        binding.accountDetailsRecyclerView.isVisible = false
        adapter.submitList(emptyList())
    }

    private fun showFullAccountNumber() {
        binding.accountDetailsNumberVisibilityIcon
            .setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_account_number_visible))
        binding.accountDetailsNumberValue.text = accountNumber
    }

    private fun showPartialAccountNumber() {
        binding.accountDetailsNumberVisibilityIcon
            .setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_account_number_invisible))
        binding.accountDetailsNumberValue.text = accountNumber?.hidePartialNumber()
    }

    private fun hideAccountNumberIcon() {
        binding.accountDetailsNumberVisibilityIcon.isVisible = false
    }

    private fun showAccountNumberIcon() {
        binding.accountDetailsNumberVisibilityIcon.isVisible = true
    }
}