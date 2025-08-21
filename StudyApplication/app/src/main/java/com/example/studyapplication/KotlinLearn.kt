package com.example.studyapplication


fun main() {
    println("hello")

//    Kotlin 的基本类型
//    byte      short       int         long    float   double  --java
//    Byte      Short       Int         Long    Float   Double  --kotlin
//    Byte      Short       Integer     Long    Float   Double  --java
//    Byte?     Short?      Int?        Long?   Float?  Double? --kotlin

    val a: Int = 3 // java final
    var b = 3.0

//    数组 int[]
    val a1 = arrayOf(1, 2, 3)
    for (i in a1) {
        println(i)
    }

    val a2 = arrayOfNulls<Int>(3)
    a2[0] = 1
    for (i in a2) {
        println(i)
    }

    val a3 = Array(3) {
        it * it
    }

    for (i in a3) {
        println(i)
    }

//    遍历数组的方式
    for (i in a3.indices) {
        println(i)
    }

    for ((index, item) in a3.withIndex()) {
        println("index: $index === item: $item")
//        python f'{x}'
//        js `${x}`
    }

//    forEach
    a3.forEach({ it: Int -> println(it) })
    a3.forEach({ it -> println(it) })
    a3.forEach({ println(it) })
    a3.forEach() { println(it) }
    a3.forEach { println(it) }
    a3.forEach(::println)

    a3.forEachIndexed { index, item -> println("$index, $item") }

//    Collection ArrayList HashMap
    val c1 = listOf(1, 2, 3)
    println(c1)

    val c2 = mutableListOf(1, 2, 3)
    c2[0] = 5
    c2.removeAt(1)
    c2.add(6)
    println(c2)
}