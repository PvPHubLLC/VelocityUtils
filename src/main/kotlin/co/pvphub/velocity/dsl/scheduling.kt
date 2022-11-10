package co.pvphub.velocity.scheduling

import co.pvphub.velocity.plugin.VelocityPlugin
import java.util.concurrent.TimeUnit

fun sync(plugin: VelocityPlugin, task: (SyncTask) -> Unit): SyncTask {
    val syncTask = SyncTask(plugin, task)
    syncTask.schedule()
    return syncTask
}

fun syncRepeat(
    plugin: VelocityPlugin,
    repeat: Long = 0,
    repeatUnit: TimeUnit = TimeUnit.MILLISECONDS,
    delay: Long = 0,
    delayUnit: TimeUnit = TimeUnit.MILLISECONDS,
    task: (SyncTask) -> Unit
): SyncTask {
    val syncTask = SyncTask(plugin, task)
        .repeat(repeat, repeatUnit)
        .delay(delay, delayUnit)
    syncTask.schedule()
    return syncTask
}

fun syncDelayed(
    plugin: VelocityPlugin,
    delay: Long = 0,
    delayUnit: TimeUnit = TimeUnit.MILLISECONDS,
    task: (SyncTask) -> Unit
): SyncTask {
    val syncTask = SyncTask(plugin, task)
        .delay(delay, delayUnit)
    syncTask.schedule()
    return syncTask
}

fun async(plugin: VelocityPlugin, task: (AsyncTask) -> Unit) : AsyncTask {
    val asyncTask = AsyncTask(plugin, task)
    asyncTask.schedule()
    return asyncTask
}

fun asyncRepeat(
    plugin: VelocityPlugin,
    repeat: Long = 0,
    repeatUnit: TimeUnit = TimeUnit.MILLISECONDS,
    delay: Long = 0,
    delayUnit: TimeUnit = TimeUnit.MILLISECONDS,
    task: (AsyncTask) -> Unit
): SyncTask {
    val asyncTask = AsyncTask(plugin, task)
        .repeat(repeat, repeatUnit)
        .delay(delay, delayUnit)
    asyncTask.schedule()
    return asyncTask
}

fun asyncDelayed(
    plugin: VelocityPlugin,
    delay: Long = 0,
    delayUnit: TimeUnit = TimeUnit.MILLISECONDS,
    task: (AsyncTask) -> Unit
): SyncTask {
    val asyncTask = AsyncTask(plugin, task)
        .delay(delay, delayUnit)
    asyncTask.schedule()
    return asyncTask
}