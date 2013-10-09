package me.Et.buk;

import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener{

	public FileConfiguration config;
	public static Logger log;
	public HashMap<Player, Boolean> players = new HashMap<Player, Boolean>();
	public Integer playersInGame = 0;

	public void onEnable() {
		log = Logger.getLogger("Minecraft");
		config = this.getConfig();
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	public void onDisable() {

	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("whoop")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("chat")) {
					p.sendMessage("" + ChatColor.AQUA.hashCode());
				}
				if(args[0].equalsIgnoreCase("in")) {
					if (playersInGame >= 2) {
						p.sendMessage("Sorry, game's full.");
					} else {
						if(players.get(p)) {
							p.sendMessage("You're already in the game, silly face!");
						} else {
					players.put(p, true);
					playersInGame += 1;
						}
					}
				}
				if(args[0].equalsIgnoreCase("out")) {
					clearPlayer(p, false);
				}
				p.sendMessage("" + playersInGame);
			}
		}

				return false;

	}
	public void clearPlayer(Player p, Boolean leaving) {

		if (players.get(p)) {
			playersInGame += -1;
			players.put(p, false);
			p.getInventory().clear();
			p.teleport(p.getWorld().getSpawnLocation());
		} else {
			if (!(leaving)) {

			p.sendMessage("You're not even in the game.....");
			}
		}

	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		clearPlayer(p, true);

	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		players.put(p, false);
	}
	@EventHandler
	public void onItemClickEvent(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		Material m = event.getCurrentItem().getType();
		if (m != Material.AIR) {
		p.sendMessage(ChatColor.AQUA + "I dislike the fact that you just clicked on some " + m);
		}
	}
}