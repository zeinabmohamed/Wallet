package com.zm.org.wallet.di

import android.content.Context
import com.zm.org.wallet.data.usertransactions.UserTransactionsRepository
import com.zm.org.wallet.domain.usertransactions.AddUserTransactionUseCase
import com.zm.org.wallet.domain.usertransactions.GetUserBalanceSummaryUseCase
import com.zm.org.wallet.domain.usertransactions.GetUserTransactionsCategorizedByDateUseCase
import com.zm.org.wallet.domain.usertransactions.TransactionMapper
import com.zm.org.wallet.ui.error.ErrorToUserMessage
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
