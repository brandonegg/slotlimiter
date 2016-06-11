/*
    SlotLimiter, slot limiting Minecraft plugin.
    Copyright (C) 2016  bman7842

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.bman7842.slotlimiter;

import me.bman7842.slotlimiter.Commands.SlotLimiterCMD;
import me.bman7842.slotlimiter.Listeners.ConnectionEvents;
import me.bman7842.slotlimiter.Listeners.InventoryInteractionEvent;
import me.bman7842.slotlimiter.Listeners.NaturalEvents;
import me.bman7842.slotlimiter.Managers.ConfigManager;
import me.bman7842.slotlimiter.Managers.GUIManager;
import me.bman7842.slotlimiter.Managers.InventorySlotManager;
import me.bman7842.slotlimiter.Utils.SLMessages;
import me.bman7842.slotlimiter.Utils.SLPermission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by brand on 6/7/2016.
 */
public class SlotLimiter extends JavaPlugin {

    private ConfigManager inventorySlotConfig;
    private GUIManager guiManager;
    private InventorySlotManager inventorySlotManager;

    @Override
    public void onEnable() {
        saveResource("InventorySlotData.yml", false);

        inventorySlotConfig = new ConfigManager("InventorySlotData");
        inventorySlotManager = new InventorySlotManager(inventorySlotConfig);
        guiManager = new GUIManager(inventorySlotManager);

        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new InventoryInteractionEvent(guiManager), this);
        pm.registerEvents(new NaturalEvents(guiManager), this);
        pm.registerEvents(new ConnectionEvents(guiManager), this);

        getCommand("sl").setExecutor(new SlotLimiterCMD(guiManager, inventorySlotManager));

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(SLPermission.SL_ADMIN.getPermission())) {
                guiManager.clearBlockedSlots(p);
            } else {
                guiManager.loadBlockedSlots(p);
                p.sendMessage(SLMessages.formatAlert("Your inventory was updated."));
            }
        }
    }

    @Override
    public void onDisable() {
        for (Player p : guiManager.getPlayersConfiguring()) {
            guiManager.finishEditing(p);
        }
        inventorySlotManager.saveSlotData();
    }

    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("SlotLimiter");
    }

    public static String getVersion() {
        return getPlugin().getDescription().getVersion();
    }

}
