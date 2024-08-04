package de.jonasheilig.abilitiesSystem.listeners

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import java.io.File

class PlayerQuitListener : Listener {

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        val maxHealth = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH)?.baseValue ?: 20.0
        savePlayerHearts(player.name, maxHealth)
    }

    private fun savePlayerHearts(playerName: String, hearts: Double) {
        val configFile = File(AbilitiesSystem.instance.dataFolder, "heart.yml")
        val config = YamlConfiguration.loadConfiguration(configFile)
        config.set(playerName, hearts)
        config.save(configFile)
    }
}
