package com.ardidong.omdbapp.common


fun Int?.orZero() = this ?: 0

fun Boolean?.orFalse() = this ?: false
