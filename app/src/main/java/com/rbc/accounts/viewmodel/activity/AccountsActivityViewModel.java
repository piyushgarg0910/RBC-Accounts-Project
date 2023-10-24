package com.rbc.accounts.viewmodel.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rbc.accounts.model.AccountInfoSummaryType;

public class AccountsActivityViewModel extends ViewModel {

    private MutableLiveData<AccountInfoSummaryType> _accountSelectedLiveData = new MutableLiveData();
    public LiveData<AccountInfoSummaryType> accountSelectedLiveData = _accountSelectedLiveData;

    public void accountSelected(AccountInfoSummaryType account) {
        _accountSelectedLiveData.setValue(account);
    }
}
