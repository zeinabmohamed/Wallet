package com.zm.org.balance.di

import com.zm.org.balance.data.database.TransactionsDao
import com.zm.org.balance.data.usertransactions.UserTransactionsRepository
import com.zm.org.balance.data.usertransactions.UserTransactionsRepositoryImpl
import com.zm.org.balance.data.usertransactions.local.UserTransactionsLocalDataSource
import com.zm.org.balance.domain.usertransactions.GetUserBalanceSummaryUseCase
import com.zm.org.balance.domain.usertransactions.GetUserTransactionsCategorizedByDateUseCase
import com.zm.org.balance.domain.usertransactions.TransactionMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindUserTransactionsRepository(
        userTransactionsRepositoryImpl: UserTransactionsRepositoryImpl,
    ): UserTransactionsRepository

}

