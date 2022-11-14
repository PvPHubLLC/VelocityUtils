package co.pvphub.velocity.protocol.packet.title

class TitleSubtitlePacket(
    text: String? = null
) : GenericTitlePacket("TitleSubtitlePacket") {

    init {
        createConstructor()
        text?.let { setComponent(it) }
    }

}