/*
    SlotLimiter
    Copyright (C) 2016  bman7842
    (visit the main class for more information)
*/

package me.bman7842.slotlimiter.Commands;

import me.bman7842.slotlimiter.Commands.SubCommands.Configure;
import me.bman7842.slotlimiter.Commands.SubCommands.Done;
import me.bman7842.slotlimiter.Commands.SubCommands.Info;
import me.bman7842.slotlimiter.Commands.SubCommands.Update;
import me.bman7842.slotlimiter.Managers.GUIManager;
import me.bman7842.slotlimiter.Managers.InventorySlotManager;
import me.bman7842.slotlimiter.Utils.SLMessages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by brand on 6/7/2016.
 */
public class SlotLimiterCMD implements CommandExecutor {

    HashMap<String, SubCommand> subCommands = new HashMap<String, SubCommand>();

    public SlotLimiterCMD(GUIManager guiManager, InventorySlotManager inventorySlotManager) {
        subCommands.put("configure", new Configure(guiManager));
        subCommands.put("done", new Done(guiManager, inventorySlotManager));
        subCommands.put("update", new Update(guiManager));
        subCommands.put("info", new Info());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(SLMessages.formatError("This command is only for players!"));
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(SLMessages.formatError("Invalid arguments, type '/sl help'"));
            return false;
        } else if (args[0].equalsIgnoreCase("help")) {
            sendHelpMsg(sender);
            return false;
        }

        SubCommand command = getCmdIgnoreCase(args[0]);

        if (command != null) {
            Vector<String> l = new Vector<String>();
            l.addAll(Arrays.asList(args));
            l.remove(0);
            args = l.toArray(new String[0]);

            command.onCommand((Player) sender, args);
        } else {
            sender.sendMessage(SLMessages.formatError("The command " + args[0] + " was not found, check '/sl help' for a list of commands."));
        }

        return false;
    }

    private void sendHelpMsg(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "SLOTLIMITER " + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "help:");
        sender.sendMessage(ChatColor.GRAY + "----------------------------------------------");
        for (SubCommand subCommand : subCommands.values()) {
            sender.sendMessage(subCommand.help());
        }
        sender.sendMessage(ChatColor.GRAY + "----------------------------------------------");
    }

    private SubCommand getCmdIgnoreCase(String cmd) {
        for (String command : subCommands.keySet()) {
            if (cmd.equalsIgnoreCase(command)) {
                return subCommands.get(command);
            }
        }
        return null;
    }

}
