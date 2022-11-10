package co.pvphub.velocity.scheduling

import co.pvphub.velocity.plugin.VelocityPlugin
import com.velocitypowered.api.scheduler.ScheduledTask
import kotlin.concurrent.thread

/**
 * Task allows for easy creation of asynchronous tasks.
 *
 * @param plugin instance
 * @param task you want to do
 */
class AsyncTask(
    plugin: VelocityPlugin,
    private val asyncTask: (AsyncTask) -> Unit
) : SyncTask(plugin, {}) {

    override fun schedule(): ScheduledTask {
        val scheduledTask = plugin.server.scheduler
            .buildTask(plugin, getTask())
            .repeat(repeat, repeatUnit)
            .delay(delay, delayUnit)
            .schedule()
        runningTask = scheduledTask
        return scheduledTask
    }

    override fun getTask() : () -> Unit = {
        thread(false) {
            asyncTask(this)
            iteration++
        }
    }

}