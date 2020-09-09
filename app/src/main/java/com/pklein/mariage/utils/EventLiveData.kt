package com.pklein.mariage.utils

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class EventLiveData<out T>(private val content: T) {
    var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            content
        }
    }
}