package de.jonasheilig.abilitiesSystem.listeners

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import java.util.UUID

class SprintAppleListener : Listener {
    @EventHandler
    fun onPlayerUse(event: PlayerInteractEvent) {
        val item = event.item ?: return
        val player = event.player

        val cooldownKey = NamespacedKey(AbilitiesSystem.instance, "cooldown_apple")
        val sprintTimeKey = NamespacedKey(AbilitiesSystem.instance, "sprinttime_apple")

        if (item.itemMeta?.persistentDataContainer?.has(cooldownKey, PersistentDataType.BYTE) == true) {
            event.isCancelled = true
            val improvementValue = AbilitiesSystem.instance.config.getLong("cooldown-improvement-value", 1000L)
            val uuid = player.uniqueId
            val newCooldownTime = AbilitiesSystem.instance.databaseManager.loadPlayerData(uuid, "cooldown_time", 5000L) - improvementValue
            AbilitiesSystem.instance.databaseManager.savePlayerData(uuid, "cooldown_time", newCooldownTime)
            player.sendMessage("Abklingzeit um $improvementValue ms reduziert.")
            item.amount = item.amount - 1
        } else if (item.itemMeta?.persistentDataContainer?.has(sprintTimeKey, PersistentDataType.BYTE) == true) {
            event.isCancelled = true
            val improvementValue = AbilitiesSystem.instance.config.getLong("sprinttime-improvement-value", 1000L)
            val uuid = player.uniqueId
            val newSprintTime = AbilitiesSystem.instance.databaseManager.loadPlayerData(uuid, "max_sprint_time", 10000L) + improvementValue
            AbilitiesSystem.instance.databaseManager.savePlayerData(uuid, "max_sprint_time", newSprintTime)
            player.sendMessage("Sprintzeit um $improvementValue ms erhöht.")
            item.amount = item.amount - 1
        }
    }
}
