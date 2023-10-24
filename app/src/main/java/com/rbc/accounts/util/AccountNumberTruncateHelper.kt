package com.rbc.accounts.util

object AccountNumberTruncateHelper {

    fun String.truncate() : String {
        return if (length <= 4) {
            substring(0, length-1)
        } else {
            "x-${substring(length-5, length-1)}"
        }
    }


    fun String.hidePartialNumber() : String {
        return if (length <= 7) {
            "****${substring(length-3, length-1)}"
        } else {
            "${substring(0, 2)}******${substring(length - 5, length - 1)}"
        }
    }
}