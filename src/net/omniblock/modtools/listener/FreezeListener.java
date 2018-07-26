package net.omniblock.modtools.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import net.omniblock.modtools.api.SpigotFreezeAPI;
import net.omniblock.network.library.utils.TextUtil;

public class FreezeListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void checkFrozen(PlayerQuitEvent e) {

		if(SpigotFreezeAPI.hasFrozen(e.getPlayer())) {
			
			SpigotFreezeAPI.clearPlayer(e.getPlayer());
			Bukkit.broadcastMessage(TextUtil.format("&6&lSANCIONES: &cEl jugador &7" + e.getPlayer().getCustomName() + " &cserá sancionado/a debido a que se desconectó mientras estaba siendo revisado por el Staff; incumplió las normas de Omniblock Network!"));
			return;
			
		}

	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void checkFrozen(BlockBreakEvent e) {

		if(SpigotFreezeAPI.hasFrozen(e.getPlayer())) {
			
			e.setCancelled(true);
			return;
			
		}

	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void checkFrozen(PlayerTeleportEvent e) {

		if(SpigotFreezeAPI.hasFrozen(e.getPlayer())) {

			e.setTo(e.getFrom());
			e.setCancelled(true);
			return;

		}

	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void checkFrozen(PlayerMoveEvent e) {
		
		if(SpigotFreezeAPI.hasFrozen(e.getPlayer())) {

			e.setCancelled(true);
			return;

		}
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void checkFrozen(PlayerCommandPreprocessEvent e) {
		
		if(SpigotFreezeAPI.hasFrozen(e.getPlayer())) {

			e.getPlayer().sendMessage(TextUtil.format("&cNo puedes usar comandos mientras estás congelado/a."));
			e.setCancelled(true);
			return;

		}
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void checkFrozen(EntityDamageByEntityEvent e) {
		
		if(e.getDamager() instanceof Player)
			if(SpigotFreezeAPI.hasFrozen((Player) e.getDamager())) {
				
				e.setCancelled(true);
				return;
				
			}
		
		if(e.getEntity() instanceof Player)
			if(SpigotFreezeAPI.hasFrozen((Player) e.getEntity()))
				e.setCancelled(true);
		
		return;
		
	}
	
}
