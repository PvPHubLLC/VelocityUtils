package co.pvphub.velocity.scheduling

import java.util.concurrent.Executors

object ThreadManager {
    var worker = Executors.newFixedThreadPool(4)
        private set

    fun setMaxThreads(numThreads: Int) {
        worker = Executors.newFixedThreadPool(numThreads)
    }
}