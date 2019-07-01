package me.happy.king;

import org.bukkit.entity.Player;

public class KillTheKingManager {

    public boolean active = false;

    public Player king;

    public boolean isActive() {
        return active;
    }

    public Player getKing() {
        return king;
    }
}
