package com.example.studyapplication.kotlin

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import kotlin.random.Random
import kotlin.system.measureTimeMillis

//VM options: -Dkotlinx.coroutines.debug

// 开始 取消 完成
fun Job.log() {
    println(
        """
    ${"*" * 50}
    threadName = ${Thread.currentThread().name}
    isActive = $isActive
    isCancelled = $isCancelled
    isCompleted = $isCompleted
    ${"*" * 50}  
    """.trimIndent()
    )
}

private operator fun String.times(i: Int): String {
    return repeat(i)
}

suspend fun main1() {
    val job = GlobalScope.launch { }
}

fun main2() = runBlocking {
    val job = launch {
        println("job start")
        delay(1000)
        println("job end")
    }
    job.log()
    delay(500)
    job.cancel()
    job.log()
    delay(2000)
}

fun main3() = runBlocking {
    val job = launch(start = CoroutineStart.LAZY) {
        println("start job")
        delay(1000)
    }
    delay(500)
    job.log()
    job.start()
    job.log()
    delay(500)
//    job.cancel()
    job.log()
    delay(2000)
    job.log()
}

fun main4() = runBlocking {
    suspend fun network() {
        val time = (Random.nextDouble() * 1000).toLong();
        println("start job, time = $time ms")
        delay(time)
    }

    val job = launch(start = CoroutineStart.LAZY) {
        println("start job")
        network()
        println("end job")
    }

    delay(500)
    job.start()
    job.log()
    job.invokeOnCompletion {
        println("job completed")
        job.log()
    }
    job.join()
}

fun main5() = runBlocking {
    val deferred = async {
        println("start job")
        delay(1000)
        println("end job")
        "data"
    }
    println("start await")
    val result = deferred.await()
    println("result = $result")
}

fun main6() = runBlocking {
//    图片上传
    val submitPost: Job

    var uploadImage: Job? = null
    var uploadVideo: Job? = null
    var uploadAudio: Job? = null

    submitPost = launch {
        println("start submitPost")

        uploadImage = launch {
            println("start upload image")
            delay(1000)
            println("upload image end")
        }

        uploadVideo = launch {
            println("start upload video")
            delay(2000)
            println("upload video end")
        }

        uploadAudio = launch {
            println("start upload audio")
            delay(3000)
            println("upload audio end")
        }
    }

    delay(1000)

    submitPost.children.forEachIndexed { index, job -> println("index = $index, job = $job") }

//    submitPost.cancel()
    submitPost.join()
    println("completed")
}

// 九宫格
fun main7() = runBlocking {
    suspend fun uploadImage(index: Int): String {
        println("start upload image $index")
        delay(1000)
        println("end upload image $index")
        return "https://www.yuanrenxue.cn/image/$index"
    }

    val imageList = mutableListOf<String>()

    val time = measureTimeMillis {
        imageList.add(uploadImage(1))
        imageList.add(uploadImage(2))
        imageList.add(uploadImage(3))
    }
    println("time = $time")
    println("imageList = $imageList")

    val imageList1: List<String>

    val time1 = measureTimeMillis {
        val d1 = async { uploadImage(1) }
        val d2 = async { uploadImage(2) }
        val d3 = async { uploadImage(3) }

        imageList1 = listOf(d1.await(), d2.await(), d3.await())
    }

    println("time1 = $time1")
    println("imageList = $imageList1")
}

fun log(msg: Any) {
    println("${Thread.currentThread().name} $msg")
}

suspend fun net(): String {
    log("start net")
    withContext(Dispatchers.IO) {
        log("start net with context")
        val time = (Random.nextDouble() * 1000).toLong()
        delay(time)
    }
    log("end net")
    return "data"
}

val customDispatcher = Executors.newSingleThreadExecutor {
    Thread(it, "customDispatcher").apply { isDaemon = true }
}.asCoroutineDispatcher()

fun main() = runBlocking(customDispatcher) {
    log("start")
    val a = net()
    log(a)
}