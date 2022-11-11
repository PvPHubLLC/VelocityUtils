package co.pvphub.velocity.extensions

import com.velocitypowered.api.proxy.Player
import org.simpleyaml.configuration.file.YamlConfiguration
import java.util.UUID

fun YamlConfiguration.setPlayer(key: String, player: Player) {
    set(key, player.uniqueId.toString())
}

fun YamlConfiguration.getPlayer(key: String) : UUID {
    return UUID.fromString(getString(key))
}