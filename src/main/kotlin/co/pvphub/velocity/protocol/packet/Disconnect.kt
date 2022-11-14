package co.pvphub.velocity.protocol.packet

class Disconnect(
    reason: String? = null
) : PacketAdapter("protocol.packet.Disconnect") {

    init {
        createConstructor(reason ?: "")
    }

}