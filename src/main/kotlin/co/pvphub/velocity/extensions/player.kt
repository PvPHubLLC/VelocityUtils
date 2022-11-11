package co.pvphub.velocity.extensions

import co.pvphub.velocity.plugin.VelocityPlugin
import co.pvphub.velocity.scheduling.async
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.server.RegisteredServer

fun Player.tryConnect(
    server: RegisteredServer,
    plugin: VelocityPlugin,
    checkIfAvailable: Boolean = false,
    cb: (SendResult) -> Unit
) {
    async(plugin) {
        // Try and ping the server, check if available
        if (checkIfAvailable) {
            try {
                server.ping().join()
            } catch (e: Exception) {
                cb(SendResult.FAIL_UNAVAILABLE)
                return@async
            }
        }
        // Try and connect the player
        try {
            val result = createConnectionRequest(server).connect()
            if (!result.get().isSuccessful) {
                cb(SendResult.SUCCESS)
            }
        } catch (e: Exception) {
            cb(SendResult.FAIL_OTHER)
        }
    }
}

enum class SendResult {
    FAIL_UNAVAILABLE,
    FAIL_OTHER,
    SUCCESS
}