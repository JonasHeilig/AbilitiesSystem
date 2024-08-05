package de.jonasheilig.abilitiesSystem.listeners

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener : Listener {

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        val maxHealth = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH)?.baseValue ?: 20.0
        AbilitiesSystem.instance.databaseManager.savePlayerHearts(player.name, maxHealth)
    }
}
