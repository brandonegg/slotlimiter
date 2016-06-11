/*
    SlotLimiter
    Copyright (C) 2016  bman7842
    (visit the main class for more information)
*/

package me.bman7842.slotlimiter.Listeners;

import me.bman7842.slotlimiter.Managers.GUIManager;
import me.bman7842.slotlimiter.Utils.SLMessages;
import me.bman7842.slotlimiter.Utils.SLPermission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by brand on 6/10/2016.
 */
public class ConnectionEvents implements Listener {

    private final GUIManager guiManager;

    public ConnectionEvents(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPermission(SLPermission.SL_ADMIN.getPermission())) {
            guiManager.loadBlockedSlots(p);
            p.sendMessage(SLMessages.formatAlert("Your inventory was updated."));
        }
    }

}
