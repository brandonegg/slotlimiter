/*
    SlotLimiter
    Copyright (C) 2016  bman7842
    (visit the main class for more information)
*/

package me.bman7842.slotlimiter.Commands.SubCommands;

import me.bman7842.slotlimiter.Commands.SubCommand;
import me.bman7842.slotlimiter.SlotLimiter;
import me.bman7842.slotlimiter.Utils.SLMessages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by brand on 6/10/2016.
 */
public class Info implements SubCommand {

    @Override
    public boolean onCommand(Player player, String[] args) {
        player.sendMessage(SLMessages.formatCenteredMessage(ChatColor.DARK_GRAY + "-----------------------------------"));
        player.sendMessage(SLMessages.formatCenteredMessage(ChatColor.RED+""+ChatColor.BOLD+"Slot Limiter"));
        player.sendMessage("");
        player.sendMessage(SLMessages.formatCenteredMessage(ChatColor.GRAY + "Version: " + ChatColor.WHITE + SlotLimiter.getVersion()));
        player.sendMessage(SLMessages.formatCenteredMessage(ChatColor.GRAY + "Developed by: " + ChatColor.WHITE + "bman7842"));
        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage(SLMessages.formatCenteredMessage(ChatColor.DARK_GRAY + "-----------------------------------"));

        return false;
    }

    @Override
    public String help() {
        return (SLMessages.formatCommandHelp("info", "Provides information about this plugin."));
    }

}
