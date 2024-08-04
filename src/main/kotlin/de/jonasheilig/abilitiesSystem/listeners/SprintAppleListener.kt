package de.jonasheilig.abilitiesSystem.listeners

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import java.io.File
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
            val newCooldownTime = loadPlayerData(uuid, "cooldown-time", 5000L) - improvementValue
            savePlayerData(uuid.toString(), "cooldown-time", newCooldownTime)
            player.sendMessage("Abklingzeit um $improvementValue ms reduziert.")
            item.amount = item.amount - 1
        } else if (item.itemMeta?.persistentDataContainer?.has(sprintTimeKey, PersistentDataType.BYTE) == true) {
            event.isCancelled = true
            val improvementValue = AbilitiesSystem.instance.config.getLong("sprinttime-improvement-value", 1000L)
            val uuid = player.uniqueId
            val newSprintTime = loadPlayerData(uuid, "max-sprint-time", 10000L) + improvementValue
            savePlayerData(uuid.toString(), "max-sprint-time", newSprintTime)
            player.sendMessage("Sprintzeit um $improvementValue ms erhöht.")
            item.amount = item.amount - 1
        }
    }

    private fun loadPlayerData(playerUUID: UUID, key: String, defaultValue: Long): Long {
        val configFile = File(AbilitiesSystem.instance.dataFolder, "playerdata.yml")
        if (!configFile.exists()) return defaultValue

        val config = YamlConfiguration.loadConfiguration(configFile)
        return config.getLong("$playerUUID.$key", defaultValue)
    }

    private fun savePlayerData(playerUUID: String, key: String, value: Any) {
        val configFile = File(AbilitiesSystem.instance.dataFolder, "playerdata.yml")
        val config = YamlConfiguration.loadConfiguration(configFile)
        config.set("$playerUUID.$key", value)
        config.save(configFile)
    }
}
