package com.zm.org.balance.di

import android.content.Context
import androidx.room.Room
import com.zm.org.balance.data.database.AppDatabase
import com.zm.org.balance.data.database.TransactionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideTransactionsDao(database: AppDatabase): TransactionsDao {
        return database.transactionsDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "user-balance.db"
        ).build()
    }
}
