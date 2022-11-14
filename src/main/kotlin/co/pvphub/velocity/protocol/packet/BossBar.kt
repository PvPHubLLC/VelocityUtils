package co.pvphub.velocity.protocol.packet

import co.pvphub.velocity.reflect.ClassAccessor
import java.util.UUID

class BossBar : PacketAdapter("protocol.packet.BossBar") {

    init {
        createConstructor()
    }

    fun action(action: Int) = instanceMethodVoid("setAction", instance, action)
    fun action() : Action = Action.of(instanceMethod("getAction", instance))
    fun uuid(uuid: UUID) = instanceMethodVoid("setUuid", instance, uuid)
    fun uuid() : UUID? = instanceMethod("getUuid", instance)
    fun name(name: String) = instanceMethodVoid("setName", instance, name)
    fun name() : String? = instanceMethod("getName", instance)
    fun percent(percent: Float) = instanceMethodVoid("setPercent", instance, percent)
    fun percent() : Float = instanceMethod("getPercent", instance)
    fun color(color: Int) = instanceMethodVoid("setColor", instance, color)
    fun color() : Int = instanceMethod("getColor", instance)
    fun overlay(overlay: Int) = instanceMethodVoid("setOverlay", instance, overlay)
    fun overlay() : Int = instanceMethod("getOverlay", instance)
    fun flags(flags: Short) = instanceMethodVoid("setFlags", instance, flags)
    fun flags() : Short = instanceMethod("getFlags", instance)

    enum class Action(val code: Int) {
        ADD(BossBarUtil.int("ADD")),
        REMOVE(BossBarUtil.int("REMOVE")),
        UPDATE_PERCENT(BossBarUtil.int("UPDATE_PERCENT")),
        UPDATE_NAME(BossBarUtil.int("UPDATE_NAME")),
        UPDATE_STYLE(BossBarUtil.int("UPDATE_STYLE")),
        UPDATE_PROPERTIES(BossBarUtil.int("UPDATE_PROPERTIES"));

        companion object {
            fun of(i: Int): Action {
                return values().firstOrNull { it.code == i } ?: ADD
            }
        }
    }
}

object BossBarUtil : ClassAccessor("protocol.packet.BossBar")