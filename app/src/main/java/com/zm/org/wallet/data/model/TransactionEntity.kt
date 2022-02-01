package com.zm.org.wallet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zm.org.wallet.util.TimeMillis

@Entity(tableName = "user-transactions")
data class TransactionEntity (
    @ColumnInfo(name = "transaction_title")
    var title: String = "",
    @ColumnInfo(name = "transaction_type")
    var type: TransactionType = TransactionType.EXPENSE,
    /**
    all amounts expected will be in same Currency,
    so for now following kiss principle will keep it simple without currency support
     **/
    @ColumnInfo(name = "transaction_amount")
    var amount: Float = 0f,
    /**
     * DateTime in timestamp format
     */
    @ColumnInfo(name = "transaction_creationDateMillis")
    @get:JvmName("getCreationDateMillis")
    @set:JvmName("setCreationDateMillis")
    var creationDateMillis: TimeMillis = TimeMillis(),
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
    constructor():this("",TransactionType.EXPENSE,0f,TimeMillis())
}
