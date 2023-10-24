package com.rbc.accounts.dependencyinjection

import com.rbc.accounts.repo.AccountsRepo
import com.rbc.accounts.usecase.AccountsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class HiltModule {

    @Singleton
    @Provides
    fun provideAccountRepo() : AccountsRepo {
        return AccountsRepo()
    }

    @Provides
    fun provideAccountsUseCase(accountsRepo: AccountsRepo): AccountsUseCase {
        return AccountsUseCase(accountsRepo)
    }
}