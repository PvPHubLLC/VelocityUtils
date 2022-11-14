package co.pvphub.velocity.protocol

import co.pvphub.velocity.extensions.json
import co.pvphub.velocity.reflect.ClassAccessor
import co.pvphub.velocity.util.colored
import com.velocitypowered.api.util.GameProfile
import io.netty.buffer.ByteBuf
import net.kyori.adventure.text.Component
import java.util.UUID

object ProtocolUtils : ClassAccessor("protocol.ProtocolUtils") {

    fun readVarInt(it: ByteBuf) : Int = staticMethod("readVarInt", it)
    fun readVarIntSafely(it: ByteBuf) : Int = staticMethod("readVarIntSafely", it)
    fun varIntBytes(it: ByteBuf) : Int = staticMethod("varIntBytes", it)
    fun writeVarInt(b: ByteBuf, i: Int) : Unit = staticMethod("writeVarInt", b, i)
    fun writeVarIntFull(b: ByteBuf, i: Int) : Unit = staticMethod("writeVarIntFull", b, i)
    fun write21BitVarInt(b: ByteBuf, i: Int) : Unit = staticMethod("write21BitVarInt", b, i)
    fun readString(it: ByteBuf) : String = staticMethod("readString", it)
    fun readStringCap(b: ByteBuf, i: Int) : String = staticMethod("readString", b, i)
    fun writeString(b: ByteBuf, c: CharSequence) {
        getMethodByClasses("writeString", ByteBuf::class.java, CharSequence::class.java).invoke(null, b, c)
    }
    fun readByteArray(it: ByteBuf) : Array<Byte> = staticMethod("readByteArray", it)
    fun readByteArrayCap(b: ByteBuf, c: Int) : Array<Byte> = staticMethod("readByteArray", b, c)
    fun writeByteArray(b: ByteBuf, a: Array<Byte>) : Unit = staticMethod("writeByteArray", b, a)
    fun readIntegerArray(it: ByteBuf) : Array<Int> = staticMethod("readIntegerArray", it)
    fun readUuid(it: ByteBuf) : UUID = staticMethod("readUuid", it)
    fun writeUuid(b: ByteBuf, u: UUID) : Unit = staticMethod("writeUuid", b, u)
    fun readUuidIntArray(it: ByteBuf) : UUID = staticMethod("readUuidIntArray", it)
    fun writeUuidIntArray(b: ByteBuf, u: UUID) : Unit = staticMethod("writeUuidIntArray", b, u)
    // readCompoundTag
    // writeCompoundTag
    fun readStringArray(it: ByteBuf) : Array<String> = staticMethod("readStringArray", it)
    fun writeStringArray(b: ByteBuf, s: Array<String>) : Unit = staticMethod("writeStringArray", b, s)
    fun writeProperties(b: ByteBuf, p: List<GameProfile.Property>) : Unit = staticMethod("writeProperties", b, p)
    fun readProperties(it: ByteBuf) : List<GameProfile.Property> = staticMethod("readProperties", it)

}

fun ByteBuf.readVarInt() : Int = ProtocolUtils.readVarInt(this)
fun ByteBuf.readVarIntSafely() : Int = ProtocolUtils.readVarIntSafely(this)
fun ByteBuf.varIntBytes() : Int = ProtocolUtils.varIntBytes(this)
fun ByteBuf.writeVarInt(varInt: Int) = ProtocolUtils.writeVarInt(this, varInt)
fun ByteBuf.writeVarIntFull(varInt: Int) = ProtocolUtils.writeVarIntFull(this, varInt)
fun ByteBuf.write21BitBVarInt(varInt: Int) = ProtocolUtils.write21BitVarInt(this, varInt)
fun ByteBuf.readString() : String = ProtocolUtils.readString(this)
fun ByteBuf.readString(cap: Int) : String = ProtocolUtils.readStringCap(this, cap)
fun ByteBuf.writeString(seq: CharSequence) = ProtocolUtils.writeString(this, seq)
fun ByteBuf.readByteArray() : Array<Byte> = ProtocolUtils.readByteArray(this)
fun ByteBuf.readByteArray(cap: Int) : Array<Byte> = ProtocolUtils.readByteArrayCap(this, cap)
fun ByteBuf.writeByteArray(arr: Array<Byte>) = ProtocolUtils.writeByteArray(this, arr)
fun ByteBuf.readIntArray() : Array<Int> = ProtocolUtils.readIntegerArray(this)
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