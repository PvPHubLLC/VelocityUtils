package co.pvphub.velocity.extensions

import org.simpleyaml.configuration.file.YamlConfiguration

fun String.translatable(config: YamlConfiguration) : String {
    return config.getString(this)
}

fun String.translatableList(config: YamlConfiguration) : List<String> {
    return config.getStringList(this)
}