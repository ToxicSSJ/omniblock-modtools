package net.omniblock.modtools.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.omniblock.modtools.ModTools;
import net.omniblock.modtools.Tool;
import net.omniblock.modtools.commands.SensorCommand;
import net.omniblock.modtools.listener.SensorListener;
import net.omniblock.network.handlers.base.sql.util.Resolver;
import net.omniblock.network.library.utils.TextUtil;

/**
 * 
 * Esta API es utilizada para
 * manejar todo lo relacionado
 * con el modo sensor.
 * 
 * @author zlToxicNetherlz
 *
 */
public class SpigotSensorAPI extends Tool {

	/**
	 * 
	 * Este set contiene las Network ID's de
	 * los jugadores que tienen el modo sensor
	 * activado.
	 * 
	 */
	public static final Set<String> SENSOR_PLAYERS = new HashSet<String>();
	
	@Override
	public void install() {
		
		CommandExecutor cmd = new SensorCommand();
		Listener listener = new SensorListener();
		
		ModTools.getInstance().getCommand("sensor").setExecutor(cmd);
		ModTools.getInstance().getServer().getPluginManager().registerEvents(listener, ModTools.getInstance());
		
	}
	
	/**
	 * 
	 * Este metodo devuelve los jugadores
	 * que poseen el sensor activado y están
	 * conectados al servidor.
	 * 
	 * @return La lista de jugadores con el
	 * sensor activado.
	 */
	public static List<Player> getSensorPlayers(){
		
		List<Player> result = new ArrayList<Player>();
		
		for(String name : SENSOR_PLAYERS) {
			
			Player player = Bukkit.getPlayer(Resolver.getLastNameByNetworkID(name));
			
			if(player == null)
				continue;
			
			result.add(player);
			continue;
			
		}
		
		return result;
		
	}
	
	/**
	 * 
	 * Este metodo permite saber si un
	 * jugador tiene el modo sensor activado.
	 * 
	 * @param player El jugador.
	 * @return true si tiene el modo sensor
	 * activado.
	 */
	public static boolean hasSensor(Player player) {
		
		String networkID = Resolver.getNetworkID(player);
		
		return SENSOR_PLAYERS.contains(networkID);
		
	}
	
	/**
	 * 
	 * Este metodo permite activar o desactivar
	 * el modo sensor en base al estado actual
	 * del jugador.
	 * 
	 * @param player El jugador.
	 */
	public static void toggleSensor(Player player) {
		
		String networkID = Resolver.getNetworkID(player);
		
		if(!hasSensor(player)){

			SENSOR_PLAYERS.add(Resolver.getNetworkID(player));
			player.sendMessage(TextUtil.format("&aHas activado el modo sensor."));
			
			return;
			
		}
		
		SENSOR_PLAYERS.remove(networkID);
		player.sendMessage(TextUtil.format("&7Has desactivado el modo sensor."));
		
	}

}
