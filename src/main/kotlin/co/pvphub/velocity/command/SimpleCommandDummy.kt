package co.pvphub.velocity.command

import com.velocitypowered.api.command.SimpleCommand
import java.util.concurrent.CompletableFuture

class SimpleCommandDummy(
    private val cmd: SimpleCommandBuilder
) : SimpleCommand {
    override fun execute(invocation: SimpleCommand.Invocation) {
        cmd.getCommand(invocation.arguments().toList())?.also {
            it.executeFor(invocation.source(), invocation.arguments().toList(), invocation.alias())
        } ?: run {
            cmd.unknown(invocation.source(), invocation.arguments().toList(), invocation.alias())
        }
    }

    override fun hasPermission(invocation: SimpleCommand.Invocation?): Boolean {
        return super.hasPermission(invocation)
    }

    override fun suggestAsync(invocation: SimpleCommand.Invocation): CompletableFuture<MutableList<String>> {
        // todo return the suggestions
        val cmds = cmd.getCommands(invocation.arguments().toList())
        if (cmds.isEmpty()) return CompletableFuture()
        return CompletableFuture()
    }
}