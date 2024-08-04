package de.jonasheilig.abilitiesSystem.items

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import org.bukkit.ChatColor

class HeartApple {

    companion object {
        fun create(): ItemStack {
            val item = ItemStack(Material.APPLE, 1)
            val meta = item.itemMeta

            meta?.setDisplayName("${ChatColor.RED}HeartApple")
            meta?.lore = listOf("§7Right-click to gain an extra heart.")

            val key = NamespacedKey(AbilitiesSystem.instance, "heart_apple")
            meta?.persistentDataContainer?.set(key, PersistentDataType.BYTE, 1.toByte())
            item.itemMeta = meta
            return item
        }
    }
}
