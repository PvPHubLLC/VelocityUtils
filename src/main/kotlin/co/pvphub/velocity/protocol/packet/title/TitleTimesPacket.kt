package co.pvphub.velocity.protocol.packet.title

class TitleTimesPacket(
    fadeIn: Int = 0,
    stay: Int = 10,
    fadeOut: Int = 0
) : GenericTitlePacket("TitleTimesPacket") {
    var fadeIn = 0
        set(value) {
            field = value
            fadeIn(value)
        }
    var stay = 0
        set(value) {
            field = value
            stay(value)
        }
    var fadeOut = 0
        set(value) {
            field = value
            fadeOut(value)
        }

    init {
        createConstructor()
        println(clazz.declaredMethods.joinToString("\n") { "${it.name}(${it.parameters.joinToString(", ") { it1 -> it1.type.name }})" })
        this.fadeIn = fadeIn
        this.stay = stay
        this.fadeOut = fadeOut
    }

    private fun fadeIn(it: Int) = instanceMethodVoid("setFadeIn", instance, it)
    private fun stay(it: Int) = instanceMethodVoid("setStay", instance, it)
    private fun fadeOut(it: Int) = instanceMethodVoid("setFadeOut", instance, it)

    init {
        createConstructor()
    }

}