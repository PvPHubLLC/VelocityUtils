package co.pvphub.velocity.protocol.packet

import co.pvphub.velocity.reflect.ClassAccessor
import com.velocitypowered.api.util.GameProfile
import net.kyori.adventure.text.Component
import java.util.*


class PlayerListItem(
    action: Action,
    items: List<Item>
) : PacketAdapter("procotol.packet.PlayerListItem") {

    init {
        createConstructor(action, items)
    }

    fun action(): Action = Action.of(instanceMethod("getAction", instance))
    fun items(): List<Item> = instanceMethod("getItems", instance)

    enum class Action(val code: Int) {
        ADD_PLAYER(PlayerListItemUtil.int("ADD_PLAYER")),
        UPDATE_GAMEMODE(PlayerListItemUtil.int("UPDATE_GAMEMODE")),
        UPDATE_LATENCY(PlayerListItemUtil.int("UPDATE_LATENCY")),
        UPDATE_DISPLAY_NAME(PlayerListItemUtil.int("UPDATE_DISPLAY_NAME")),
        REMOVE_PLAYER(PlayerListItemUtil.int("REMOVE_PLAYER"));

        companion object {
            fun of(i: Int): Action {
                return Action.values().firstOrNull { it.code == i } ?: REMOVE_PLAYER
            }
        }
    }

    class Item(uuid: UUID) : ClassAccessor("procotol.packet.PlayerListItem.Item") {

        init {
            createConstructor(uuid)
        }

        fun uuid() : UUID? = instanceMethod("getUuid", instance)
        fun name() : String? = instanceMethod("getName", instance)
        fun name(name: String) = instanceMethodVoid("setName", instance, name)
        fun properties() : List<GameProfile.Property> = instanceMethod("getProperties", instance)
        fun properties(prop: List<GameProfile.Property>) = instanceMethodVoid("setProperties", instance, prop)
        fun gamemode() : Int = instanceMethod("getGameMode", instance)
        fun gamemode(gamemode: Int) = instanceMethodVoid("setGameMode", instance, gamemode)
        fun latency(ping: Int) = instanceMethodVoid("setLatency", instance, ping)
        fun latency() : Int = instanceMethod("getLatency", instance)
        fun displayname(name: Component) = instanceMethodVoid("setDisplayName", instance, name)
        fun displayname() : Component? = instanceMethod("getDisplayName", instance)
        // player key

    }
}

object PlayerListItemUtil : PacketAdapter("procotol.packet.PlayerListItem")