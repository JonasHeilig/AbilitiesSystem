package de.jonasheilig.abilitiesSystem.listeners

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class HealthListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val hearts = AbilitiesSystem.instance.databaseManager.loadPlayerHearts(player.name) ?: getDefaultHearts()
        val attribute = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH)
        attribute?.baseValue = hearts
        player.health = hearts
    }

    private fun getDefaultHearts(): Double {
        return AbilitiesSystem.instance.config.getDouble("default-hearts", 8.0)
    }
}
