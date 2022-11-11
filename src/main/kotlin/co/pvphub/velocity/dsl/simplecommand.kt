package co.pvphub.velocity.dsl

import co.pvphub.velocity.command.SimpleCommandBuilder

inline fun simpleCommand(cmd: (SimpleCommandBuilder.() -> Unit)) : SimpleCommandBuilder {
    val cmdB = SimpleCommandBuilder()
    cmd(cmdB)
    return cmdB
}

