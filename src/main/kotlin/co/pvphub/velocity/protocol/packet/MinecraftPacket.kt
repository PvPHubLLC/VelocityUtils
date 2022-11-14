package co.pvphub.velocity.protocol.packet

import co.pvphub.velocity.reflect.ClassAccessor
import com.velocitypowered.api.network.ProtocolVersion
import io.netty.buffer.ByteBuf
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

abstract class MinecraftPacket {

    open fun decode(buf: ByteBuf, direction: Any, protocolVersion: ProtocolVersion) {
    }

    open fun encode(buf: ByteBuf, direction: Any, protocolVersion: ProtocolVersion) {
    }

    fun create() : Any {
        val classAccessor = ClassAccessor("protocol.MinecraftPacket")
        println(classAccessor.clazz().classLoader)
        return Proxy.newProxyInstance(
            classAccessor.clazz().classLoader,
            arrayOf(classAccessor.clazz()),
        ) { proxy, method, args ->
            val methodName = method.name
            val classArgs = method.parameterTypes

            if (methodName == "decode") {
                if (args[0] !is ByteBuf || args[2] !is ProtocolVersion) Unit
                decode(args[0] as ByteBuf, args[1], args[2] as ProtocolVersion)
            } else if (methodName == "encode") {
                if (args[0] !is ByteBuf || args[2] !is ProtocolVersion) Unit
                encode(args[0] as ByteBuf, args[1], args[2] as ProtocolVersion)
            }
            Unit
        }
    }

    companion object {
        fun <T : MinecraftPacket> cast(packet: T) : Any {
            val classAccessor = ClassAccessor("protocol.MinecraftPacket")

            return Proxy.newProxyInstance(
                classAccessor.clazz().classLoader,
                arrayOf(classAccessor.clazz()),
            ) { proxy, method, args ->
                val methodName = method.name
                val classArgs = method.parameterTypes

                if (methodName == "decode") {
                    if (args[0] !is ByteBuf || args[2] !is ProtocolVersion) Unit
                    packet.decode(args[0] as ByteBuf, args[1], args[2] as ProtocolVersion)
                } else if (methodName == "encode") {
                    if (args[0] !is ByteBuf || args[2] !is ProtocolVersion) Unit
                    packet.decode(args[0] as ByteBuf, args[1], args[2] as ProtocolVersion)
                }
                Unit
            }
        }
    }

}