package de.jonasheilig.abilitiesSystem.database

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*
import kotlin.collections.HashMap

class DatabaseManager(private val plugin: AbilitiesSystem) {

    private lateinit var connection: Connection

    fun initDatabase() {
        if (plugin.config.getBoolean("useMySQL")) {
            val url = "${plugin.config.getString("url")}:${plugin.config.getInt("port")}/${plugin.config.getString("database")}"
            val username = plugin.config.getString("username")
            val password = plugin.config.getString("password")
            connection = DriverManager.getConnection(url, username, password)
            createTables()
        }
    }

    private fun createTables() {
        val statement = connection.createStatement()
        statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS playerdata (" +
                    "uuid VARCHAR(36) PRIMARY KEY, " +
                    "max_sprint_time BIGINT, " +
                    "cooldown_time BIGINT)"
        )
        statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS playerhearts (" +
                    "playername VARCHAR(16) PRIMARY KEY, " +
                    "hearts DOUBLE)"
        )
        statement.close()
    }

    fun savePlayerData(uuid: UUID, key: String, value: Long) {
        val query = "INSERT INTO playerdata (uuid, $key) VALUES (?, ?) ON DUPLICATE KEY UPDATE $key = VALUES($key)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setString(1, uuid.toString())
        statement.setLong(2, value)
        statement.executeUpdate()
        statement.close()
    }

    fun loadPlayerData(uuid: UUID, key: String, defaultValue: Long): Long {
        val query = "SELECT $key FROM playerdata WHERE uuid = ?"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setString(1, uuid.toString())
        val resultSet: ResultSet = statement.executeQuery()
        val result = if (resultSet.next()) resultSet.getLong(key) else defaultValue
        resultSet.close()
        statement.close()
        return result
    }

    fun savePlayerHearts(playerName: String, hearts: Double) {
        val query = "INSERT INTO playerhearts (playername, hearts) VALUES (?, ?) ON DUPLICATE KEY UPDATE hearts = VALUES(hearts)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setString(1, playerName)
        statement.setDouble(2, hearts)
        statement.executeUpdate()
        statement.close()
    }

    fun loadPlayerHearts(playerName: String): Double? {
        val query = "SELECT hearts FROM playerhearts WHERE playername = ?"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setString(1, playerName)
        val resultSet: ResultSet = statement.executeQuery()
        val result = if (resultSet.next()) resultSet.getDouble("hearts") else null
        resultSet.close()
        statement.close()
        return result
    }

    fun closeConnection() {
        connection.close()
    }
}
