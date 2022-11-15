package co.pvphub.velocity.dsl

import co.pvphub.velocity.plugin.VelocityPlugin
import com.velocitypowered.api.event.EventHandler
import com.velocitypowered.api.event.EventTask
import com.velocitypowered.api.event.PostOrder

class DummyHandler<E>(
    private val block: E.() -> Unit
) : EventHandler<E> {
    override fun execute(event: E) {
        block.invoke(event)
    }

    override fun executeAsync(event: E): EventTask {
        return EventTask.async {
            block.invoke(event)
        }
    }

}

@JvmName("event1")
inline fun <reified T> event(
    plugin: VelocityPlugin,
    postOrder: PostOrder = PostOrder.NORMAL,
    noinline block: T.() -> Unit
) {
    plugin.server.eventManager.register(plugin, T::class.java, postOrder, DummyHandler(block))
}

inline fun <reified T> VelocityPlugin.event(
    postOrder: PostOrder = PostOrder.NORMAL,
    noinline block: T.() -> Unit
) {
    event(this, postOrder, block)
}