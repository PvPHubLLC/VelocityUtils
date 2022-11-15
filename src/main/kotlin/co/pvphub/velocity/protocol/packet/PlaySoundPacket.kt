package co.pvphub.velocity.protocol.packet

import co.pvphub.velocity.protocol.packet.sound.Sound
import co.pvphub.velocity.protocol.readString
import co.pvphub.velocity.protocol.readVarInt
import co.pvphub.velocity.protocol.writeString
import co.pvphub.velocity.protocol.writeVarInt
import com.velocitypowered.api.network.ProtocolVersion
import com.velocitypowered.proxy.connection.MinecraftSessionHandler
import com.velocitypowered.proxy.protocol.MinecraftPacket
import com.velocitypowered.proxy.protocol.ProtocolUtils
import io.netty.buffer.ByteBuf

class PlaySoundPacket(
    sound: Sound? = null,
    var x: Int = 0,
    var y: Int = 0,
    var z: Int = 0
) : MinecraftPacket {
    private var soundId: String? = null
    var category: Category = Category.MASTER
    var volume: Float = 1f
    var pitch: Float = 1f
    var seed: Long = (0..Long.MAX_VALUE).random()

    init {
        sound?.let { sound(it) }
    }

    fun sound(sound: Sound) : PlaySoundPacket {
        this.soundId = "minecraft:${sound.key}"
        return this
    }

    override fun decode(buf: ByteBuf, direction: ProtocolUtils.Direction, protocolVersion: ProtocolVersion) {
        soundId = buf.readString()
        if (protocolVersion.protocol > 47) {
            category = Category.values()[buf.readVarInt()]
        }

        x = (buf.readInt().toDouble() / 8.0).toInt()
        y = (buf.readInt().toDouble() / 8.0).toInt()
        z = (buf.readInt().toDouble() / 8.0).toInt()
        volume = buf.readFloat()
        if (protocolVersion.protocol < 210) {
            pitch = buf.readUnsignedByte().toFloat() / 63.0f
        } else {
            pitch = buf.readFloat()
        }

        if (protocolVersion.protocol >= 759) {
            seed = buf.readLong()
        }
    }

    override fun encode(buf: ByteBuf, p1: ProtocolUtils.Direction?, protocolVersion: ProtocolVersion) {
        buf.writeString(soundId!!)
        buf.writeVarInt(category.ordinal)
        buf.writeInt(x * 8)
        buf.writeInt(y * 8)
        buf.writeInt(z * 8)
        buf.writeFloat(volume)

        if (protocolVersion.protocol < 210) {
            buf.writeByte((pitch * 63.0f).toInt().toByte().toInt() and 255)
        } else {
            buf.writeFloat(pitch)
        }

        if (protocolVersion.protocol >= 759) {
            buf.writeLong(seed)
        }
    }

    override fun handle(p0: MinecraftSessionHandler?): Boolean {
        return false
    }

    enum class Category(val id: String){
        MASTER("master"),
        MUSIC("music"),
        RECORDS("record"),
        WEATHER("weather"),
        BLOCKS("block"),
        HOSTILE("hostile"),
        NEUTRAL("neutral"),
        PLAYERS("player"),
        AMBIENT("ambient"),
        VOICE("voice");

        open fun category(readString: String?): Category? {
            for (cat in values()) {
                if (cat.id == readString) return cat
            }
            return null
        }
    }
}