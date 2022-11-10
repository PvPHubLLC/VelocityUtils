package co.pvphub.velocity.scheduling

import co.pvphub.velocity.plugin.VelocityPlugin
import com.velocitypowered.api.scheduler.ScheduledTask
import com.velocitypowered.api.scheduler.Scheduler
import com.velocitypowered.api.scheduler.TaskStatus
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


/**
 * Task allows for easy creation of asynchronous tasks.
 *
 * @param plugin instance
 * @param task you want to do
 */
open class SyncTask(
    protected val plugin: VelocityPlugin,
    protected val task: (SyncTask) -> Unit
) : Scheduler.TaskBuilder {
    protected var delay: Long = 0
    protected var delayUnit: TimeUnit = TimeUnit.MILLISECONDS
    protected var repeat: Long = 0
    protected var repeatUnit: TimeUnit = TimeUnit.MILLISECONDS
    var iteration: Long = 0
        protected set

    protected var runningTask: ScheduledTask? = null

    override fun delay(time: Long, unit: TimeUnit): SyncTask {
        this.delay = time
        this.delayUnit = unit
        return this
    }

    override fun repeat(time: Long, unit: TimeUnit): SyncTask {
        this.repeat = time
        this.repeatUnit = unit
        return this
    }

    override fun clearDelay(): SyncTask {
        this.delay = 0
        return this
    }

    override fun clearRepeat(): SyncTask {
        this.repeat = 0
        return this
    }

    fun cancel() = runningTask?.cancel()

    fun status(): TaskStatus? = runningTask?.status()

    fun plugin() : VelocityPlugin = plugin

    override fun schedule(): ScheduledTask {
        val scheduledTask = plugin.server.scheduler
            .buildTask(plugin, getTask())
            .repeat(repeat, repeatUnit)
            .delay(delay, delayUnit)
            .schedule()
        runningTask = scheduledTask
        return scheduledTask
    }

    open fun getTask() : () -> Unit = {
        task(this)
        iteration++
    }

}