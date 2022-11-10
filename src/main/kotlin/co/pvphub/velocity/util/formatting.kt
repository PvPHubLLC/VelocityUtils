package co.pvphub.velocity.dsl

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.server.RegisteredServer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import java.util.regex.Pattern

private val serializer = LegacyComponentSerializer.builder()
    .character('&')
    .hexCharacter('#')
    .hexColors()
    .build()

fun String.colored(
    s: String,
    p: Player? = null,
    server: RegisteredServer? = null,
    vararg placeholders: Pair<String, String>
): TextComponent {
    return color(s, p, server, *placeholders)
}

fun color(
    s: String,
    p: Player? = null,
    server: RegisteredServer? = null,
    vararg placeholders: Pair<String, String>
): TextComponent {
    var s = s
    server?.let {
        s = s.replace("%server-name%", server.serverInfo.name)
            .replace("%server-connected%", Integer.toString(server.playersConnected.size))
            .replace("%server-online%", Integer.toString(server.playersConnected.size))
            .replace("%server-players%", Integer.toString(server.playersConnected.size))
    }
    p?.let {
        s = s.replace("%player%", p.username)
            .replace("%username%", p.username)
    }
    placeholders.forEach { s = s.replace(it.first, it.second) }
    return serializer.deserialize(s)
}

fun getSerializer(): LegacyComponentSerializer {
    return serializer
}

private val pattern = Pattern.compile("&#[a-f0-9]{6}|&[a-f0-9k-o]|&r", Pattern.CASE_INSENSITIVE)
fun strip(s: String): String {
    var s = s
    var match = pattern.matcher(s)
    while (match.find()) {
        val color = s.substring(match.start(), match.end())
        s = s.replace(color, "")
        match = pattern.matcher(s)
    }
    return s
}
