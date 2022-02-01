package com.zm.org.balance.di

import com.zm.org.balance.data.usertransactions.UserTransactionsRepository
import com.zm.org.balance.domain.usertransactions.AddUserTransactionUseCase
import com.zm.org.balance.domain.usertransactions.GetUserBalanceSummaryUseCase
import com.zm.org.balance.domain.usertransactions.GetUserTransactionsCategorizedByDateUseCase
import com.zm.org.balance.domain.usertransactions.TransactionMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideGetUserBalanceSummaryUseCase(
        userTransactionsRepository: UserTransactionsRepository,
    ): GetUserBalanceSummaryUseCase {
        return GetUserBalanceSummaryUseCase(userTransactionsRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetUserTransactionsCategorizedByDateUseCase(
        userTransactionsRepository: UserTransactionsRepository,
        transactionMapper: TransactionMapper,
    ): GetUserTransactionsCategorizedByDateUseCase {
        return GetUserTransactionsCategorizedByDateUseCase(
            userTransactionsRepository,
            transactionMapper)
    }

    @Provides
    @ViewModelScoped
    fun provideAddUserTransactionUseCase(
        userTransactionsRepository: UserTransactionsRepository,
        transactionMapper: TransactionMapper,
    ): AddUserTransactionUseCase {
        return AddUserTransactionUseCase(
            userTransactionsRepository,
            transactionMapper)
    }
}
