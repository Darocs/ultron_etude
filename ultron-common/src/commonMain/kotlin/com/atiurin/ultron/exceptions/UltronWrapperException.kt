package com.atiurin.ultron.exceptions

class UltronWrapperException : AssertionError {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable)
            : super(
        "$message${
            if (cause is UltronWrapperException || cause is UltronOperationException) ""
            else "\nOriginal error - ${cause::class.simpleName}: ${cause.message}"
        }"
    )
}