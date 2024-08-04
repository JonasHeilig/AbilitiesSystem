package de.jonasheilig.abilitiesSystem.commands

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class SetSprintTimeCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender.hasPermission("abilitiesSystem.setSprintTime")) {
            if (args.size == 2) {
                val targetPlayer: Player? = Bukkit.getPlayer(args[0])
                val sprintTime: Long = args[1].toLongOrNull() ?: return false

                if (targetPlayer != null && sprintTime >= 0) {
                    savePlayerData(targetPlayer.uniqueId.toString(), "max-sprint-time", sprintTime)
                    sender.sendMessage("Sprintzeit für ${targetPlayer.name} auf $sprintTime Millisekunden gesetzt.")
                    return true
                }
            }
        }
        return false
    }

    private fun savePlayerData(playerUUID: String, key: String, value: Any) {
        val configFile = File(AbilitiesSystem.instance.dataFolder, "playerdata.yml")
        val config = YamlConfiguration.loadConfiguration(configFile)
        config.set("$playerUUID.$key", value)
        config.save(configFile)
    }
}
