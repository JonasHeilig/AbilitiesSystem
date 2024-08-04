package de.jonasheilig.abilitiesSystem.items

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class SprintTimeApple {

    companion object {
        fun create(): ItemStack {
            val improvementValue = AbilitiesSystem.instance.config.getLong("sprinttime-improvement-value", 1000L)
            val item = ItemStack(Material.APPLE, 1)
            val meta = item.itemMeta
            meta?.displayName(Component.text("Sprintzeit-Apfel").color(NamedTextColor.RED))
            meta?.lore(listOf(Component.text("Rechtsklick, um die Sprintzeit um $improvementValue ms zu erhöhen").color(NamedTextColor.GRAY)))
            val key = NamespacedKey(AbilitiesSystem.instance, "sprinttime_apple")
            meta?.persistentDataContainer?.set(key, PersistentDataType.BYTE, 1.toByte())
            item.itemMeta = meta
            return item
        }
    }
}