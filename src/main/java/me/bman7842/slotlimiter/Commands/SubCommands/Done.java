/*
    SlotLimiter
    Copyright (C) 2016  bman7842
    (visit the main class for more information)
*/

package me.bman7842.slotlimiter.Commands.SubCommands;

import me.bman7842.slotlimiter.Commands.SubCommand;
import me.bman7842.slotlimiter.Managers.GUIManager;
import me.bman7842.slotlimiter.Managers.InventorySlotManager;
import me.bman7842.slotlimiter.Utils.SLMessages;
import me.bman7842.slotlimiter.Utils.SLPermission;
import org.bukkit.entity.Player;

/**
 * Created by brand on 6/10/2016.
 */
public class Done implements SubCommand {

    private final GUIManager guiManager;
    private final InventorySlotManager inventorySlotManager;

    public Done(GUIManager guiManager, InventorySlotManager inventorySlotManager) {
        this.guiManager = guiManager;
        this.inventorySlotManager = inventorySlotManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (!player.hasPermission(SLPermission.SL_ADMIN.getPermission())) {
            player.sendMessage(SLMessages.noPermMessage());
            return false;
        }

        if (!guiManager.isPlayerConfiguringSlots(player)) {
            player.sendMessage(SLMessages.formatError("You are not configuring any inventory, type '/sl configure' to start."));
            return false;
        }

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("sure") || args[0].equalsIgnoreCase("yes")) {
                player.sendMessage(SLMessages.formatAlert("Preparing save, converting inventory to a list."));
                inventorySlotManager.updateBlockedSlots(player.getInventory());
                player.sendMessage(SLMessages.formatSuccess("Inventory converted, saving..."));
                inventorySlotManager.saveSlotData();
                player.sendMessage(SLMessages.formatSuccess("Saved!"));

                guiManager.finishEditing(player);

                SLMessages.sendYesNoButtons(player, "Would you like to update player's inventories?", "/sl update", "", "Players online may experience short lag", "Your changes will not take affect");
            } else if (args[0].equalsIgnoreCase("nope") || args[0].equalsIgnoreCase("no")) {
                guiManager.finishEditing(player);

                player.sendMessage(SLMessages.formatSuccess("Editing stopped, no changes were made."));
            }
            return false;
        }

        SLMessages.sendYesNoButtons(player, "Would you like to save changes?", "/sl done yes", "/sl done no", "Previous changes can't be restored after click sure", "All changes made wont be saved");

        return false;
    }

    @Override
    public String help() {
        return (SLMessages.formatCommandHelp("done", "Requires the permission sl.admin, stops configuring inventory slots."));
    }

    //This makes my eyes hurt
//    public void sendYesNoButtons(Player p) {
//        TextComponent mainLine = new TextComponent("                      [");
//        mainLine.setColor(ChatColor.GRAY);
//        mainLine.setBold(true);
//        TextComponent sureButton = new TextComponent("Sure");
//        sureButton.setColor(ChatColor.GREEN);
//        sureButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sl done yes"));
//        sureButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Previous changes can't be restored after click sure").color(ChatColor.GRAY).create()));
//        TextComponent divider = new TextComponent(" | ");
//        divider.setColor(ChatColor.GRAY);
//        divider.setBold(true);
//        TextComponent leftBracket = new TextComponent("[");
//        leftBracket.setColor(ChatColor.GRAY);
//        leftBracket.setBold(true);
//        TextComponent rightBracket = new TextComponent("]");
//        rightBracket.setColor(ChatColor.GRAY);
//        rightBracket.setBold(true);
//        TextComponent noButton = new TextComponent("Nope");
//        noButton.setColor(ChatColor.RED);
//        noButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sl done no"));
//        noButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("All changes made wont be saved").color(ChatColor.GRAY).create()));
//
//        mainLine.addExtra(sureButton);
//        mainLine.addExtra(rightBracket);
//        mainLine.addExtra(divider);
//        mainLine.addExtra(leftBracket);
//        mainLine.addExtra(noButton);
//        mainLine.addExtra(rightBracket);
//
//        p.sendMessage(SLMessages.formatCenteredMessage(ChatColor.DARK_GRAY + "-----------------------------------"));
//        p.sendMessage(" ");
//        p.sendMessage(SLMessages.formatCenteredMessage(ChatColor.GRAY + "Would you like to save changes?"));
//        p.sendMessage(" ");
//        p.spigot().sendMessage(mainLine);
//        p.sendMessage(" ");
//        p.sendMessage(SLMessages.formatCenteredMessage(ChatColor.DARK_GRAY + "-----------------------------------"));
//    }
}
