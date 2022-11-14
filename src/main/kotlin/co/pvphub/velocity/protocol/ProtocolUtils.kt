package co.pvphub.velocity.protocol

import co.pvphub.velocity.reflect.ClassAccessor
import com.velocitypowered.api.util.GameProfile
import io.netty.buffer.ByteBuf
import java.util.UUID

object ProtocolUtils : ClassAccessor("protocol.ProtocolUtils") {

    fun readVarInt(it: ByteBuf) : Int = genericMethod("readVarInt", it)
    fun readVarIntSafely(it: ByteBuf) : Int = genericMethod("readVarIntSafely", it)
    fun varIntBytes(it: ByteBuf) : Int = genericMethod("varIntBytes", it)
    fun writeVarInt(b: ByteBuf, i: Int) : Unit = genericMethod("writeVarInt", b, i)
    fun writeVarIntFull(b: ByteBuf, i: Int) : Unit = genericMethod("writeVarIntFull", b, i)
    fun write21BitVarInt(b: ByteBuf, i: Int) : Unit = genericMethod("write21BitVarInt", b, i)
    fun readString(it: ByteBuf) : String = genericMethod("readString", it)
    fun readStringCap(b: ByteBuf, i: Int) : String = genericMethod("readString", b, i)
    fun writeString(b: ByteBuf, c: CharSequence) : Unit = genericMethod("writeString", b, c)
    fun readByteArray(it: ByteBuf) : Array<Byte> = genericMethod("readByteArray", it)
    fun readByteArrayCap(b: ByteBuf, c: Int) : Array<Byte> = genericMethod("readByteArray", b, c)
    fun writeByteArray(b: ByteBuf, a: Array<Byte>) : Unit = genericMethod("writeByteArray", b, a)
    fun readIntegerArray(it: ByteBuf) : Array<Int> = genericMethod("readIntegerArray", it)
    fun readUuid(it: ByteBuf) : UUID = genericMethod("readUuid", it)
    fun writeUuid(b: ByteBuf, u: UUID) : Unit = genericMethod("writeUuid", b, u)
    fun readUuidIntArray(it: ByteBuf) : UUID = genericMethod("readUuidIntArray", it)
    fun writeUuidIntArray(b: ByteBuf, u: UUID) : Unit = genericMethod("writeUuidIntArray", b, u)
    // readCompoundTag
    // writeCompoundTag
    fun readStringArray(it: ByteBuf) : Array<String> = genericMethod("readStringArray", it)
    fun writeStringArray(b: ByteBuf, s: Array<String>) : Unit = genericMethod("writeStringArray", b, s)
    fun writeProperties(b: ByteBuf, p: List<GameProfile.Property>) : Unit = genericMethod("writeProperties", b, p)
    fun readProperties(it: ByteBuf) : List<GameProfile.Property> = genericMethod("readProperties", it)

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