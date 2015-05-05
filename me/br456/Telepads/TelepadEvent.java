package me.br456.Telepads;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TelepadEvent implements Listener{
	private IconMenu menu;
	SettingsManager settings = SettingsManager.getInstance();

	@EventHandler
	public void pressurePlate(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.PHYSICAL)) {
			if(event.getClickedBlock().getType() == Material.STONE_PLATE 
					&& event.getClickedBlock().getRelative(BlockFace.DOWN).getType() == Material.LAPIS_BLOCK) {
				if(!event.getPlayer().hasPermission("telepad.menu")) return;
				
				menu = new IconMenu(ChatColor.BLUE + "Telepads", 54, Telepads.getPlugin());                
				
				for(String key : Telepads.getTelepads().keySet()) {
					menu.addItem(Telepads.getTelepads().get(key).getPos(), Telepads.getTelepads().get(key).getItemStack().getItemStack(), key);
				}
				
				menu.open(event.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		final Player player = event.getPlayer();
		if(event.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.LAPIS_BLOCK && 
				event.getTo().getBlock().getType() == Material.STONE_PLATE) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Telepads.getPlugin(), new Runnable() {
                public void run() {
                	player.closeInventory();
                }
            }, 10L);
		}
	}
}
