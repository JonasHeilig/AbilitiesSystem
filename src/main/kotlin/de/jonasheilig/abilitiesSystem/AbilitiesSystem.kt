package de.jonasheilig.abilitiesSystem

import de.jonasheilig.abilitiesSystem.listeners.*
import de.jonasheilig.abilitiesSystem.commands.*
import org.bukkit.plugin.java.JavaPlugin

class AbilitiesSystem : JavaPlugin() {

    override fun onEnable() {
        server.pluginManager.registerEvents(HealthListener(), this)
        getCommand("heart")?.setExecutor(HeartCommand())
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
