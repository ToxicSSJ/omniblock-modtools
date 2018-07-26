package net.omniblock.modtools.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import net.omniblock.modtools.api.SpigotBanAPI;
import net.omniblock.network.systems.EventPatcher;

public class BanListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void checkBan(PlayerLoginEvent e) {

		if(SpigotBanAPI.checkPlayerBan(e.getPlayer())) {

			e.disallow(Result.KICK_FULL,
					SpigotBanAPI.DUEDATE.containsKey(e.getPlayer().getName())
							? new String(EventPatcher.YOURE_BANNED)
									.replaceFirst("VAR_BAN_HASH", SpigotBanAPI.DUEDATE.get(e.getPlayer().getName()).getHash())
									.replaceFirst("VAR_TO_DATE", SpigotBanAPI.DUEDATE.get(e.getPlayer().getName()).getTo())
									.replaceFirst("VAR_REASON", SpigotBanAPI.DUEDATE.get(e.getPlayer().getName()).getReason())
							: EventPatcher.YOURE_BANNED_WITHOUT_VARS);
			return;

		}

	}
	
}
