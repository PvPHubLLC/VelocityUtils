package co.pvphub.velocity.protocol.packet.title

class TitleActionbarPacket(
    text: String? = null
) : GenericTitlePacket("TitleActionbarPacket") {

    init {
        createConstructor()
        text?.let { setComponent(it) }
    }

}