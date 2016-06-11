/*
    SlotLimiter
    Copyright (C) 2016  bman7842
    (visit the main class for more information)
*/

package me.bman7842.slotlimiter.Commands.SubCommands;

import me.bman7842.slotlimiter.Commands.SubCommand;
import me.bman7842.slotlimiter.Managers.GUIManager;
import me.bman7842.slotlimiter.Utils.SLMessages;
import me.bman7842.slotlimiter.Utils.SLPermission;
import org.bukkit.entity.Player;

/**
 * Created by brand on 6/7/2016.
 */
public class Configure implements SubCommand {

    private final GUIManager guiManager;

    public Configure(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (!player.hasPermission(SLPermission.SL_ADMIN.getPermission())) {
            player.sendMessage(SLMessages.noPermMessage());
            return false;
        }

        if (guiManager.isPlayerConfiguringSlots(player)) {
            player.sendMessage(SLMessages.formatError("You are currently editing, type '/sl done' to finish editing."));
            return false;
        }

        player.sendMessage(SLMessages.formatAlert("Opening the SL permission editor."));
        guiManager.openConfigureInventory(player);

        return false;
    }

    @Override
    public String help() {
        return (SLMessages.formatCommandHelp("configure", "Requires the permission sl.admin, configures the inventory slots available."));
    }

}
