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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by brand on 6/10/2016.
 */
public class Update implements SubCommand {

    //TODO: Updates player's inventories
    private final GUIManager guiManager;

    public Update(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (!player.hasPermission(SLPermission.SL_ADMIN.getPermission())) {
            player.sendMessage(SLMessages.noPermMessage());
            return false;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(SLPermission.SL_ADMIN.getPermission())) {
                guiManager.clearBlockedSlots(p);
            } else {
                guiManager.loadBlockedSlots(p);
                p.sendMessage(SLMessages.formatAlert("Your inventory was updated."));
            }
        }

        player.sendMessage(SLMessages.formatSuccess("Players have been updated!"));

        return false;
    }

    @Override
    public String help() {
        return (SLMessages.formatCommandHelp("update", "Requires the permission sl.admin, updates all players online with the latest blocked slots."));
    }

}
