package co.pvphub.velocity.protocol.packet

import co.pvphub.velocity.reflect.ClassAccessor
import co.pvphub.velocity.reflect.velocityClass
import io.netty.buffer.ByteBuf
import java.lang.reflect.Method

abstract class PacketAdapter(
    clazzName: String = "protocol.MinecraftPacket"
) : ClassAccessor(clazzName)