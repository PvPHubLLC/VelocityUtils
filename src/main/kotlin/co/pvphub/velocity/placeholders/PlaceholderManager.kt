package co.pvphub.velocity.placeholders

import co.pvphub.velocity.placeholders.builtin.PlayerPlaceholders
import co.pvphub.velocity.placeholders.builtin.ServerPlaceholders
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.server.RegisteredServer

object PlaceholderManager {
    private val adapters = arrayListOf<PlaceholderAdapter>()

    init {
        this += PlayerPlaceholders()
        this += ServerPlaceholders()
    }

    /**
     * Call to apply all placeholders to a string.
     *
     * @param string to format
     * @param player player we want to replace for
     * @param server that we want to replace for
     * @return the formatted string
     */
    fun applyPlaceholders(string: String, player: Player? = null, server: RegisteredServer? = null) : String {
        var exposedStr = string
        adapters.forEach { exposedStr = it.apply(exposedStr, player, server) }
        return exposedStr
    }

    /**
     * To register your own placeholders call [register] or [plusAssign]
     *
     * @param adapter your placeholder adapter to register
     */
    operator fun plusAssign(adapter: PlaceholderAdapter) {
        register(adapter)
    }

    fun register(adapter: PlaceholderAdapter) {
        adapters += adapter
    }

}

/**
 * Extension function to easily apply placeholders.
 * Simply calls [PlaceholderManager.applyPlaceholders]
 */
fun String.applyPlaceholders(player: Player? = null, server: RegisteredServer? = null) : String {
    return PlaceholderManager.applyPlaceholders(this, player, server)
}