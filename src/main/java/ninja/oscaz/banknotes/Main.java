package ninja.oscaz.banknotes;

import net.milkbowl.vault.economy.Economy;
import ninja.oscaz.banknotes.command.WithdrawCommand;
import ninja.oscaz.banknotes.listener.EntityInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Main extends JavaPlugin {

    public static Economy economy;

    @Override
    public void onEnable() {
        this.setEconomy();
        this.getCommand("withdraw").setExecutor(new WithdrawCommand());
        Bukkit.getPluginManager().registerEvents(new EntityInteractListener(), this);
    }

    @Override
    public void onDisable() {

    }

    private void setEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer()
                .getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
            return;
        }

        throw new RuntimeException("No economy found!");
    }

    public static ItemStack getBankNote(double amount) {
        ItemStack note = new ItemStack(Material.PAPER);
        ItemMeta noteMeta = note.getItemMeta();
        noteMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Bank Note");
        noteMeta.setLore(
                Arrays.asList(
                        ChatColor.GRAY + "Worth: " + ChatColor.GREEN + "" + ChatColor.BOLD + "$" + amount,
                        ChatColor.DARK_GRAY + "Right click to redeem this note."
                )
        );
        note.setItemMeta(noteMeta);
        return note;
    }

}
