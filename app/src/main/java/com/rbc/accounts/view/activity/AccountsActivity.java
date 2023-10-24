package com.rbc.accounts.view.activity;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.rbc.accounts.R;
import com.rbc.accounts.databinding.ActivityAccountsBinding;
import com.rbc.accounts.model.AccountInfoSummaryType;
import com.rbc.accounts.view.fragment.AccountDetailsFragment;
import com.rbc.accounts.viewmodel.activity.AccountsActivityViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AccountsActivity extends AppCompatActivity {

    public static String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
    public static String ACCOUNT_NAME = "ACCOUNT_NAME";
    public static String ACCOUNT_BALANCE = "ACCOUNT_BALANCE";
    public static String ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public static String ACCOUNT_DETAILS_FRAGMENT = "ACCOUNT_DETAILS_FRAGMENT";
    private AccountsActivityViewModel viewModel;

    private ActivityAccountsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AccountsActivityViewModel.class);
        setupUi();
        setupObserver();
    }

    private void setupUi() {
        setSupportActionBar(binding.accountActivityToolbar);
        setTitle(getString(R.string.account_summary_screen_toolbar_title));
    }

    private void setupObserver() {
        viewModel.accountSelectedLiveData
                .observe(this, accountInfoSummaryType -> {
                    openDetailsFragment(accountInfoSummaryType);
                    updateToolbar();
                    getOnBackPressedDispatcher().addCallback(onBackInvokedCallback);
                });
    }

    private void openDetailsFragment(AccountInfoSummaryType account) {
        AccountDetailsFragment fragment = new AccountDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ACCOUNT_NUMBER, account.getNumber());
        bundle.putString(ACCOUNT_NAME, account.getName());
        bundle.putString(ACCOUNT_BALANCE, account.getBalance());
        bundle.putInt(ACCOUNT_TYPE, account.getType());

        fragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.account_activity_fragment_container, fragment, ACCOUNT_DETAILS_FRAGMENT)
                .addToBackStack(ACCOUNT_DETAILS_FRAGMENT)
                .commit();
    }

    private void updateToolbar() {
        setTitle(getString(R.string.account_details_screen_toolbar_title));
        binding.accountActivityToolbar.setNavigationIcon(R.drawable.icon_back_white);
        binding.accountActivityToolbar.setNavigationOnClickListener(v -> navigateBack());
    }

    private void resetToolbar() {
        setTitle(getString(R.string.account_summary_screen_toolbar_title));
        binding.accountActivityToolbar.setNavigationIcon(null);
    }

    private void navigateBack() {
        resetToolbar();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
        getSupportFragmentManager().popBackStack();
    }

    private final OnBackPressedCallback onBackInvokedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            navigateBack();
        }
    };

}
