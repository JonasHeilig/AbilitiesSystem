package de.jonasheilig.abilitiesSystem.listeners

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerToggleSprintEvent
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class SprintListener : Listener {

    private val sprintTimes: MutableMap<UUID, Long> = mutableMapOf()
    private val cooldownTimes: MutableMap<UUID, Long> = mutableMapOf()

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        sprintTimes[player.uniqueId] = 0
        cooldownTimes[player.uniqueId] = 0
    }

    @EventHandler
    fun onPlayerToggleSprint(event: PlayerToggleSprintEvent) {
        val player = event.player
        val uuid = player.uniqueId

        if (event.isSprinting) {
            val currentTime = System.currentTimeMillis()
            val lastCooldownTime = cooldownTimes[uuid] ?: 0
            val cooldownTime = AbilitiesSystem.instance.databaseManager.loadPlayerData(uuid, "cooldown_time", 5000L)
            if (currentTime < lastCooldownTime + cooldownTime) {
                event.isCancelled = true
                player.sendMessage("Du musst warten, bevor du wieder sprinten kannst!")
                return
            }

            object : BukkitRunnable() {
                override fun run() {
                    if (!player.isSprinting) {
                        cancel()
                        return
                    }

                    val sprintStartTime = sprintTimes[uuid] ?: 0
                    val sprintDuration = System.currentTimeMillis() - sprintStartTime
                    val maxSprintTime = AbilitiesSystem.instance.databaseManager.loadPlayerData(uuid, "max_sprint_time", 10000L)
                    if (sprintDuration > maxSprintTime) {
                        player.isSprinting = false
                        cooldownTimes[uuid] = System.currentTimeMillis()
                        player.sendMessage("Du bist zu müde, um weiter zu sprinten. Bitte warte einen Moment.")
                        cancel()
                    }
                }
            }.runTaskTimer(AbilitiesSystem.instance, 0, 20) // Check every second
            sprintTimes[uuid] = System.currentTimeMillis()
        }
    }
}
