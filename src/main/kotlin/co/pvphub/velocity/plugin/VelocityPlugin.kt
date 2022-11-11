package co.pvphub.velocity.plugin

import com.velocitypowered.api.proxy.ProxyServer
import org.simpleyaml.configuration.file.YamlConfiguration
import java.io.*
import java.nio.file.Path
import java.util.logging.Logger

open class VelocityPlugin(
    val server: ProxyServer,
    val logger: Logger,
    val dataDirectory: Path
) {
    var config: YamlConfiguration? = null
        private set

    fun saveResource(resourcePath: String, replace: Boolean) {
        require(resourcePath != "") { "ResourcePath cannot be null or empty" }
        val resourcePath = resourcePath.replace('\\', '/')
        val `in` = javaClass.classLoader.getResourceAsStream(resourcePath)
            ?: throw IllegalArgumentException("The embedded resource '$resourcePath' cannot be found")
        val outFile = File(this.dataDirectory.toAbsolutePath().toString(), resourcePath)
        val lastIndex = resourcePath.lastIndexOf('/')
        val outDir = File(
            this.dataDirectory.toAbsolutePath().toString(),
            resourcePath.substring(0, if (lastIndex >= 0) lastIndex else 0)
        )
        if (!outDir.exists()) {
            outDir.mkdirs()
        }
        try {
            if (!outFile.exists() || replace) {
                val out: OutputStream = FileOutputStream(outFile)
                val buf = ByteArray(1024)
                var len: Int
                while (`in`.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
                out.close()
                `in`.close()
            } else {
            }
        } catch (ex: IOException) {
        }
    }

    fun getResource(filename: String): InputStream? {
        return try {
            val url = javaClass.getResource(filename) ?: return null
            val connection = url.openConnection()
            connection.useCaches = false
            connection.getInputStream()
        } catch (ex: IOException) {
            null
        }
    }

    fun saveDefaultConfig() {
        config = createOrLoadConfig("${dataDirectory}/config.yml", "config.yml")
    }

    fun createOrLoadConfig(path: String, def: String? = null): YamlConfiguration {
        val file = File(path)
        if (!file.exists()) {
            try {
                if (def != null) {
                    this.saveResource(def, false)
                } else {
                    file.createNewFile()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return YamlConfiguration.loadConfiguration(file)
    }
}