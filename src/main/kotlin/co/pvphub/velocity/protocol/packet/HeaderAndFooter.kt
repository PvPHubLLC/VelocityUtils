package co.pvphub.velocity.protocol.packet

class HeaderAndFooter(
    header: String,
    footer: String
) : PacketAdapter("procotol.packet.HeaderAndFooter") {

    init {
        createConstructor(header, footer)
    }

    fun header() : String? = instanceMethod("getHeader", instance)
    fun footer() : String? = instanceMethod("getFooter", instance)

}