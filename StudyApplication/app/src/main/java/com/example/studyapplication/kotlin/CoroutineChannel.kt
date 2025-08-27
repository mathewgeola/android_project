package com.example.studyapplication.kotlin

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main11() = runBlocking {
    val channel1 = Channel<Int>()
    val channel2 = Channel<Int>(capacity = Channel.UNLIMITED)
    val channel3 = Channel<Int>(capacity = Channel.CONFLATED)
    val channel4 = Channel<Int>(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val channel5 = Channel<Int>(capacity = 2, onBufferOverflow = BufferOverflow.DROP_LATEST)

    var channel: Channel<Int>? = null

    channel = channel1
    channel = channel2
    channel = channel3
    channel = channel4
    channel = channel5

    launch {
        (1..3).forEach {
            channel.send(it)
            println("send: $it")
        }
        channel.close()
    }

    launch {
        for (i in channel) {
            println("receive: $i")
        }
    }

    println("end")
}

fun main22() = runBlocking {
    val channel6 = Channel<Int>(capacity = 2, onBufferOverflow = BufferOverflow.DROP_LATEST) {
        println("not deal: $it")
    }

    var channel: Channel<Int>? = null
    channel = channel6

    launch {
        (1..3).forEach {
            channel.send(it)
            println("send: $it")
        }
        channel.close()
    }

    launch {
        channel.receive()
        channel.cancel()
    }

    println("end")
}

fun main() = runBlocking {
    val channel: ReceiveChannel<Int> = produce {
        (1..3).forEach {
            send(it)
            println("send: $it")
        }
    }

    launch {
        for (i in channel) {
            println("receive: $i")
        }
    }
    println("end")
}
