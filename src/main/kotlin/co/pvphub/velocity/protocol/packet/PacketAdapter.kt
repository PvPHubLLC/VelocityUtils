package co.pvphub.velocity.protocol.packet

import co.pvphub.velocity.reflect.ClassAccessor
import io.netty.buffer.ByteBuf
import java.lang.reflect.Method

abstract class PacketAdapter(
    clazzName: String = "protocol.MinecraftPacket"
) : ClassAccessor(clazzName) {

    fun getDecode() : Method = getMethodByClasses("decode", ByteBuf::class.java, Any::class.java, Any::class.java)

    fun getEncode() : Method = getMethodByClasses("encode", ByteBuf::class.java, Any::class.java, Any::class.java)
}