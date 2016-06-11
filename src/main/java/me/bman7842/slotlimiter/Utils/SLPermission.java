/*
    SlotLimiter
    Copyright (C) 2016  bman7842
    (visit the main class for more information)
*/

package me.bman7842.slotlimiter.Utils;

import org.bukkit.permissions.Permission;

/**
 * Created by brand on 6/7/2016.
 */
public enum SLPermission {

    SL_ADMIN("sl.admin");

    private Permission permission;

    SLPermission(String permissionStr) {
        this.permission = new Permission(permissionStr);
    }

    public Permission getPermission() {
        return permission;
    }
}
