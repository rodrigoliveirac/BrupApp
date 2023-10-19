package com.rodcollab.brupapp.util

fun <T> initializer(callback: () -> T): T = callback()