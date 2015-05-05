package me.br456.Telepads;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class IconMenu implements Listener {
	
	SettingsManager settings = SettingsManager.getInstance();
	private Inventory inv;
	private String name;
	private int slots;
	private InventoryClickEvent event;
	private Plugin plugin;
	
	public IconMenu(String name, int slots, Plugin plugin) {
		this.name = name;
		this.slots = slots;
		this.plugin = plugin;
		
		inv = Bukkit.getServer().createInventory(null, slots, name);
		plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	public String getName() { return name; }
	public int getSlots() { return slots; }
	public InventoryClickEvent getEvent() { return event; }
	
	public void open(Player player) {
		player.openInventory(inv);
		return;
	}
	
	public void addItem(int pos, ItemStack item, String name) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		item.setItemMeta(im);
		
		inv.setItem(pos, item);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		boolean exists = false;
		if(event.getInventory().getName() == this.name) {
			event.setCancelled(true);
			if(event.getCurrentItem() == null) return;
			for(String key : Telepads.getTelepads().keySet()) {
				if(Telepads.getTelepads().get(key).getPos() == event.getSlot()) {
					exists = true;
				}
			}
			if(exists == false) return;
			if(Telepads.getTelepads().containsKey(event.getCurrentItem().getItemMeta().getDisplayName())) {
				
				final Player player = (Player)event.getWhoClicked();
            	final String name = event.getCurrentItem().getItemMeta().getDisplayName();
            	
            	if(player.hasPermission("telepad.teleport.all") || player.hasPermission("telepad.teleport" + name)){ 
            		
            		player.sendMessage(ChatColor.BLUE + "Teleporting to " + name + " in " 
                			+ settings.getConfig().getInt("Teleport Delay") + " seconds");
                	player.closeInventory();
                	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                        	player.teleport(Telepads.getTelepads().get(name).getLocation().getLocation());
                        	player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE2, 1, 1);
                        }
                    }, settings.getConfig().getInt("Teleport Delay") * 20);	 
                	return;
                	
            	} else {
            		player.sendMessage(ChatColor.RED + "You don't have permission to do this!");
            		return;
            	}
			}
		}
	}
}