package de.jonasheilig.abilitiesSystem.listeners

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

class InvincibleAppleListener : Listener {

    @EventHandler
    fun onPlayerUse(event: PlayerInteractEvent) {
        val item = event.item ?: return
        val player = event.player
        val key = NamespacedKey(AbilitiesSystem.instance, "invisible_invincible_apple")

        if (item.itemMeta?.persistentDataContainer?.has(key, PersistentDataType.BYTE) == true) {
            event.isCancelled = true

            player.isInvulnerable = true

            val attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)
            attribute?.baseValue = 2048.0
            player.health = 2048.0

            hideHealth(player)

            player.sendMessage("Du bist jetzt unsichtbar und unsterblich!")
            item.amount -= 1
        }
    }

    private fun hideHealth(player: Player) {
        Bukkit.getScheduler().runTaskTimer(AbilitiesSystem.instance, Runnable {
            player.absorptionAmount = 2048.0
        }, 0L, 20L)
    }
}
