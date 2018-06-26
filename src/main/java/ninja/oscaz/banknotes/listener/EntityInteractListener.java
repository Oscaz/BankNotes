package ninja.oscaz.banknotes.listener;

import ninja.oscaz.banknotes.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EntityInteractListener implements Listener {

    @EventHandler
    public void on(EntityInteractEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER) return;
        Player player = (Player) event.getEntity();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.PAPER) {
            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "Bank Note")) {
                String stringAmount = item.getItemMeta().getLore().get(0).split(ChatColor.GRAY + "Worth: " + ChatColor.GREEN + "" + ChatColor.BOLD + "\\$")[1];
                double amount;
                try {
                    amount = Double.parseDouble(stringAmount);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Error! Invalid amount formatting.");
                    return;
                }
                Main.economy.depositPlayer(getOffline(player), amount);
            }
        }
    }

    private OfflinePlayer getOffline(Player player) {
        return Bukkit.getOfflinePlayer(player.getUniqueId());
    }

}
