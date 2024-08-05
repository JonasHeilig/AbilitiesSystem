package de.jonasheilig.abilitiesSystem.commands

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HeartCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender.hasPermission("abilitiesSystem.heart")) {
            if (args.size == 2) {
                val targetPlayer: Player? = Bukkit.getPlayer(args[0])
                val hearts: Double = args[1].toDoubleOrNull() ?: return false

                if (targetPlayer != null && hearts > 0) {
                    val attribute = targetPlayer.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH)
                    attribute?.baseValue = hearts * 2
                    targetPlayer.health = hearts * 2
                    AbilitiesSystem.instance.databaseManager.savePlayerHearts(targetPlayer.name, hearts * 2)
                    sender.sendMessage("Gesundheit von ${targetPlayer.name} auf $hearts Herzen gesetzt.")
                    return true
                }
            }
        }
        return false
    }
}
