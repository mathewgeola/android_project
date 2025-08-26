package com.example.studyapplication.kotlin

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//// Modify Run Configuration...
//// VM options:
//// -Dkotlinx.coroutines.debug

fun getUrlList(): List<String> {
    val urlList = mutableListOf<String>()
    for (i in 1..10) {
        val url = "https://www.example.com/${i}"
        urlList.add(url)
    }
    println("get $urlList")
    return urlList;
}

fun requestUrl(url: String) {
    println("request $url")
}

fun fun1() {
    val urlList = getUrlList()
    urlList.forEach { requestUrl(it) }
}

fun addCore(a: Int, b: Int): Int {
    return a + b
}

// 代码块函数体
fun add1(a: Int, b: Int): Int {
    return addCore(a, b)
}

// 表达式函数体
fun add2(a: Int, b: Int) = addCore(a, b)


fun genUr1(): Sequence<String> {
    return sequence {
        for (i in 1..10) {
            val url = "https://www.example.com/${i}"
            println("gen $url")
            yield(url)
        }
    }
}

fun genUrl2() = sequence {
    for (i in 1..10) {
        val url = "https://www.example.com/${i}"
        println("gen $url")
        yield(url)
    }
}


fun fun2() = runBlocking {
    val genUrl = genUrl2()
    genUrl.iterator().forEach {
        println("request $it")
    }
}

fun fun31() {
    GlobalScope.launch {
        println("in lunch ${Thread.currentThread().name}")
        delay(1000L)
        println("after: 1000 ms")
    }
    println("out lunch ${Thread.currentThread().name}")
    Thread.sleep(2000L)
}

fun fun32() {
    val result = runBlocking {
        println("in lunch ${Thread.currentThread().name}")
        delay(1000L)
        println("after 1000 ms")
        return@runBlocking "data"
    }
    println("out lunch ${Thread.currentThread().name} result: $result")
    Thread.sleep(2000L)
}

fun fun33() = runBlocking {
    println("main ${Thread.currentThread().name}")

    val deferred = async {
        println("in lunch ${Thread.currentThread().name}")
        delay(1000L)
        println("after 1000 ms")
        return@async "data"
    }

    println("out lunch ${Thread.currentThread().name}")
    val result = deferred.await()
    println(result)
}

suspend fun fun34() {
    println("main ${Thread.currentThread().name}")

    val deferred = GlobalScope.async {
        println("in lunch ${Thread.currentThread().name}")
        delay(1000L)
        println("after 1000 ms")
        return@async "data"
    }

    println("out lunch ${Thread.currentThread().name}")
    val result = deferred.await()
    println(result)
}

// CPS 转换 将带有 suspend 关键字修饰的函数 转换为 Callback 函数
// void f1(Continuation obj){}
// Result<Double>
suspend fun f1(): Double {
    delay(1000L)
    return 0.0
}

suspend fun f2() {
    f1()
}

fun main() {
    fun1()

    fun2()

    fun31()
    fun32()
    fun33()
    runBlocking { fun34() }
    runBlocking { f2() }
}