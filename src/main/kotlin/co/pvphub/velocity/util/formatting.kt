package co.pvphub.velocity.util

import co.pvphub.velocity.placeholders.PlaceholderManager
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.server.RegisteredServer
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import java.util.regex.Pattern

private val serializer = LegacyComponentSerializer.builder()
    .character('&')
    .hexCharacter('#')
    .hexColors()
    .build()

fun String.colored(
    p: Player? = null,
    server: RegisteredServer? = null,
    vararg placeholders: Pair<String, String>
): TextComponent {
    return color(this, p, server, *placeholders)
}

fun color(s: String) : TextComponent = color(s)
fun color(s: String, p: Player) : TextComponent = color(s, p)

fun color(
    s: String,
    p: Player? = null,
    server: RegisteredServer? = null,
    vararg placeholders: Pair<String, String>
): TextComponent {
    var str = PlaceholderManager.applyPlaceholders(s, p, server)
    placeholders.forEach { str = str.replace(it.first, it.second) }
    return serializer.deserialize(str)
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
