package co.pvphub.velocity.protocol.packet

class KeepAlive : PacketAdapter("procotol.packet.KeepAlive") {

    init {
        createConstructor()
    }

    fun id(id: Long) = instanceMethodVoid("setRandomId", instance, id)
    fun id() : Long = instanceMethod("getRandomId", instance)
    fun generate() {
        id(System.nanoTime() / 1000000L)
    }

}