package com.zm.org.balance.di

import android.content.Context
import androidx.room.Room
import com.zm.org.balance.data.database.AppDatabase
import com.zm.org.balance.data.database.TransactionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DatabaseModule::class])
object FakeDatabaseModule {

    @Provides
    fun provideTransactionsDao(database: AppDatabase): TransactionsDao {
        return database.transactionsDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.inMemoryDatabaseBuilder(
            appContext, AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }
}
