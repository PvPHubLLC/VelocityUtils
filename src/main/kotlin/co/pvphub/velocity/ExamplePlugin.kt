package co.pvphub.velocity

import co.pvphub.velocity.command.oldliteral.arguments.greedyString
import co.pvphub.velocity.command.oldliteral.arguments.integer
import co.pvphub.velocity.command.oldliteral.arguments.string
import co.pvphub.velocity.command.oldliteral.dsl.command
import co.pvphub.velocity.command.oldliteral.register
import co.pvphub.velocity.dsl.simpleCommand
import co.pvphub.velocity.extensions.json
import co.pvphub.velocity.plugin.VelocityPlugin
import co.pvphub.velocity.protocol.*
import co.pvphub.velocity.protocol.packet.Disconnect
import co.pvphub.velocity.protocol.packet.DummyPacket
import co.pvphub.velocity.protocol.packet.title.TitleActionbarPacket
import co.pvphub.velocity.protocol.packet.title.TitleSubtitlePacket
import co.pvphub.velocity.protocol.packet.title.TitleTextPacket
import co.pvphub.velocity.scheduling.async
import co.pvphub.velocity.scheduling.asyncRepeat
import co.pvphub.velocity.util.colored
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.network.ProtocolVersion
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.text.Component
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
            aliases += "velocity-util"
            suggestSubCommands = true

            unknownSubcommand { source, _, _ ->
                source.sendMessage("&cUnknown sub command".colored())
            }

            subCommands += simpleCommand {
                name = "echo"
                permission = "velocity.util.echo"

                executes { source, args, _ ->
                    if (args.size == 1) {
                        source.sendMessage("&cSay something else!".colored())
                        return@executes
                    }
                    source.sendMessage(args.subList(1, args.size).joinToString(" ").colored())
                }
            }

            subCommands += simpleCommand {
                name = "help"
                permission = "velocity.util.command.help"
                aliases += "about"
                suggestSubCommands = true

                unknownSubcommand { source, _, _ ->
                    source.sendMessage("&cUnknown sub command".colored())
                }

                executes { source, _, _ ->
                    source.sendMessage("&7Help command".colored())
                }

                subCommands += simpleCommand {
                    name = "development"

                    executes { source, _, _ ->
                        source.sendMessage("&7Development help".colored())
                    }
                }
            }
        }.register(this)

        simpleCommand {
            name = "packet"
            suggestSubCommands = true

            subCommands += simpleCommand {
                name = "title"
                executes { source, _, _ ->
                    val titleTextPacket = TitleTextPacket("&cMade with packets".colored().json())
                    val titleSubtitlePacket = TitleSubtitlePacket("&7By MattMX".colored().json())
//                    val titleTimesPacket = TitleTimesPacket()
//                    titleTimesPacket.stay = 100
                    (source as Player).sendPackets(
                        titleTextPacket.get(),
                        titleSubtitlePacket.get(),
//                        titleTimesPacket.get()
                    )
                }
            }

            subCommands += simpleCommand {
                name = "actionbar"
                executes { source, _, _ ->
                    (source as Player).sendPacket(TitleActionbarPacket("&cTitle bar packet!".colored().json()).get())
                }
            }

            subCommands += simpleCommand {
                name = "disconnect"
                executes { source, _, _ ->
                    (source as Player).sendPacket(Disconnect("&cNaughty boy".colored().json()).get())
                }
            }

            subCommands += simpleCommand {
                name = "custompacket"
                executes { source, _, _ ->
                }
            }
        }.register(this)

        PacketType.PLAY.registerClientbound(DummyPacket(),
            mappings(0x40, ProtocolVersion.MINECRAFT_1_7_2,null,  false),
            mappings(0x1A, ProtocolVersion.MINECRAFT_1_9, null,  false),
            mappings(0x1B, ProtocolVersion.MINECRAFT_1_13,null,  false),
            mappings(0x1A, ProtocolVersion.MINECRAFT_1_14,null,  false),
            mappings(0x1B, ProtocolVersion.MINECRAFT_1_15,null,  false),
            mappings(0x1A, ProtocolVersion.MINECRAFT_1_16,null,  false),
            mappings(0x19, ProtocolVersion.MINECRAFT_1_16_2,null,  false),
            mappings(0x1A, ProtocolVersion.MINECRAFT_1_17,null,  false),
            mappings(0x17, ProtocolVersion.MINECRAFT_1_19,null,  false),
            mappings(0x19, ProtocolVersion.MINECRAFT_1_19_1,null,  false),
        )

        command<Player>("vmsg") {
            val recipient by string("recipient")
            val message by greedyString("message")

            runs {
                val target = this@ExamplePlugin.server.allPlayers.firstOrNull { it.username == recipient }
                target?.also {
                    source.sendMessage("&7[Me -> &a$recipient&7] &f".colored().append(Component.text(message)))
                    target.sendMessage("&7[&a${source.username}&7 -> Me] &f".colored().append(Component.text(message)))
                } ?: run { source.sendMessage("&cThe player $recipient is not online!".colored()) }
            }
        }.register(this.server)

        command<Player>("balls") {
            subcommand(command<Player>("test") {
                val msg by greedyString("message")

                runs {
                    source.sendMessage("&7You: &f$msg".colored())
                }
            })
            subcommand(command<Player>("add") {
                val first by integer("first")
                val second by integer("second")

                runs {
                    source.sendMessage("&7$first + $second = ${first + second}".colored())
                }
            })
        }.register(this.server)
    }

}