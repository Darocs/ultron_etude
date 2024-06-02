package com.atiurin.ultron.config

import com.atiurin.ultron.core.common.UltronOperationType

object UltronCommonConfig {
    val operationsExcludedFromListeners: MutableList<UltronOperationType> = mutableListOf()
    var operationTimeoutMs : Long = 5_000
    var isListenersOn = true
    var logDateFormat = "MM-dd HH:mm:ss.SSS"
}