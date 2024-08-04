package de.jonasheilig.abilitiesSystem.listeners

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.io.File

class HealthListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val hearts = loadPlayerHearts(player.name) ?: getDefaultHearts()
        val attribute = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH)
        attribute?.baseValue = hearts
        player.health = hearts
    }

    private fun loadPlayerHearts(playerName: String): Double? {
        val configFile = File(AbilitiesSystem.instance.dataFolder, "heart.yml")
        if (!configFile.exists()) return null

        val config = YamlConfiguration.loadConfiguration(configFile)
        return if (config.contains(playerName)) config.getDouble(playerName) else null
    }

    private fun getDefaultHearts(): Double {
        return AbilitiesSystem.instance.config.getDouble("default-hearts", 8.0)
    }
}
