package com.example.studyapplication.kotlin

import java.util.Random


fun main() {
    val nextInt = Random().nextInt();
    if (nextInt % 2 == 0) {
        println("is even")
    } else {
        println("is odd")
    }

    val s = if (nextInt % 2 == 0) {
        "is even"
    } else {
        "is odd"
    }
    println(s)

    for (i in 1..10) {
        println(i)
    }

    for (i in 1..<10) {
        println(i)
    }

    for (i in 1 until 10) {
        println(i)
    }

    for (i in 10 downTo 1) {
        println(i)
    }

    for (i in 1..10 step 2) {
        println(i)
    }

    val closedFloatingPointRange = 1f..2f

    println(1.5 in closedFloatingPointRange)

//    For-loop range must have an 'iterator()' method.
//    for (i in closedFloatingPointRange) {
//
//    }

    val charRange = 'a'..'z'
    println(charRange.joinToString())

    when {
        nextInt % 2 == 0 -> {
            println("is even")
        }

        else -> {
            println("is odd")
        }
    }

    val ss = when {
        nextInt % 2 == 0 -> {
            "is even"
        }

        else -> {
            "is odd"
        }
    }
    println(ss)

    val discount = when (Random().nextFloat()) {
        in 0f..<0.2f -> {
            "超级折扣"
        }

        in 0.2f..0.8f -> {
            "一版 折扣"
        }

        else -> {
            "较低折扣"
        }
    }
    println(discount)

    val a = 1
    val b = 2

    var d = 0
    try {
        d = a / b
    } catch (e: Exception) {
        d = 0
    }
    println(d)

    val c = try {
        a / b
    } catch (e: Exception) {
        0
    }
    println(c)
}