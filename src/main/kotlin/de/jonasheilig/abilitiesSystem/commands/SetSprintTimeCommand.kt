package de.jonasheilig.abilitiesSystem.commands

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetSprintTimeCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender.hasPermission("abilitiesSystem.setSprintTime")) {
            if (args.size == 2) {
                val targetPlayer: Player? = Bukkit.getPlayer(args[0])
                val sprintTime: Long = args[1].toLongOrNull() ?: return false

                if (targetPlayer != null && sprintTime >= 0) {
                    AbilitiesSystem.instance.databaseManager.savePlayerData(targetPlayer.uniqueId, "max_sprint_time", sprintTime)
                    sender.sendMessage("Sprintzeit für ${targetPlayer.name} auf $sprintTime Millisekunden gesetzt.")
                    return true
                }
            }
        }
        return false
    }
}
