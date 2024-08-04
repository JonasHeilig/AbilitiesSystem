package de.jonasheilig.abilitiesSystem

import de.jonasheilig.abilitiesSystem.items.*
import de.jonasheilig.abilitiesSystem.listeners.*
import de.jonasheilig.abilitiesSystem.commands.*
import org.bukkit.plugin.java.JavaPlugin

class AbilitiesSystem : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        saveDefaultConfig()
        server.pluginManager.registerEvents(HealthListener(), this)
        server.pluginManager.registerEvents(HeartAppleListener(), this)
        server.pluginManager.registerEvents(PlayerQuitListener(), this)
        server.pluginManager.registerEvents(SprintListener(), this)
        server.pluginManager.registerEvents(SprintAppleListener(), this)
        getCommand("heart")?.setExecutor(HeartCommand())
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object {
        lateinit var instance: AbilitiesSystem
    }

    init {
        instance = this
    }
}
