package de.jonasheilig.abilitiesSystem

import de.jonasheilig.abilitiesSystem.items.*
import de.jonasheilig.abilitiesSystem.listeners.*
import de.jonasheilig.abilitiesSystem.commands.*
import de.jonasheilig.abilitiesSystem.database.DatabaseManager
import org.bukkit.plugin.java.JavaPlugin

class AbilitiesSystem : JavaPlugin() {

    lateinit var databaseManager: DatabaseManager

    override fun onEnable() {
        // Plugin startup logic
        saveDefaultConfig()
        databaseManager = DatabaseManager(this)
        databaseManager.initDatabase()

        server.pluginManager.registerEvents(HealthListener(), this)
        server.pluginManager.registerEvents(HeartAppleListener(), this)
        server.pluginManager.registerEvents(PlayerQuitListener(), this)
        server.pluginManager.registerEvents(SprintListener(), this)
        server.pluginManager.registerEvents(SprintAppleListener(), this)
        getCommand("heart")?.setExecutor(HeartCommand())
        getCommand("setsprinttime")?.setExecutor(SetSprintTimeCommand())
        getCommand("setcooldown")?.setExecutor(SetCooldownCommand())
    }

    override fun onDisable() {
        // Plugin shutdown logic
        databaseManager.closeConnection()
    }

    companion object {
        lateinit var instance: AbilitiesSystem
    }

    init {
        instance = this
    }
}
