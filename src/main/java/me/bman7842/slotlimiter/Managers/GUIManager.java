/*
    SlotLimiter
    Copyright (C) 2016  bman7842
    (visit the main class for more information)
*/

package me.bman7842.slotlimiter.Managers;

import me.bman7842.slotlimiter.Utils.CustomItemStacks;
import me.bman7842.slotlimiter.Utils.SLMessages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by brand on 6/7/2016.
 */
public class GUIManager {

    private final InventorySlotManager inventorySlotManager;

    public GUIManager(InventorySlotManager inventorySlotManager) {
        this.inventorySlotManager = inventorySlotManager;
    }

    private HashMap<Player, Inventory> playersActualInventory = new HashMap<Player, Inventory>();

    public void openConfigureInventory(Player p) {
        if (isPlayerConfiguringSlots(p)) {
            restoreInventory(p);
            return;
        }

        clearBlockedSlots(p);

        Inventory newPInv = Bukkit.createInventory(p, InventoryType.PLAYER);
        for (int i = 0; i < 41; i++) {
            if (p.getInventory().getItem(i) != null) {
                if (!p.getInventory().getItem(i).getType().equals(Material.AIR)) {
                    newPInv.setItem(i, p.getInventory().getItem(i));
                }
            }
        }

        playersActualInventory.put(p, newPInv);
        p.getInventory().clear();
        loadBlockedSlots(p);

        p.sendMessage(SLMessages.formatSuccess("Configure mode activated! Left click a slot in your inventory to block it, right click a blocked slot to unblock it. Shift-Right-Click to unblock all, Shift-Left-Click to block all."));
    }

    public void finishEditing(Player p) {
        if (!isPlayerConfiguringSlots(p)) {
            Bukkit.getLogger().warning("Checks failed, not configuring player finished. Report to developer.");
            return;
        }

        restoreInventory(p);
        playersActualInventory.remove(p);
    }

    public boolean isPlayerConfiguringSlots(Player p) {
        if (playersActualInventory.keySet().contains(p)) {
            return true;
        }
        return false;
    }

    public void loadBlockedSlots(Player p) {
        PlayerInventory pInv = p.getInventory();
        clearBlockedSlots(p);
        for (Integer i : inventorySlotManager.getBlockedSlots()) {
            if (pInv.getItem(i) != null) {
                if (!pInv.getItem(i).getType().equals(Material.AIR)) {
                    fillEmptySlot(p, pInv, pInv.getItem(i), i);
                    pInv.setItem(i, CustomItemStacks.getBlockedPane());
                } else {
                    pInv.setItem(i, CustomItemStacks.getBlockedPane());
                }
            } else {
                pInv.setItem(i, CustomItemStacks.getBlockedPane());
            }
        }
    }

    public void clearBlockedSlots(Player p) {
        for (int i = 0; i < 41; i++) {
            if (p.getInventory().getItem(i) != null) {
                if (p.getInventory().getItem(i).equals(CustomItemStacks.getBlockedPane())) {
                    p.getInventory().setItem(i, new ItemStack(Material.AIR));
                }
            }
        }
    }

    public Set<Player> getPlayersConfiguring() {
        return playersActualInventory.keySet();
    }

    private void fillEmptySlot(Player p, PlayerInventory playerInventory, ItemStack item, Integer slot) {
        for (int i = 0; i < 36; i++) {
            if (playerInventory.getItem(i) != null) {
                if (playerInventory.getItem(i).equals(Material.AIR)) {
                    playerInventory.setItem(i, item);
                    playerInventory.setItem(slot, new ItemStack(Material.AIR));
                    return;
                }
            }
        }
        playerInventory.setItem(slot, new ItemStack(Material.AIR,1));
        p.getWorld().dropItemNaturally(p.getLocation(), item);
    }

    private void restoreInventory(Player p) {
        Inventory pOriginalInv = playersActualInventory.get(p);

        p.getInventory().clear();
        for (int i = 0; i<41; i++) {
            if (pOriginalInv.getItem(i) != null) {
                p.getInventory().setItem(i, pOriginalInv.getItem(i));
            }
        }

        p.sendMessage(SLMessages.formatSuccess("Original inventory restored"));
        playersActualInventory.remove(p);
    }

}
