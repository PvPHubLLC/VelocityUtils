package co.pvphub.velocity.protocol.packet

import com.velocitypowered.api.network.ProtocolVersion
import io.netty.buffer.ByteBuf

class DummyPacket : MinecraftPacket() {

    override fun encode(buf: ByteBuf, direction: Any, protocolVersion: ProtocolVersion) {
    }

}