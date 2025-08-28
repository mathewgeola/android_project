package com.example.studyapplication.kotlin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

suspend fun foo1() {

}

suspend fun foo2() {
    delay(1000L)
}

suspend fun foo3(): Int {
    return 3
}

suspend fun foo4(): Int {
    delay(1000L)
    return 4
}

suspend fun foo5(): String {
    delay(1000L)
    return "5"
}


suspend fun function1(): String {
    withContext(Dispatchers.IO) {
        delay(1000L)
    }
    return "function1"
}

suspend fun function2(a: String): String {
    withContext(Dispatchers.IO) {
        delay(1000L)
    }
    return "$a function2"
}

suspend fun function3(a: String, b: String): String {
    withContext(Dispatchers.IO) {
        delay(1000L)
    }
    return "$a $b function3"
}

suspend fun function() {
    val a = function1()
    val b = function2(a)
    val c = function3(a, b)
    println(c)
}
