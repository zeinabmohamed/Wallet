package com.zm.org.balance.di

import android.content.Context
import com.zm.org.balance.data.usertransactions.UserTransactionsRepository
import com.zm.org.balance.domain.usertransactions.AddUserTransactionUseCase
import com.zm.org.balance.domain.usertransactions.GetUserBalanceSummaryUseCase
import com.zm.org.balance.domain.usertransactions.GetUserTransactionsCategorizedByDateUseCase
import com.zm.org.balance.domain.usertransactions.TransactionMapper
import com.zm.org.balance.ui.error.ErrorToUserMessage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @ViewModelScoped
    fun provideUserErrorMapper(
        @ApplicationContext appContext: Context
    ): ErrorToUserMessage {
        return ErrorToUserMessage(
            appContext)
    }
}
