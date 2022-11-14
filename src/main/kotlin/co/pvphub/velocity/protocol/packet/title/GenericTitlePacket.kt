package co.pvphub.velocity.protocol.packet.title

import co.pvphub.velocity.extensions.json
import co.pvphub.velocity.protocol.packet.PacketAdapter
import net.kyori.adventure.text.TextComponent

open class GenericTitlePacket(
    name: String
) : PacketAdapter("protocol.packet.title.$name") {

    fun setComponent(it: String) { instanceMethodVoid("setComponent", instance, it) }
    fun setTextComponent(it: TextComponent) = setComponent(it.json())
    fun getComponent() : String = instanceMethod("getComponent", instance)

}