package co.pvphub.velocity

import co.pvphub.velocity.dsl.simpleCommand
import co.pvphub.velocity.plugin.VelocityPlugin
import co.pvphub.velocity.scheduling.async
import co.pvphub.velocity.scheduling.asyncRepeat
import co.pvphub.velocity.util.colored
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import java.nio.file.Path
import java.util.logging.Logger
import javax.inject.Inject

@Plugin(
    id = "velocityutil",
    name = "VelocityUtil",
    version = "1.0",
    description = "Development utilities for Velocity!",
    authors = ["MattMX"]
)
class ExamplePlugin @Inject constructor(
    server: ProxyServer,
    logger: Logger,
    @DataDirectory
    dataDirectory: Path
) : VelocityPlugin(server, logger, dataDirectory) {

    @Subscribe
    fun onProxyInitialize(e: ProxyInitializeEvent) {
        async(this) {
            logger.info("This message is asynchronous thanks to VelocityUtils!")
        }
        asyncRepeat(this, 1000) {
            logger.info("Repeated ${it.iteration + 1} times with 1s delay!")
            if (it.iteration >= 4) it.cancel()
        }

        simpleCommand {
            name = "velocityutil"
            permission = "velocity.util.command"
            aliases = arrayListOf("velocity-util")
            suggestSubCommands = true

            subCommands += simpleCommand {
                name = "help"
                permission = "velocity.util.command.help"
                executes { commandSource, args, alias ->
                    commandSource.sendMessage("&7Help command".colored())
                }
            }

        }.register(this)
    }

}