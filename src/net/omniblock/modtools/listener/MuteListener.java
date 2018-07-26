package net.omniblock.modtools.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import io.netty.util.internal.ThreadLocalRandom;
import net.omniblock.modtools.api.SpigotMuteAPI;
import net.omniblock.network.library.utils.TextUtil;

public class MuteListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void checkMute(AsyncPlayerChatEvent e) {

		if(SpigotMuteAPI.hasMute(e.getPlayer())) {

			if(SpigotMuteAPI.getTime(e.getPlayer()) <= 0) {
				
				SpigotMuteAPI.clearPlayer(e.getPlayer());
				return;
				
			}
			
			if(ThreadLocalRandom.current().nextBoolean())
				e.getPlayer().sendMessage(TextUtil.format("&7¡Estás silenciado, aún te quedán &4" + SpigotMuteAPI.getTime(e.getPlayer()) + "&7 segundos!"));
			
			e.setCancelled(true);
			return;

		}

	}
	
}
