package com.example.studyapplication.kotlin


fun add(a: Int, b: Int): Int {
    return a + b
}


inline fun someInlineFunc() {
    println("1")
    println("2")
}

// 中缀运算符函数
infix fun Int.abc(bitCont: Int): Int {
    return this.shl(bitCont)
}


fun main() {
    println(add(1, 2))

    someInlineFunc()

    println(1.abc(5))
    println(1 abc 5)
    println(1 abc 5 + 1)
    println(1 abc (5 + 1))

    val addFun1 = fun(a: Int, b: Int): Int {
        return a + b
    }
    val addFun2 = block@{ a: Int, b: Int -> a + b }
    val addFun3 = { a: Int, b: Int -> a + b }
    println(addFun1(1, 2))
    println(addFun3.invoke(1, 2))
}