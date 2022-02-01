package com.zm.org.balance.ui.error

import android.content.Context
import com.zm.org.balance.R

/**
 * [ErrorToUserMessage] help us to map all data/domain [Throwable] to user descriptive messages
 *
 */
class ErrorToUserMessage(private val appContext: Context) {

    /**
     * for demo scope now just will map all cases to one user error message
     */
    fun toUserMessage(throwable: Throwable): String {
       return appContext.getString(R.string.something_wrong_please_try_again_later)
    }
}
