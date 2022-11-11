package co.pvphub.velocity.command

import com.velocitypowered.api.command.SimpleCommand

class Temp : SimpleCommand {
    override fun execute(invocation: SimpleCommand.Invocation) {
        invocation.arguments()
        invocation.source()
        invocation.alias()
    }
}