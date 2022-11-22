package co.pvphub.velocity.placeholders

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.server.RegisteredServer

interface PlaceholderAdapter {

    fun apply(string: String, player: Player? = null, server: RegisteredServer? = null) : String

}