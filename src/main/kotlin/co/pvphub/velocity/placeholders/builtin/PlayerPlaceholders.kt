package co.pvphub.velocity.placeholders.builtin

import co.pvphub.velocity.placeholders.PlaceholderAdapter
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.server.RegisteredServer

class PlayerPlaceholders : PlaceholderAdapter{

    override fun apply(string: String, player: Player?, server: RegisteredServer?): String {
        player?.let {
            val playerServer = if (player.currentServer.isPresent) player.currentServer.get() else null
            return string.replace("%player%", player.username)
                .replace("%player-uuid%", player.uniqueId.toString())
                .replace("%player-locale%", player.effectiveLocale.toString())
                .replace("%player-server%", playerServer?.serverInfo?.name ?: "null")
                .replace("%player-server-players%", playerServer?.server?.playersConnected?.size?.toString() ?: "null")
                .replace("%player-server-ip%", playerServer?.server?.serverInfo?.address?.toString() ?: "null")
                .replace("%player-ping%", player.ping.toString())
                .replace("%player-online-mode%", player.isOnlineMode.toString())
                .replace("%player-clientbrand%", player.clientBrand ?: "vanilla")
        }
        return string
    }

}