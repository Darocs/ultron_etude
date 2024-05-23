package com.atiurin.ultron.log


abstract class UFileLogger : ULogger() {
    abstract fun getLogFilePath(): String
    abstract fun clearFile()
}