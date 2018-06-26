package ninja.oscaz.banknotes.command;

import co.insou.commands.CommandConsumer;
import ninja.oscaz.banknotes.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WithdrawCommand extends CommandConsumer {

    public WithdrawCommand() {
        super("withdraw", true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length != 1) {
            this.sendUsage(sender);
            return;
        }
        double amount;
        try {
            amount = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            this.sendUsage(sender);
            return;
        }
        Player player = (Player) sender;
        Main.economy.withdrawPlayer(this.getOffline(player), amount);
        for (int i = 0; i < 36; i++) {
            if (player.getInventory().getItem(i) == null || player.getInventory().getItem(i).getType() == Material.AIR) {
                player.getInventory().setItem(i, Main.getBankNote(amount));
                return;
            }
        }
        sender.sendMessage(ChatColor.RED + "You must have an empty slot!");
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Usage: /withdraw <amount>");
    }

    private OfflinePlayer getOffline(Player player) {
        return Bukkit.getOfflinePlayer(player.getUniqueId());
    }
}
