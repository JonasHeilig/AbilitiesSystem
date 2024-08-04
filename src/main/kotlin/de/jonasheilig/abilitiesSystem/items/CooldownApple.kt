package de.jonasheilig.abilitiesSystem.items

import de.jonasheilig.abilitiesSystem.AbilitiesSystem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class CooldownApple {

    companion object {
        fun create(): ItemStack {
            val improvementValue = AbilitiesSystem.instance.config.getLong("cooldown-improvement-value", 1000L)
            val item = ItemStack(Material.APPLE, 1)
            val meta = item.itemMeta
            meta?.displayName(Component.text("Abklingzeit-Apfel").color(NamedTextColor.RED))
            meta?.lore(listOf(Component.text("Rechtsklick, um die Abklingzeit um $improvementValue ms zu reduzieren").color(NamedTextColor.GRAY)))
            val key = NamespacedKey(AbilitiesSystem.instance, "cooldown_apple")
            meta?.persistentDataContainer?.set(key, PersistentDataType.BYTE, 1.toByte())
            item.itemMeta = meta
            return item
        }
    }
}