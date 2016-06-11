/*
    SlotLimiter
    Copyright (C) 2016  bman7842
    (visit the main class for more information)
*/

package me.bman7842.slotlimiter.Commands;

import org.bukkit.entity.Player;

/**
 * Created by brand_000 on 8/2/2015.
 */
public interface SubCommand {

    /**
     * @param player Command sender
     * @param args SubCommand arguments
     */
    public boolean onCommand(Player player, String[] args);

    /**
     * Returns a help message specific to the subcommand
     */
    public String help();

}
