package co.pvphub.velocity.protocol.packet

import co.pvphub.velocity.protocol.writeUuid
import co.pvphub.velocity.protocol.writeVarInt
import com.velocitypowered.api.network.ProtocolVersion
import com.velocitypowered.proxy.connection.MinecraftSessionHandler
import com.velocitypowered.proxy.protocol.MinecraftPacket
import com.velocitypowered.proxy.protocol.ProtocolUtils
import io.netty.buffer.ByteBuf
import java.util.*

class TestPacket : MinecraftPacket {
    var entityId: Int = 0
    var uuid: UUID = UUID.randomUUID()
    var x: Double = 0.0
    var y: Double = 0.0
    var z: Double = 0.0
    var yaw: Double = 0.0
    var pitch: Double = 0.0

    override fun decode(p0: ByteBuf, p1: ProtocolUtils.Direction, p2: ProtocolVersion) {

    }

    override fun encode(buf: ByteBuf, p1: ProtocolUtils.Direction, p2: ProtocolVersion) {
        buf.writeVarInt(entityId)
        buf.writeUuid(uuid)
        buf.writeDouble(x)
        buf.writeDouble(y)
        buf.writeDouble(z)
        buf.writeDouble(yaw)
        buf.writeDouble(pitch)
    }

    override fun handle(p0: MinecraftSessionHandler): Boolean {
        return false
    }
}