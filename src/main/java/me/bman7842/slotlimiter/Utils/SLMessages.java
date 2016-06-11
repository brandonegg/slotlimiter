/*
    SlotLimiter
    Copyright (C) 2016  bman7842
    (visit the main class for more information)
*/

package me.bman7842.slotlimiter.Utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by brand on 6/7/2016.
 */
public class SLMessages {

    private static final int CENTER_PX = 154;

    public enum MessageType {
        PREFIX(ChatColor.GRAY+"["+ ChatColor.RED+"SL"+ ChatColor.GRAY+"]"), ERROR(ChatColor.RED + "ERROR!" + ChatColor.GRAY), ALERT(ChatColor.YELLOW + "ALERT!" + ChatColor.GRAY), SUCCESS(ChatColor.GREEN + "SUCCESS!" + ChatColor.GRAY);

        private String message;

        MessageType(String message) {
            this.message = message;
        }

        public String getTag() {
            return message;
        }
    }

    public static String noPermMessage() {
        return (formatError("You don't have permission to run this command."));
    }

    public static String formatCommandHelp(String command, String commandInfo) {
        return (ChatColor.WHITE+" - "+ ChatColor.RED+"/sl "+command+": "+ ChatColor.GRAY+commandInfo);
    }

    public static String formatError(String msg) {
        return (MessageType.PREFIX.getTag()+" "+ MessageType.ERROR.getTag()+" "+ ChatColor.GRAY+msg);
    }

    public static String formatAlert(String msg) {
        return (MessageType.PREFIX.getTag()+" "+ MessageType.ALERT.getTag()+" "+ ChatColor.GRAY+msg);
    }

    public static String formatSuccess(String msg) {
        return (MessageType.PREFIX.getTag()+" "+ MessageType.SUCCESS.getTag()+" "+ ChatColor.GRAY+msg);
    }

    public static String formatCenteredMessage(String message){
        if(message == null || message.equals("")) return ("");
        message = ChatColor.translateAlternateColorCodes('§', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
                continue;
            }else if(previousCode){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                    continue;
                }else isBold = false;
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        return (sb.toString() + message);
    }

    public static void sendYesNoButtons(Player p, String question, String yesCmd, String noCmd, String yesInfo, String noInfo) {
        TextComponent mainLine = new TextComponent("                      [");
        mainLine.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        mainLine.setBold(true);
        TextComponent sureButton = new TextComponent("Sure");
        sureButton.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        sureButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, yesCmd));
        sureButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(yesInfo).color(net.md_5.bungee.api.ChatColor.GRAY).create()));
        TextComponent divider = new TextComponent(" | ");
        divider.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        divider.setBold(true);
        TextComponent leftBracket = new TextComponent("[");
        leftBracket.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        leftBracket.setBold(true);
        TextComponent rightBracket = new TextComponent("]");
        rightBracket.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        rightBracket.setBold(true);
        TextComponent noButton = new TextComponent("Nope");
        noButton.setColor(net.md_5.bungee.api.ChatColor.RED);
        noButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, noCmd));
        noButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(noInfo).color(net.md_5.bungee.api.ChatColor.GRAY).create()));

        mainLine.addExtra(sureButton);
        mainLine.addExtra(rightBracket);
        mainLine.addExtra(divider);
        mainLine.addExtra(leftBracket);
        mainLine.addExtra(noButton);
        mainLine.addExtra(rightBracket);

        p.sendMessage(SLMessages.formatCenteredMessage(net.md_5.bungee.api.ChatColor.DARK_GRAY + "-----------------------------------"));
        p.sendMessage(" ");
        p.sendMessage(SLMessages.formatCenteredMessage(net.md_5.bungee.api.ChatColor.GRAY + question));
        p.sendMessage(" ");
        p.spigot().sendMessage(mainLine);
        p.sendMessage(" ");
        p.sendMessage(SLMessages.formatCenteredMessage(net.md_5.bungee.api.ChatColor.DARK_GRAY + "-----------------------------------"));
    }

}
