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

    override fun hasPermission(invocation: SimpleCommand.Invocation): Boolean {
        cmd.getCommand(invocation.arguments().toList())?.let {
            return it.hasPermission(invocation.source())
        }
        return false
    }

    override fun suggestAsync(invocation: SimpleCommand.Invocation): CompletableFuture<MutableList<String>> {
        val current = if (invocation.arguments().isEmpty()) ""
            else invocation.arguments()[if (invocation.arguments().size - 1 > 0) invocation.arguments().size - 1 else 0]
        var args = invocation.arguments().toMutableList()
        args = if (args.size - 1 < 0) mutableListOf("") else args.subList(0, args.size - 1)
        cmd.getCommand(args)?.let {
            return CompletableFuture.completedFuture(
                it.getSuggetions(current, invocation.source()).toMutableList()
            )
        }
        return CompletableFuture.completedFuture(mutableListOf())
    }
}