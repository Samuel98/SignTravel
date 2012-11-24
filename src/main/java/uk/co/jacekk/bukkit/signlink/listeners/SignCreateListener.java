package uk.co.jacekk.bukkit.signlink.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.SignChangeEvent;

import uk.co.jacekk.bukkit.baseplugin.v5.event.BaseListener;
import uk.co.jacekk.bukkit.signlink.SignLink;

public class SignCreateListener extends BaseListener<SignLink> {
	
	public SignCreateListener(SignLink plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onSignChange(SignChangeEvent event){
		Player player = event.getPlayer();
		
		String[] lines = event.getLines();
		
		String line;
		String location = null;
		String destination = null;
		
		for (int i = 3; i >= 0; --i){
			line = lines[i]; 
			
			if (line.startsWith("[") && line.endsWith("]")){
				if (destination == null){
					destination = line.substring(1, line.length() - 1);
				}else if (location == null){
					location = line.substring(1, line.length() - 1);
				}else{
					break;
				}
			}
		}
		
		if (location != null){
			if (player.hasPermission("signlink.create") == false){
				player.sendMessage(plugin.formatMessage(ChatColor.RED + "You do not have permission to create signs."));
				event.setCancelled(true);
				return;
			}
			
			if (plugin.locations.contains(location)){
				player.sendMessage(plugin.formatMessage(ChatColor.RED + "The location " + location + " already exists"));
			}else{
				player.sendMessage(plugin.formatMessage(ChatColor.GREEN + "The location " + location + " has been added"));
				plugin.locations.add(location, player.getLocation());
			}
		}
		
		if (destination != null && plugin.locations.contains(destination) && player.hasPermission("signlink.create") == false){
			player.sendMessage(plugin.formatMessage(ChatColor.RED + "You do not have permission to create signs."));
			event.setCancelled(true);
			return;
		}
	}
	
}
