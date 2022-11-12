package co.pvphub.velocity

import co.pvphub.velocity.command.literal.arguments.greedyString
import co.pvphub.velocity.command.literal.arguments.string
import co.pvphub.velocity.command.literal.dsl.command
import co.pvphub.velocity.dsl.simpleCommand
import co.pvphub.velocity.plugin.VelocityPlugin
import co.pvphub.velocity.scheduling.async
import co.pvphub.velocity.scheduling.asyncRepeat
import co.pvphub.velocity.util.colored
import com.mojang.brigadier.CommandDispatcher
import com.velocitypowered.api.command.BrigadierCommand
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ConsoleCommandSource
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.text.Component
import java.awt.TextComponent
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

        // todo test and impl https://github.com/nicolaic/brigadier-dsl
        simpleCommand {
            name = "velocityutil"
            permission = "velocity.util.command"
            aliases = arrayListOf("velocity-util")
            suggestSubCommands = true

            unknownSubcommand { source, args, alias ->
                source.sendMessage("&cUnknown sub command".colored())
            }

            subCommands += simpleCommand {
                name = "help"
                permission = "velocity.util.command.help"
                suggestSubCommands = true

                executes { source, args, alias ->
                    source.sendMessage("&7Help command".colored())
                }
                subCommands += simpleCommand {
                    name = "development"

                    executes { source, args, alias ->
                        source.sendMessage("&7Development help".colored())
                    }
                }
            }
        }.register(this)

        val msg = command<Player>("vmsg") {
            val recipient by string("recipient")
            val message by greedyString("message")

            runs {
                val target = this@ExamplePlugin.server.allPlayers.firstOrNull { it.username == recipient }
                target?.also {
                    source.sendMessage("&7[Me -> &a$recipient&7] &f".colored().append(Component.text(message)))
                    target.sendMessage("&7[&a${source.username}&7 -> Me] &f".colored().append(Component.text(message)))
                } ?: run { source.sendMessage("&cThe player $recipient is not online!".colored()) }
            }
            subcommand(command<Player>("last") {
                val message by greedyString("message")

                runs {
                    source.sendMessage("test".colored())
                }
            })
        }
//       server.commandManager.register(BrigadierCommand(msg.buildLiteral()))
    }

}