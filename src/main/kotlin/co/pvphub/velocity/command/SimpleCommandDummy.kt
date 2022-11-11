package co.pvphub.velocity.command

import com.velocitypowered.api.command.SimpleCommand
import java.util.concurrent.CompletableFuture

class SimpleCommandDummy(
    private val cmd: SimpleCommandBuilder
) : SimpleCommand {
    override fun execute(invocation: SimpleCommand.Invocation?) {
        TODO("Not yet implemented")
    }

    override fun hasPermission(invocation: SimpleCommand.Invocation?): Boolean {
        return super.hasPermission(invocation)
    }

    override fun suggestAsync(invocation: SimpleCommand.Invocation): CompletableFuture<MutableList<String>> {
        val cmds = cmd.getCurrentCommand(invocation.arguments().toList())
        if (cmds.isEmpty()) return CompletableFuture()
        return CompletableFuture()
    }
}