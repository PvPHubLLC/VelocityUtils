package co.pvphub.velocity.placeholders.builtin

import co.pvphub.velocity.placeholders.PlaceholderAdapter
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.server.RegisteredServer

class ServerPlaceholders : PlaceholderAdapter {

    override fun apply(string: String, player: Player?, server: RegisteredServer?): String {
        server?.let {
            return string.replace("%server%", it.serverInfo.name)
                .replace("%server-name%", it.serverInfo.name)
                .replace("%server-ip%", it.serverInfo.address.toString())
                .replace("%server-players%", it.playersConnected.size.toString())
                .replace("%server-connected%", it.playersConnected.size.toString())
                .replace("%server-online%", it.playersConnected.size.toString())
        }
        return string
    }

}