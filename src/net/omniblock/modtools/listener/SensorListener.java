package net.omniblock.modtools.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import net.omniblock.modtools.api.SpigotSensorAPI;
import net.omniblock.network.library.utils.TextUtil;

public class SensorListener implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void checkSensor(BlockBreakEvent e) {

		if(e.getBlock().getType() == Material.DIAMOND_ORE || e.getBlock().getType() == Material.EMERALD_ORE)
			for(Player player : SpigotSensorAPI.getSensorPlayers())
				if(e.getPlayer() != player)
					player.sendMessage(TextUtil.format("&9&lSENSOR: &7El jugador &f" + e.getPlayer().getName() + " &7ha picado una " + (e.getBlock().getType() == Material.DIAMOND_ORE ? "&bMena de Diamante" : "&aMena de Esmeralda") + "&7!"));

	}
	
}
