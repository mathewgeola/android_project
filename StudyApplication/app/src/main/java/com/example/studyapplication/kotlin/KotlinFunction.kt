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

fun List<Int>.sum(): Int {
    var r = 0
    for (v in this) {
        r += v
    }
    return r
}

fun List<Int>.sumPlus(callback: (Int) -> Unit): Int {
    var r = 0
    for (v in this) {
        r += v
        callback(r)
    }
    return r
}

fun List<Int>.sumPro1(): (scale: Float) -> Float {
    return fun(scale): Float {
        var r = 0f
        for (v in this) {
            r += v * scale
        }
        return r
    }
}

fun List<Int>.sumPro2(): (scale: Float) -> Float {
    return block@{ scale ->
        var r = 0f
        for (v in this) {
            r += v * scale
        }
        r
    }
}

fun List<Int>.sumPro3(): (scale: Float) -> Float {
    return {
        var r = 0f
        for (v in this) {
            r += v * it
        }
        r
    }
}

fun add(v1: Int): (Int, (Int) -> Unit) -> Unit {
    return fun(v2: Int, callback: (Int) -> Unit) {
        callback(v1 + v2)
    }
}

class User(val username: String, val desc: String) {
    operator fun component1(): String {
        return username
    }

    operator fun component2(): String {
        return desc
    }
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

    val list = listOf<Int>(1, 2, 3, 4, 5)
    println(list.sum())

    println(list.sumPlus { println(it) })
    println(list.sumPlus(::println))

    println(list.sumPro1()(0.5f))
    println(list.sumPro2()(0.5f))
    println(list.sumPro3()(0.5f))

    val filter = list.filter { it % 2 == 0 }
    println(filter)

    val map = list.map { it * it }
    println(map)

    val flatMap = list.asSequence().flatMap { 0..it }
    println(flatMap)
    println(flatMap.toList())
    flatMap.forEach { println(it) }

    add(1)(2) {
        println(it)
    }

    val user = User("tom", "how are you")
    val username1 = user.username
    val desc1 = user.desc
    val (username2, desc2) = user
    println("username1 = $username1 desc1 = $desc1")
    println("username2 = $username2 desc2 = $desc2")
}