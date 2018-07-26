package net.omniblock.modtools.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.modtools.ModTools;
import net.omniblock.modtools.api.SpigotVanishAPI;

public class VanishListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void checkVanish(PlayerJoinEvent e) {

		if(SpigotVanishAPI.hasVanish(e.getPlayer())) {

			new BukkitRunnable() {
				
				@Override
	            public void run() {
					
					for(Player p : Bukkit.getOnlinePlayers())
						if(p != e.getPlayer())
							p.hidePlayer(e.getPlayer());
	                 
	            }
				
			}.runTaskLater(ModTools.getInstance(), 2L);
			
			e.getPlayer().setGameMode(GameMode.SPECTATOR);
			return;

		}

		new BukkitRunnable() {
			
			@Override
            public void run() {
				
				for(Player player : SpigotVanishAPI.getVanishedPlayers()) {
					
					for(Player p : Bukkit.getOnlinePlayers())
						if(p != player)
							p.hidePlayer(player);
					
				}
                 
            }
			
		}.runTaskLater(ModTools.getInstance(), 2L);

	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void checkVanish(PlayerTeleportEvent e) {

		if(SpigotVanishAPI.hasVanish(e.getPlayer())) {

			e.getPlayer().setGameMode(GameMode.SPECTATOR);
			return;

		}

	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void checkVanish(PlayerChangedWorldEvent e) {
		
		if(SpigotVanishAPI.hasVanish(e.getPlayer())) {

			e.getPlayer().setGameMode(GameMode.SPECTATOR);
			return;

		}
		
	}
	
}
