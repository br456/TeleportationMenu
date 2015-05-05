package me.br456.Telepads;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TelepadCommand implements CommandExecutor{
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("You must be a player!");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getLabel().equalsIgnoreCase("telepad")) {
			if(args.length == 0) {
				sendHelp(player);
				return true;
			}
			
			//Start create
			
			if(args[0].equalsIgnoreCase("create")) {
				if(!player.hasPermission("telepad.create")) return true;
				if(args.length == 1) {
					sendHelp(player);
					return true;
				}
				
				if(args.length == 2) {
					sendHelp(player);
					return true;
				}
				
				if(args.length == 3) {
					String teleName = args[1];
					int id;
					Location pLoc = player.getLocation();
					
					if(pLoc.getBlock().getType() != Material.STONE_PLATE 
							|| pLoc.getBlock().getRelative(BlockFace.DOWN).getType() != Material.LAPIS_BLOCK) {
						player.sendMessage(ChatColor.RED + "You must be on a lapis block and pressure plate to create a telepad!");
						return true;
					}
					
					if(Telepads.getTelepads().containsKey(teleName)) {
						player.sendMessage(ChatColor.RED + "This telepad already exists!");
						return true;
					}
					
					try {
						id = Integer.parseInt(args[2]);
					} catch( NumberFormatException e) {
						e.printStackTrace();
						sendHelp(player);
						id = 0;
						return true;
					}
					
					
					int pos = 0;
					
					for(String key : Telepads.getTelepads().keySet()) {
						if(Telepads.getTelepads().get(key).getPos() >= pos) {
							pos = Telepads.getTelepads().get(key).getPos() + 1;
						}
					}

					ItemStack is = new ItemStack(Material.getMaterial(id), 1);
					
					Telepads.getTelepads().put(teleName, new Pad(teleName, new SerializableLocation(pLoc), pos, new SerializableItemStack(is)));
					player.sendMessage(ChatColor.BLUE + "Create telepad " + teleName + " at the location " + 
							"X: " + (int)Math.round(pLoc.getX()) +
							" Y: " + (int)Math.round(pLoc.getY()) +
							" Z: " + (int)Math.round(pLoc.getZ()) +
							" World: " + pLoc.getWorld().getName() + " with the item id of " + is.getTypeId() + ":" + is.getDurability());
				}
				
				if(args.length == 4) {
					String teleName = args[1];
					int id, dmgV;
					Location pLoc = player.getLocation();
					
					if(pLoc.getBlock().getType() != Material.STONE_PLATE 
							|| pLoc.getBlock().getRelative(BlockFace.DOWN).getType() != Material.LAPIS_BLOCK) {
						player.sendMessage(ChatColor.RED + "You must be on a lapis block and pressure plate to create a telepad!");
						return true;
					}
					
					if(Telepads.getTelepads().containsKey(teleName)) {
						player.sendMessage(ChatColor.RED + "This telepad already exists!");
						return true;
					}
					
					try {
						id = Integer.parseInt(args[2]);
					} catch( NumberFormatException e) {
						sendHelp(player);
						id = 0;
						return true;
					}
					
					try {
						dmgV = Integer.parseInt(args[3]);
					} catch( NumberFormatException e) {
						sendHelp(player);
						dmgV = 0;
						return true;
					}
					
					
					int pos = 0;
					
					for(String key : Telepads.getTelepads().keySet()) {
						if(Telepads.getTelepads().get(key).getPos() >= pos) {
							pos = Telepads.getTelepads().get(key).getPos() + 1;
						}
					}
					
					ItemStack is = new ItemStack(Material.getMaterial(id), 1, (short) dmgV);
					
					Telepads.getTelepads().put(teleName, new Pad(teleName, new SerializableLocation(pLoc), pos, new SerializableItemStack(is)));
					player.sendMessage(ChatColor.BLUE + "Create telepad " + teleName + " at the location " + 
							"X: " + pLoc.getX() +
							" Y: " + pLoc.getY() +
							" Z: " + pLoc.getZ() +
							" World: " + pLoc.getWorld() + " with the item id of " + is.getTypeId() + ":" + is.getDurability());
				}
				
				if(args.length == 5) {
					sendHelp(player);
					return true;
				}
				return true;
			}
			
			//End create
			
			if(args.length ==  1) {
				
				if(args[0].equalsIgnoreCase("list")) {
					if(!player.hasPermission("telepad.list")) return true;
					player.sendMessage(ChatColor.BLUE + "Telepads:");
					
					for(String key : Telepads.getTelepads().keySet()) {
						player.sendMessage(ChatColor.AQUA + key);
					}
					
					return true;
				} else {
					sendHelp(player);
					return true;
				}
				
			}
			
			if(args.length == 2) {

				if(args[0].equalsIgnoreCase("remove")) {
					if(!player.hasPermission("telepad.remove")) return true;
					String teleName = args[1];
					
					if(!Telepads.getTelepads().containsKey(teleName)) {
						player.sendMessage(ChatColor.RED + "Invalid telepad '" + teleName + "'!");
						return true;
					}
					
					Telepads.getTelepads().remove(teleName);
					player.sendMessage(ChatColor.BLUE + "Removed " + teleName);
				}
				
			}
			
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("setpos")) {
					if(!player.hasPermission("telepad.setpos")) return true;
					String teleName = args[1];
					int pos;
					boolean isFull = false;
					
					try {
						pos = Integer.parseInt(args[2]);
					} catch( NumberFormatException e) {
						sendHelp(player);
						pos = 0;
						return true;
					}
					
					if(!Telepads.getTelepads().containsKey(teleName)) {
						player.sendMessage(ChatColor.RED + "This telepad dosn't exist!");
						return true;
					}
					
					for(String key : Telepads.getTelepads().keySet()) {
						if(Telepads.getTelepads().get(key).getPos() == pos) {
							isFull = true;
						}
					}
					
					if(isFull == true) {
						player.sendMessage(ChatColor.RED + "There is already a telepad at this position!");
						return true;
					} else {
						Telepads.getTelepads().get(teleName).setPos(pos);
						player.sendMessage(ChatColor.BLUE + "Replaced telepad at " + pos + " with " + teleName);
					}				
					
				}
			}
			
			if(args.length == 4) {
				sendHelp(player);
				return true;
			}
		}
		return true;
	}
	
	private void sendHelp(Player player) {
		player.sendMessage(ChatColor.BLUE + "---====Telepad Help====---");
		player.sendMessage(ChatColor.BLUE + "/telepad list: " + ChatColor.AQUA + "Lists all telepads");
		player.sendMessage(ChatColor.BLUE + "/telepad create <telepadname> <item id> <data value>: " + 
				ChatColor.AQUA + "Creates a telepad");
		player.sendMessage(ChatColor.BLUE + "/telepad remove <telepadname>: " + ChatColor.AQUA + "Removes a telepad");
		player.sendMessage(ChatColor.BLUE + "/telepad setpos <telepadname> <pos>: " + ChatColor.AQUA + "Sets a telepads position");
	}
}
