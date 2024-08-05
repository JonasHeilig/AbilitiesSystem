package de.jonasheilig.abilitiesSystem.listeners

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

class InfiniteSprintAppleListener : Listener {

    @EventHandler
    fun onPlayerUse(event: PlayerInteractEvent) {
        val item = event.item ?: return
        val player = event.player
        val key = NamespacedKey(AbilitiesSystem.instance, "infinite_sprint_apple")

        if (item.itemMeta?.persistentDataContainer?.has(key, PersistentDataType.BYTE) == true) {
            event.isCancelled = true
            val uuid = player.uniqueId
            AbilitiesSystem.instance.databaseManager.savePlayerData(uuid, "max_sprint_time", Long.MAX_VALUE)
            player.sendMessage("Du kannst jetzt unendlich sprinten!")
            item.amount -= 1
        }
    }
}
