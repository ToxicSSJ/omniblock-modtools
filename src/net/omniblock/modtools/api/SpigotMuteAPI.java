package net.omniblock.modtools.api;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.omniblock.modtools.ModTools;
import net.omniblock.modtools.Tool;
import net.omniblock.modtools.commands.MuteCommand;
import net.omniblock.modtools.listener.MuteListener;
import net.omniblock.network.library.utils.NumberUtil;

/**
 * 
 * Esta API es utilizada para
 * manejar todo lo relacionado
 * con los silencios o muteos.
 * 
 * @author zlToxicNetherlz
 *
 */
public class SpigotMuteAPI extends Tool {

	/**
	 * 
	 * Este mapa contiene como llave a el jugador muteado
	 * y como valor la fecha para desmutearse.
	 * 
	 */
	private static final Map<Player, Date> MUTE_TIME = new HashMap<Player, Date>();
	
	@Override
	public void install() {
		
		CommandExecutor cmd = new MuteCommand();
		Listener listener = new MuteListener();
		
		ModTools.getInstance().getCommand("mute").setExecutor(cmd);
		ModTools.getInstance().getCommand("silenciar").setExecutor(cmd);
		
		ModTools.getInstance().getCommand("unmute").setExecutor(cmd);
		ModTools.getInstance().getCommand("dessilenciar").setExecutor(cmd);
		
		ModTools.getInstance().getServer().getPluginManager().registerEvents(listener, ModTools.getInstance());
		
	}
	
	/**
	 * 
	 * Este metodo permite silenciar a un jugador
	 * por la cantidad de segundos deseada. Sin embargo
	 * tiene un limite de 5 días.
	 * 
	 * @param player El jugador que será silenciado.
	 * @param seconds Los segundos totales del silencio.
	 * @return true si el tiempo es valido.
	 */
	public static boolean mutePlayer(Player player, int seconds) {
		
		if(seconds <= 0)
			return false;
		
		if(seconds > 432000)
			return false;
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, seconds);
		
		MUTE_TIME.put(player, calendar.getTime());
		return true;
		
	}
	
	/**
	 * 
	 * Este metodo permite remover el muteo a
	 * un jugador.
	 * 
	 * @param player El jugador muteado.
	 */
	public static void clearPlayer(Player player) {
		
		if(MUTE_TIME.containsKey(player))
			MUTE_TIME.remove(player);
		
		return;
		
	}
	
	/**
	 * 
	 * Este metodo permite saber si un jugador
	 * se encuentra muteado o no.
	 * 
	 * @param player El jugador a comprobar.
	 * @return true si está muteado.
	 */
	public static boolean hasMute(Player player) {
		return MUTE_TIME.containsKey(player);
	}
	
	/**
	 * 
	 * Este metodo permite recibir la cantidad de
	 * segundos restantes para desmutear un
	 * jugador.
	 * 
	 * @param player El jugador.
	 * @return La cantidad de segundos faltantes
	 * para su desmuteo.
	 */
	public static long getTime(Player player) {
		
		if(hasMute(player))
			return (MUTE_TIME.get(player).getTime() - new Date().getTime()) / 1000;
		
		return 0;
		
	}
	
	/**
	 * 
	 * Este metodo permite recibir la cantidad de
	 * segundos en base a un formato simple que
	 * se basa en un numero y despues el identificador
	 * de tiempo siendo:
	 * <br><br>
	 * <table border="0">
	 *	  <tr>
	 *	    <td>Segundos</td>
	 *	    <td>s</td>
	 *	  </tr>
	 *	  <tr>
	 *	    <td>Minutos</td>
	 *	    <td>m</td>
	 *	  </tr>
	 *	  <tr>
	 *	    <td>Horas</td>
	 *	    <td>h</td>
	 *	  </tr>
	 *	  <tr>
	 *	    <td>Días</td>
	 *	    <td>d</td>
	 *	  </tr>
	 * </table>
	 * 
	 * @param format El formato a extraer.
	 * @return Los segundos en base al formato, devolverá
	 * -1 si el formato es invalido.
	 */
	public static int getTimeByFormat(String format) {
		
		if(format.isEmpty())
			return -1;
		
		List<String> valid_ends = Arrays.asList("s", "m", "h", "d");
		List<Integer> seconds_data = Arrays.asList(1, 60, 3600, 86400);
		
		for(String end : valid_ends)
			if(format.endsWith(end)) {
				
				int index = valid_ends.indexOf(end);
				int amount = NumberUtil.valueOf(format.replaceAll(end, ""));
				
				if(amount == 0)
					return -1;
				
				return seconds_data.get(index) * amount;
				
			}	
		
		return -1;
		
	}

}
