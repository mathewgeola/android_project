package com.example.studyapplication.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    flow {
        emit(1)
        emit(2)
        emit(3)
        emit(4)
        emit(5)
    }.filter { it > 2 }.map { it * 2 }.take(2).collect {
        println("flow it: $it")
    }

    flowOf(1, 2, 3, 4, 5).filter { it <= 3 }.map { it + 5 }.take(2).collect {
        println("flowOf it: $it")
    }

    flowOf(1, 2, 3, 4, 5).toList().filter { it <= 3 }.map { it + 5 }.take(2).forEach {
        println("flowOf toList it: $it")
    }

    listOf(1, 2, 3, 4, 5).filter { it <= 3 }.map { it + 5 }.take(2).forEach {
        println("listOf it: $it")
    }

    listOf(1, 2, 3, 4, 5).asFlow().filter { it <= 3 }.map { it + 5 }.take(2).collect {
        println("listOf asFlow it: $it")
    }

    flowOf(1, 2, 3, 4, 5).filter {
        println("filter: $it")
        it > 2
    }.map {
        println("map: $it")
        it * 2
    }.take(2).onStart { println("onStart") }.onCompletion { println("onCompletion") }.collect {
        println(it)
    }

    flowOf(1, 2, 3, 4, 5).filter {
        println("filter: $it")
        it <= 3
    }.map {
        println("map: $it")
        it
    }.onStart {
        println("onStart")
    }.onCompletion {
        println("onCompletion: $it")
    }.collect {
        println("$it")
        if (it == 2) {
//            cancel()
        }
    }

    val flow1 = flow {
        emit(1)
        emit(2)
        throw NullPointerException()
        emit(3)
    }

    flow1.map { it * 2 }.catch { println("$it: catch") }.collect {
        try {
            println("$it")
        } catch (e: Exception) {

        }
    }

    val flow2 = flow {
        emit(1)
        emit(2)
        emit(3)
    }

    flow2.map { it * 2 }.flowOn(Dispatchers.IO).collect {
        println("$it")
    }

    val flow3 = flow {
        emit(1)
        emit(2)
        emit(3)
    }

//    flow3.map { it * 2 }.flowOn(Dispatchers.IO).onEach {
//        println("$it")
//    }.launchIn(CoroutineScope(Dispatchers.Main))

    flow3.map { it * 2 }.flowOn(Dispatchers.IO).onEach {
        println("$it")
    }.launchIn(CoroutineScope(Dispatchers.Default))

    return@runBlocking Unit
}