package co.pvphub.velocity.classloading

import co.pvphub.velocity.plugin.VelocityPlugin
import co.pvphub.velocity.scheduling.async
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.net.URLClassLoader
import java.nio.channels.Channels


object DependencyManager {

    inline fun downloadAsync(plugin: VelocityPlugin, url: URL, location: File, crossinline then: Dependency.() -> Unit) {
        async(plugin) {
            val dep = download(url, location)
            then(dep)
        }
    }

    fun download(url: URL, location: File): Dependency {
        if (!location.exists()) {
            location.parentFile.mkdirs()
            location.createNewFile()
            url.openStream().use {
                Channels.newChannel(it).use { rbc ->
                    FileOutputStream(location).use { fos ->
                        fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                    }
                }
            }
        }
        return Dependency(location)
    }

    class Common {
        enum class Storage(
            val url: URL,
            val fileName: String,
            val className: String
        ) {
            SQLITE(URL("https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.39.4.1/sqlite-jdbc-3.39.4.1.jar"),
                "sqlite.jar", "org.sqlite.JDBC"),
            MYSQL(URL("https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.31/mysql-connector-j-8.0.31.jar"),
            "mysql.jar", "com.mysql.cj.jdbc.Driver");
            inline fun get(location: File, then: Dependency.() -> Unit) {
                then(download(url, File(location, fileName)).load(className))
            }
            inline fun getAsync(plugin: VelocityPlugin, location: File, crossinline then: Dependency.() -> Unit) {
                downloadAsync(plugin, url, File(location, fileName)) {
                    loadAsync(plugin, className, then)
                }
            }
        }
    }
}

class Dependency(
    private val location: File
) {

    fun load(className: String) : Dependency {
        val child = URLClassLoader(
            arrayOf<URL>(location.toURI().toURL()),
            this.javaClass.classLoader
        )
        val classToLoad = Class.forName(className, true, child)
        return this
    }

    inline fun loadAsync(plugin: VelocityPlugin, className: String, crossinline then: Dependency.() -> Unit) {
        async(plugin) {
            load(className)
            then(this)
        }
    }

}