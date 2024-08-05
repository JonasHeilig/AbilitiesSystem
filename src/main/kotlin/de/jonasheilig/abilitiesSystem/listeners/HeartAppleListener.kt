package de.jonasheilig.abilitiesSystem.listeners

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

class HeartAppleListener : Listener {

    @EventHandler
    fun onPlayerUse(event: PlayerInteractEvent) {
        val item = event.item ?: return
        val player = event.player
        val key = NamespacedKey(AbilitiesSystem.instance, "heart_apple")

        if (item.itemMeta?.persistentDataContainer?.has(key, PersistentDataType.BYTE) == true) {
            event.isCancelled = true
            val maxHealth = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH)?.baseValue ?: 20.0
            player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH)?.baseValue = maxHealth + 2
            player.health = maxHealth + 2

            item.amount -= 1
            AbilitiesSystem.instance.databaseManager.savePlayerHearts(player.name, maxHealth + 2)
        }
    }
}
