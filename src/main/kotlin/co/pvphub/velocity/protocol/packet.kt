package co.pvphub.velocity.protocol

import co.pvphub.velocity.reflect.ClassAccessor
import co.pvphub.velocity.reflect.velocityClass
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.RegisteredServer
import com.velocitypowered.proxy.connection.client.ConnectedPlayer
import io.netty.buffer.*

val minecraftPacketClass = "protocol.MinecraftPacket".velocityClass()!!

fun packetByName(name: String, vararg args: Any) : Any {
    val packetByName = "protocol.packet.$name".velocityClass()
    val inst = packetByName?.getConstructor(*args.map { it::class.java }.toTypedArray())
    return inst!!.newInstance(*args)
}

fun packet(cb: ByteBuf.() -> Unit) : ByteBuf {
    val by = Unpooled.buffer()
    cb(by)
    return by
}

val connectedPlayerClass = "connection.client.ConnectedPlayer".velocityClass()!!
fun Player.sendPacket(packet: Any) {
    val casted = connectedPlayerClass.cast(this)
    val con = casted::class.java.getMethod("getConnection").invoke(casted)
    con::class.java.getMethod("write", Object::class.java).invoke(con, packet)
//    (this as ConnectedPlayer).connection.write(packet)
}

fun Player.sendPackets(vararg packets: Any) {
    packets.forEach { sendPacket(it) }
}

fun ProxyServer.broadcastPacket(packet: Any) {
    allServers.forEach { it.broadcastPacket(packet) }
}

fun RegisteredServer.broadcastPacket(packet: Any) {
    playersConnected.broadcastPacket(packet)
}

fun Collection<Player>.broadcastPacket(packet: Any) {
    forEach { it.sendPacket(packet) }
}