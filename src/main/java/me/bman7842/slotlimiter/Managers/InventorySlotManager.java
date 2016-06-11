/*
    SlotLimiter
    Copyright (C) 2016  bman7842
    (visit the main class for more information)
*/

package me.bman7842.slotlimiter.Managers;

import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

/**
 * Created by brand on 6/10/2016.
 */
public class InventorySlotManager {

    private final ConfigManager inventorySlotConfig;

    private final List<Integer> lastSave;
    private List<Integer> blockedSlots = new ArrayList<Integer>();

    public InventorySlotManager(ConfigManager configManager) {
        inventorySlotConfig = configManager;

        loadSlotData();
        lastSave = inventorySlotConfig.getIntegerList("main");
    }

    public void updateBlockedSlots(PlayerInventory playerInventory) {
        blockedSlots = slotsToList(playerInventory);
    }

    public void saveSlotData() {
        if (lastSave != blockedSlots) {
            inventorySlotConfig.set("main", blockedSlots);
        }
    }

    public List<Integer> getBlockedSlots() {
        return blockedSlots;
    }

    private void loadSlotData() {
        blockedSlots = inventorySlotConfig.getIntegerList("main");
    }

    private List<Integer> slotsToList(PlayerInventory playerInventory) {
        List<Integer> slots = new ArrayList<Integer>();
        for (int i = 0; i < 41; i++) {
            if (playerInventory.getItem(i) != null) {
                if (playerInventory.getItem(i).getType().equals(Material.STAINED_GLASS_PANE)) {
                    slots.add(i);
                }
            }
        }
        return slots;
    }

}
