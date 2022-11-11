package co.pvphub.velocity.command

import co.pvphub.velocity.plugin.VelocityPlugin
import com.velocitypowered.api.command.CommandSource

open class SimpleCommandBuilder(
    var name: String = "",
    var permission: String? = null,
    vararg alias: String
) {
    var aliases = arrayListOf(*alias)
    val subCommands = arrayListOf<SimpleCommandBuilder>()
    var suggestSubCommands = false
    private var suggests: ((String) -> List<String>?)? = null
    private var execute: ((CommandSource, List<String>, String) -> Unit)? = null

    infix fun permission(permission: String) : SimpleCommandBuilder {
        this.permission = permission
        return this
    }

    infix fun alias(alias: String) : SimpleCommandBuilder {
        this.aliases.add(alias)
        return this
    }

    infix fun subCommand(commandBuilder: SimpleCommandBuilder) : SimpleCommandBuilder {
        subCommands.add(commandBuilder)
        return this
    }

    infix fun hasPermission(executor: CommandSource) : Boolean {
        return permission == null || executor.hasPermission(permission)
    }

    infix fun executes(execute: (CommandSource, List<String>, String) -> Unit) : SimpleCommandBuilder {
        this.execute = execute
        return this
    }

    fun executeFor(executor: CommandSource, args: List<String>, alias: String) {
        execute?.let { it(executor, args, alias) }
    }

    infix fun suggests(suggests: (String) -> List<String>?) : SimpleCommandBuilder {
        this.suggests = suggests
        return this
    }

    fun allAliases() : List<String> {
        return aliases.toMutableList() + name
    }

    infix fun getSuggestion(arg: String) : List<String> {
        suggests?.also {
            return it(arg) ?: listOf()
        } ?: run {
            if (suggestSubCommands)
                return subCommands
                    .map { it.allAliases() }
                    .flatten()
                    .filter { it.startsWith(arg) }
        }
        return listOf()
    }

    infix fun suggestSubCommands(value: Boolean) : SimpleCommandBuilder {
        this.suggestSubCommands = value
        return this
    }

    infix fun register(plugin: VelocityPlugin) {
        plugin.server.commandManager.register(name, SimpleCommandDummy(this), *aliases.toTypedArray())
    }

    fun isCommand(arg: String) : Boolean {
        return name.startsWith(arg) || aliases.any { it.startsWith(arg) }
    }

    fun getCurrentCommand(args: List<String>) : List<SimpleCommandBuilder> {
        if (args.isEmpty()) return listOf(this)
        val cmds = arrayListOf<SimpleCommandBuilder>()
        subCommands.forEach { cmd ->
            if (cmd.isCommand(args[0])) {
                if (args.size > 1) {
                    cmds.addAll(cmd.getCurrentCommand(args.subList(1, args.size)))
                } else cmds.add(cmd)
            }
        }
        return cmds
    }

}