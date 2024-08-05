package de.jonasheilig.abilitiesSystem.commands

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StatsCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val player = sender
            val uuid = player.uniqueId

            val maxHealth = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH)?.baseValue ?: 20.0
            val sprintTime = AbilitiesSystem.instance.databaseManager.loadPlayerData(uuid, "max_sprint_time", 0L)
            val regenTime = AbilitiesSystem.instance.databaseManager.loadPlayerData(uuid, "regen_time", 0L)

            player.sendMessage("§aStatistiken:")
            player.sendMessage("§7Maximale Herzen: §a$maxHealth")
            player.sendMessage("§7Sprintszeit: §a$sprintTime Sekunden")
            player.sendMessage("§7Regenerationszeit: §a$regenTime Sekunden")
        } else {
            sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden.")
        }
        return true
    }
}
