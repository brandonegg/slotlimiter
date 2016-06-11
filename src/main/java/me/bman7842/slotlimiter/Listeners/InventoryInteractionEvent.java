/*
    SlotLimiter
    Copyright (C) 2016  bman7842
    (visit the main class for more information)
*/

package me.bman7842.slotlimiter.Listeners;

import me.bman7842.slotlimiter.Managers.GUIManager;
import me.bman7842.slotlimiter.Utils.CustomItemStacks;
import me.bman7842.slotlimiter.Utils.SLMessages;
import me.bman7842.slotlimiter.Utils.SLPermission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

/**
 * Created by brand on 6/7/2016.
 */
public class InventoryInteractionEvent implements Listener {

    private final GUIManager guiManager;

    public InventoryInteractionEvent(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();

        if (!e.getSlotType().equals(InventoryType.SlotType.OUTSIDE)) {
            if (guiManager.isPlayerConfiguringSlots(p)) {
                e.setCancelled(true);
                if (!e.getSlotType().equals(InventoryType.SlotType.CRAFTING)) {
                    if (e.getClick().equals(ClickType.LEFT)) {
                        p.getInventory().setItem(e.getSlot(), CustomItemStacks.getBlockedPane());
                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                        p.getInventory().setItem(e.getSlot(), new ItemStack(Material.AIR, 1));
                    } else if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
                        for (int i = 0; i < 41; i++) {
                            p.getInventory().setItem(i, CustomItemStacks.getBlockedPane());
                        }
                    } else if (e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                        p.getInventory().clear();
                        p.sendMessage(SLMessages.formatSuccess("Inventory cleared!"));
                    }
                } else {
                    p.sendMessage(SLMessages.formatError("Crafting slots are not supported."));
                }
            } else {
                if (e.getClickedInventory().getItem(e.getSlot()) != null) {
                    if (e.getClickedInventory().getItem(e.getSlot()).equals(CustomItemStacks.getBlockedPane())) {
                        if (p.hasPermission(SLPermission.SL_ADMIN.getPermission())) {
                            guiManager.clearBlockedSlots(p);
                        } else {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

}
