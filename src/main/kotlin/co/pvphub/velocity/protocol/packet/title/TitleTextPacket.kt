package co.pvphub.velocity.protocol.packet.title

class TitleTextPacket(
    text: String? = null
) : GenericTitlePacket("TitleTextPacket") {

    init {
        createConstructor()
        text?.let { setComponent(it) }
    }

}