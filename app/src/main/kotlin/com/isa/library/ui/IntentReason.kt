package com.isa.library.ui

interface IntentReason {
    val condition: Boolean
    val target: Class<*>
    val block: Class<*>
}