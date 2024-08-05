package de.jonasheilig.abilitiesSystem.commands

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetCooldownCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender.hasPermission("abilitiesSystem.setCooldown")) {
            if (args.size == 2) {
                val targetPlayer: Player? = Bukkit.getPlayer(args[0])
                val cooldownTime: Long = args[1].toLongOrNull() ?: return false

                if (targetPlayer != null && cooldownTime >= 0) {
                    AbilitiesSystem.instance.databaseManager.savePlayerData(targetPlayer.uniqueId, "cooldown_time", cooldownTime)
                    sender.sendMessage("Abklingzeit für ${targetPlayer.name} auf $cooldownTime Millisekunden gesetzt.")
                    return true
                }
            }
        }
        return false
    }
}
