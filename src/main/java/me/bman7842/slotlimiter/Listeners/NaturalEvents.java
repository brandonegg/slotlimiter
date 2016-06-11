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
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brand on 6/10/2016.
 */
public class NaturalEvents implements Listener {

    private final GUIManager guiManager;

    public NaturalEvents(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler
    public void userDied(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (guiManager.isPlayerConfiguringSlots(p)) {
            e.setKeepInventory(true);
            p.sendMessage(SLMessages.formatAlert("You are still in configure mode, type /sl done to finish editing."));
        } else {
            List<ItemStack> dropsClone = new ArrayList<ItemStack>();
            for (ItemStack item : e.getDrops()) {
                dropsClone.add(item);
            }
            for (ItemStack item : dropsClone) {
                if (item.equals(CustomItemStacks.getBlockedPane())) {
                    e.getDrops().remove(item);
                }
            }
        }
    }

    @EventHandler
    public void playerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPermission(SLPermission.SL_ADMIN.getPermission())) {
            guiManager.loadBlockedSlots(p);
        }
    }

    @EventHandler
    public void userDropItem(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        if (guiManager.isPlayerConfiguringSlots(p)) {
            e.setCancelled(true);
            p.sendMessage(SLMessages.formatAlert("You can't drop items in configure mode, type /sl done to finish editing."));
        } else {
            if (e.getItemDrop() != null) {
                if (e.getItemDrop().getItemStack().equals(CustomItemStacks.getBlockedPane())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void userPickupItem(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();

        if (guiManager.isPlayerConfiguringSlots(p)) {
            e.setCancelled(true);
            p.sendMessage(SLMessages.formatAlert("You can't pickup items in configure mode, type /sl done to finish editing."));
        } else {
            if (e.getItem() != null) {
                if (e.getItem().getItemStack().equals(CustomItemStacks.getBlockedPane())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void userEditingPickupItem(PlayerPickupArrowEvent e) {
        Player p = e.getPlayer();

        if (guiManager.isPlayerConfiguringSlots(p)) {
            e.setCancelled(true);
            p.sendMessage(SLMessages.formatAlert("You can't pickup entities in configure mode, type /sl done to finish editing."));
        }
    }

    @EventHandler
    public void userEditingOpenChest(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block clickedBlock = e.getClickedBlock();

        if (guiManager.isPlayerConfiguringSlots(p)) {
            if (clickedBlock.getType().equals(Material.CHEST) || clickedBlock.getType().equals(Material.ENDER_CHEST)
                    || clickedBlock.getType().equals(Material.TRAPPED_CHEST)) {
                e.setCancelled(true);
                p.sendMessage(SLMessages.formatAlert("You can't open chests in configure mode, type /sl done to finish editing."));
            }
        }
    }

    @EventHandler
    public void blockPlace(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();

        if (guiManager.isPlayerConfiguringSlots(p)) {
            e.setCancelled(true);
            p.sendMessage(SLMessages.formatAlert("You can't place blocks in configure mode, type /sl done to finish editing."));
        } else {
            if (item != null) {
                if (item.equals(CustomItemStacks.getBlockedPane())) {
                    e.setCancelled(true);
                    p.updateInventory();
                }
            }
        }
    }
}
