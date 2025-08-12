package Mystix.Utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
        if (meta == null)
            throw new IllegalStateException("ItemMeta is null for material: " + material);
    }

    public ItemBuilder(ItemStack stack) {
        this.item = stack.clone();
        this.meta = item.getItemMeta();
    }

    public ItemBuilder setName(Component name) {
        meta.displayName(name);
        return this;
    }

    public ItemBuilder setLore(List<Component> lore) {
        meta.lore(lore);
        return this;
    }

    public ItemBuilder addLoreLine(Component line) {
        List<Component> lore = meta.lore() != null ? meta.lore() : new ArrayList<>();
        if (lore == null) return this;

        lore.add(line);
        meta.lore(lore);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchant, int level, boolean ignoreRestrictions) {
        meta.addEnchant(enchant, level, ignoreRestrictions);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder setCustomModelData(int data) {
        meta.setCustomModelData(data);
        return this;
    }

    public <T, Z> ItemBuilder setPersistentData(org.bukkit.NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(key, type, value);
        return this;
    }

    public ItemBuilder setColor(Color color) {
        if (meta instanceof PotionMeta potionMeta) {
            potionMeta.setColor(color);
        } else if (meta instanceof LeatherArmorMeta leatherMeta) {
            leatherMeta.setColor(color);
        } else {
            throw new IllegalArgumentException("Color can only be applied to PotionMeta or LeatherArmorMeta items.");
        }
        return this;
    }

    public ItemBuilder setOwningPlayer(OfflinePlayer player) {
        if (!(meta instanceof SkullMeta skull))
            throw new IllegalArgumentException("could not add a owning player, meta is not a instance of skull meta");

        skull.setOwningPlayer(player);
        return this;
    }

    public ItemBuilder setOwningProfile(PlayerProfile player) {
        if (!(meta instanceof SkullMeta skull))
            throw new IllegalArgumentException("could not add a owning PlayerProfile, meta is not a instance of skull meta");

        skull.setPlayerProfile(player);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}

