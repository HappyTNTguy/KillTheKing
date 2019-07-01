package me.happy.king.listener;

import me.happy.king.KillTheKing;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.List;

public class KillTheKingListener implements Listener {

    private KillTheKing plugin;

    public KillTheKingListener(KillTheKing plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (p == plugin.getKingManager().getKing()) {
            List<ItemStack> list = e.getDrops();
            Iterator<ItemStack> i = list.iterator();
            while (i.hasNext()) {
                ItemStack item = i.next();
                if (((item.hasItemMeta()) && (item.getItemMeta().hasLore()) && (item.getType() == Material.DIAMOND_SWORD)) || (item.getType() == Material.DIAMOND_HELMET) || (item.getType() == Material.DIAMOND_CHESTPLATE) || (item.getType() == Material.DIAMOND_LEGGINGS) || (item.getType() == Material.DIAMOND_BOOTS)) {
                    i.remove();
                    break;
                }
            }
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6&lWinner&7: &f" + p.getKiller().getName()));
            plugin.getKingManager().active = false;
        }
    }
}
