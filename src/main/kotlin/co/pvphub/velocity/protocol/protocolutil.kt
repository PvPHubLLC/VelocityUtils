package co.pvphub.velocity.protocol

import co.pvphub.velocity.extensions.json
import co.pvphub.velocity.util.colored
import com.velocitypowered.api.util.GameProfile
import com.velocitypowered.proxy.protocol.ProtocolUtils
import io.netty.buffer.ByteBuf
import net.kyori.adventure.text.Component
import java.util.UUID

fun ByteBuf.readVarInt() : Int = ProtocolUtils.readVarInt(this)
fun ByteBuf.readVarIntSafely() : Int = ProtocolUtils.readVarIntSafely(this)
fun ByteBuf.varIntBytes(value: Int) : Int = ProtocolUtils.varIntBytes(value)
fun ByteBuf.writeVarInt(varInt: Int) = ProtocolUtils.writeVarInt(this, varInt)
fun ByteBuf.write21BitBVarInt(varInt: Int) = ProtocolUtils.write21BitVarInt(this, varInt)
fun ByteBuf.readString() : String = ProtocolUtils.readString(this)
fun ByteBuf.readString(cap: Int) : String = ProtocolUtils.readString(this, cap)
fun ByteBuf.writeString(seq: CharSequence) = ProtocolUtils.writeString(this, seq)
fun ByteBuf.readByteArray() : ByteArray = ProtocolUtils.readByteArray(this)
fun ByteBuf.readByteArray(cap: Int) : ByteArray = ProtocolUtils.readByteArray(this, cap)
fun ByteBuf.writeByteArray(arr: ByteArray) = ProtocolUtils.writeByteArray(this, arr)
fun ByteBuf.readIntArray() : IntArray = ProtocolUtils.readIntegerArray(this)
fun ByteBuf.readUuid() : UUID = ProtocolUtils.readUuid(this)
fun ByteBuf.writeUuid(uuid: UUID) = ProtocolUtils.writeUuid(this, uuid)
fun ByteBuf.readUuidIntArray() : UUID = ProtocolUtils.readUuidIntArray(this)
fun ByteBuf.writeUuidIntArray(uuid: UUID) = ProtocolUtils.writeUuidIntArray(this, uuid)
fun ByteBuf.readStringArray() : Array<String> = ProtocolUtils.readStringArray(this)
fun ByteBuf.writeStringArray(arr: Array<String>) = ProtocolUtils.writeStringArray(this, arr)
fun ByteBuf.writeProperties(prop: List<GameProfile.Property>) = ProtocolUtils.writeProperties(this,  prop)
fun ByteBuf.readProperties() : List<GameProfile.Property> = ProtocolUtils.readProperties(this)
fun ByteBuf.writeComponent(component: Component) = writeString(component.json())
fun ByteBuf.readComponent() : Component = readString().colored()