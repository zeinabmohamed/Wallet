package com.zm.org.wallet.di

import com.zm.org.wallet.data.usertransactions.UserTransactionsRepository
import com.zm.org.wallet.data.usertransactions.UserTransactionsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindUserTransactionsRepository(
        userTransactionsRepositoryImpl: UserTransactionsRepositoryImpl,
    ): UserTransactionsRepository

}

