package de.jonasheilig.abilitiesSystem.items

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class InvincibleApple {
    companion object {
        fun create(): ItemStack {
            val item = ItemStack(Material.APPLE, 1)
            val meta = item.itemMeta

            meta?.displayName(Component.text("Unbesiegbarkeitsapfel").color(NamedTextColor.GOLD))
            meta?.lore(listOf(Component.text("Rechtsklick um unbesiegbar zu werden.").color(NamedTextColor.GRAY)))

            val key = NamespacedKey(AbilitiesSystem.instance, "invisible_invincible_apple")
            meta?.persistentDataContainer?.set(key, PersistentDataType.BYTE, 1.toByte())
            item.itemMeta = meta
            return item
        }
    }
}
