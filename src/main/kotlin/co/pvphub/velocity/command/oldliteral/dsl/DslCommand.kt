package co.pvphub.velocity.command.oldliteral.dsl

/*
 * Copyright 2020-present Nicolai Christophersen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import co.pvphub.velocity.command.oldliteral.Command
import co.pvphub.velocity.command.oldliteral.CommandArgument
import co.pvphub.velocity.command.oldliteral.ExecutableCommand
import com.velocitypowered.api.command.CommandSource
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import com.mojang.brigadier.Command as BrigadierCommand

open class DslCommand<S>(
    private val literal: String,
    private val apply: (LiteralArgumentBuilder<S>.() -> Unit)? = null,
    private val block: (DslCommandBuilder<S>.() -> Unit),
) : Command<S> where S : CommandSource {
    constructor(
        literal: String, builderBlock: DslCommandBuilder<S>.() -> Unit,
    ) : this(literal, null, builderBlock)

    override fun buildLiteral() : LiteralArgumentBuilder<S> {
        val dslNode = LiteralNode<S>(literal, ContextRef())
        dslNode.apply(apply)

        val builder = DslCommandBuilder(dslNode)
        block(builder)

        return dslNode.buildTree()
    }
}

class DslCommandBuilder<S : CommandSource>(
    private var dslNode: DslCommandTree<S, *>,
) {
    fun executes(command: BrigadierCommand<S>) {
        dslNode.executes(command)
    }

    infix fun runs(command: ExecutableCommand<S>) {
        dslNode.runs(command)
    }

    fun literal(
        literal: String,
        apply: (LiteralArgumentBuilder<S>.() -> Unit)? = null,
        block: (DslCommandBuilder<S>.() -> Unit)? = null,
    ): DslCommandBuilder<S> {
        val literalNode = dslNode.literal(literal, apply)

        return DslCommandBuilder(literalNode).also { block?.invoke(it) }
    }

    fun literals(
        vararg literals: String,
        block: (DslCommandBuilder<S>.() -> Unit)? = null,
    ): DslCommandBuilder<S> {
        val literalNode = literals.fold(dslNode) { node, literal -> node.literal(literal, null) }

        return DslCommandBuilder(literalNode).also { block?.invoke(it) }
    }

    operator fun <T, V> CommandArgument<S, T, V>.provideDelegate(
        thisRef: Any?,
        property: KProperty<*>,
    ): ReadOnlyProperty<Any?, V> {
        val argument = dslNode.argument(this, null).also { dslNode = it }
        return ReadOnlyProperty { _, _ -> argument.getter() }
    }

    infix fun subcommand(command: Command<S>) {
        dslNode.subcommands(command)
    }

    fun subcommands(vararg commands: Command<S>) {
        dslNode.subcommands(*commands)
    }

    operator fun plus(command: Command<S>) {
        dslNode.subcommands(command)
    }
}

fun <S> command(
    literal: String,
    apply: (LiteralArgumentBuilder<S>.() -> Unit)? = null,
    block: DslCommandBuilder<S>.() -> Unit,
) where S : CommandSource = DslCommand(literal, apply, block)