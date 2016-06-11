/*
    SlotLimiter
    Copyright (C) 2016  bman7842
    (visit the main class for more information)
*/

package me.bman7842.slotlimiter.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by brand on 6/10/2016.
 */
public class CustomItemStacks {

    public static ItemStack getBlockedPane() {
        ItemStack blockedSlot = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta blockedSlotIM = blockedSlot.getItemMeta();
        blockedSlotIM.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "BLOCKED");
        blockedSlot.setItemMeta(blockedSlotIM);
        return blockedSlot;
    }

}
