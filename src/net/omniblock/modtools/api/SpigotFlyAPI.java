package net.omniblock.modtools.api;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import net.omniblock.modtools.ModTools;
import net.omniblock.modtools.Tool;
import net.omniblock.modtools.commands.FlyCommand;
import net.omniblock.network.handlers.base.sql.util.Resolver;
import net.omniblock.network.library.utils.TextUtil;

/**
 * 
 * Esta API es utilizada para
 * manejar todo lo relacionado
 * con el modo vuelo. (fly)
 * 
 * @author zlToxicNetherlz
 *
 */
public class SpigotFlyAPI extends Tool {

	/**
	 * 
	 * Este set contiene las Network ID's de
	 * los jugadores que tienen el modo vuelo
	 * activado.
	 * 
	 */
	public static final Set<String> FLY_PLAYERS = new HashSet<String>();
	
	@Override
	public void install() {
		
		CommandExecutor cmd = new FlyCommand();
		
		ModTools.getInstance().getCommand("fly").setExecutor(cmd);
		ModTools.getInstance().getCommand("volar").setExecutor(cmd);
		
	}
	
	/**
	 * 
	 * Este metodo permite saber si un
	 * jugador tiene el modo vuelo activado.
	 * 
	 * @param player El jugador.
	 * @return true si tiene el modo vuelo
	 * activado.
	 */
	public static boolean hasFly(Player player) {
		
		String networkID = Resolver.getNetworkID(player);
		
		return FLY_PLAYERS.contains(networkID);
		
	}
	
	/**
	 * 
	 * Este metodo permite activar o desactivar
	 * el modo vuelo en base al estado actual
	 * del jugador.
	 * 
	 * @param player El jugador.
	 */
	public static void toggleFly(Player player) {
		
		String networkID = Resolver.getNetworkID(player);
		
		if(player.isFlying()){

			FLY_PLAYERS.add(Resolver.getNetworkID(player));
			
			player.setAllowFlight(false);
			player.setFlying(false);
			
			player.sendMessage(TextUtil.format("&7Has desactivado el modo vuelo. (fly)"));
			return;
			
		}
		
		if(FLY_PLAYERS.contains(networkID))
			FLY_PLAYERS.remove(networkID);
		
		player.setAllowFlight(true);
		player.setFlying(true);
		
		player.sendMessage(TextUtil.format("&aHas activado el modo vuelo. (fly)"));
		
	}

}
