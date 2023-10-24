package com.rbc.accounts.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rbc.accounts.R
import com.rbc.accounts.databinding.FragmentAccountSummaryBinding
import com.rbc.accounts.model.BaseAccountSummaryModel
import com.rbc.accounts.view.AccountSummaryItemSeparator
import com.rbc.accounts.view.adapter.AccountListAdapter
import com.rbc.accounts.viewmodel.activity.AccountsActivityViewModel
import com.rbc.accounts.viewmodel.fragment.AccountSummaryFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountSummaryFragment : Fragment() {

    private lateinit var binding: FragmentAccountSummaryBinding
    private val viewModel : AccountSummaryFragmentViewModel by viewModels()
    private val activityViewModel: AccountsActivityViewModel by activityViewModels ()

    private val adapter by lazy {
        AccountListAdapter { account ->
            activityViewModel.accountSelected(account)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountSummaryBinding.inflate(layoutInflater, container, false)

        setupRecyclerView()
        setupObservers()

        viewModel.getAccounts()
        return binding.root
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        binding.accountsRecyclerView.adapter = adapter
        binding.accountsRecyclerView.layoutManager = layoutManager
        binding.accountsRecyclerView.addItemDecoration(
            AccountSummaryItemSeparator(requireContext())
        )
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                if (uiState.isLoading) {
                    showLoadingState()
                    hideErrorState()
                    hideAccounts()
                } else if (uiState.isError) {
                    hideLoadingState()
                    showErrorState()
                    hideAccounts()
                } else {
                    hideLoadingState()
                    hideErrorState()
                    showAccounts(uiState.accounts)
                }
            }
        }
    }

    private fun showLoadingState() {
        binding.accountSummaryProgressBar.isVisible = true
        binding.accountSummaryLoadingText.isVisible = true
    }

    private fun hideLoadingState() {
        binding.accountSummaryProgressBar.isVisible = false
        binding.accountSummaryLoadingText.isVisible = false
    }

    private fun showErrorState() {
        binding.accountSummaryErrorText.isVisible = true
        binding.accountSummaryErrorText.text = ""
    }

    private fun hideErrorState() {
        binding.accountSummaryErrorText.isVisible = false
        binding.accountSummaryErrorText.text = getString(R.string.unknown_error_while_fetching_data)
    }

    private fun showAccounts(accounts: List<BaseAccountSummaryModel>) {
        binding.accountsRecyclerView.isVisible = true
        adapter.updateAdapter(accounts)
    }

    private fun hideAccounts() {
        binding.accountsRecyclerView.isVisible = false
        adapter.updateAdapter(emptyList())
    }
}