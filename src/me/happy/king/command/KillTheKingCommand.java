package me.happy.king.command;

import me.happy.king.KillTheKing;
import me.happy.king.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KillTheKingCommand implements CommandExecutor {

    private KillTheKing plugin;

    public KillTheKingCommand(KillTheKing plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ((args.length == 2) && (args[0].equalsIgnoreCase("start"))) {
            Player kingPerson = Bukkit.getPlayer(args[1]);

            if (kingPerson == null) {
                sender.sendMessage(ChatColor.RED + "That player is not online!");
                return true;
            }

            if (plugin.getKingManager().isActive()) {
                sender.sendMessage(ChatColor.RED + "A king event is already in progress!");
                return true;
            }

            plugin.getKingManager().active = true;
            plugin.getKingManager().king = kingPerson;
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou started the king event!"));
            message(kingPerson);

            return true;
        }
        if ((args.length == 1) && (args[0].equalsIgnoreCase("end"))) {
            if (!plugin.getKingManager().isActive()) {
                sender.sendMessage(ChatColor.RED + "THe king event is not active!");
                return true;
            }
            plugin.getKingManager().active = false;
            sender.sendMessage(ChatColor.RED + "You ended the king event!");
            return true;
        }
        if ((args.length == 1) && (args[0].equalsIgnoreCase("giveInventory")) && (plugin.getKingManager().isActive())) {
            clearInventory(plugin.getKingManager().getKing());
            giveItems(plugin.getKingManager().getKing());
            sender.sendMessage(ChatColor.GREEN + "You gave " + plugin.getKingManager().getKing().getName() + " the items!");
            return true;
        }
        if ((args.length == 1) && (args[0].equalsIgnoreCase("giveInventory")) && (!plugin.getKingManager().isActive())) {
            sender.sendMessage(ChatColor.RED + "The king event is not active!");
            return true;
        }
        if ((args.length == 0) || (args.length > 2)) {
            sender.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "-----------------------------------------------------");
            sender.sendMessage(ChatColor.GOLD + ChatColor.GOLD.toString() + "King Help");
            sender.sendMessage(ChatColor.YELLOW + "/kingevent start <player>");
            sender.sendMessage(ChatColor.YELLOW + "/kingevent end");
            sender.sendMessage(ChatColor.YELLOW + "/kingevent giveInventory");
            sender.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "-----------------------------------------------------");
            return true;
        }
        return true;
    }

    private void clearInventory(Player target) {
        target.removePotionEffect(PotionEffectType.HUNGER);
        target.setFoodLevel(20);
        target.getInventory().clear();
        target.getInventory().setHelmet(new ItemStack(Material.AIR));
        target.getInventory().setChestplate(new ItemStack(Material.AIR));
        target.getInventory().setLeggings(new ItemStack(Material.AIR));
        target.getInventory().setBoots(new ItemStack(Material.AIR));
    }

    private void giveItems(Player player) {
        ItemStack ENDERPEARL = new ItemBuilder(Material.ENDER_PEARL, 16).toItemStack();
        ItemStack STEAK = new ItemBuilder(Material.COOKED_BEEF, 64).toItemStack();
        ItemStack POTION = new ItemStack(Material.POTION, 1, (short) 16421);
        player.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7).addUnsafeEnchantment(Enchantment.DURABILITY, 10).toItemStack());
        player.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7).addUnsafeEnchantment(Enchantment.DURABILITY, 10).toItemStack());
        player.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7).addUnsafeEnchantment(Enchantment.DURABILITY, 10).toItemStack());
        player.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7).addUnsafeEnchantment(Enchantment.DURABILITY, 10).toItemStack());
        for (int i = 0; i < 36; i++) {
            player.getInventory().setItem(i, POTION);
        }
        player.getInventory().setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 5).addUnsafeEnchantment(Enchantment.DURABILITY, 10).toItemStack());
        player.getInventory().setItem(1, ENDERPEARL);
        player.getInventory().setItem(8, STEAK);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 1, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 1, false));
    }

    private void message(Player player) {
        Bukkit.broadcastMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "--------------------");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "Kill the King");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&eKing&7: &f" + player.getName()));
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&eKing Location&7: &f" + Math.round(player.getLocation().getX()) + ", " + Math.round(player.getLocation().getY()) + ", " + Math.round(player.getLocation().getZ())));
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "--------------------");
    }
}
