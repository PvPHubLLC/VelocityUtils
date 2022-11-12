package co.pvphub.velocity.packet

import co.pvphub.velocity.packet.reflect.velocityClass

class TestPacket {

    companion object {
        val clazz = "VelocityServer".velocityClass()

        fun f() {
            clazz?.getMethod("")
        }
    }
}