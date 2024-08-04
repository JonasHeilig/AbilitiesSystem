package de.jonasheilig.abilitiesSystem.items

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class HeartApple {

    companion object {
        fun create(): ItemStack {
            val item = ItemStack(Material.APPLE, 1)
            val meta = item.itemMeta

            meta?.displayName(Component.text("HeartApple").color(NamedTextColor.RED))
            meta?.lore(listOf(Component.text("Right-click to gain an extra heart.").color(NamedTextColor.GRAY)))

            val key = NamespacedKey(AbilitiesSystem.instance, "heart_apple")
            meta?.persistentDataContainer?.set(key, PersistentDataType.BYTE, 1.toByte())
            item.itemMeta = meta
            return item
        }
    }
}
