package net.omniblock.modtools.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.omniblock.modtools.ModTools;
import net.omniblock.modtools.Tool;
import net.omniblock.modtools.commands.VanishCommand;
import net.omniblock.modtools.listener.VanishListener;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.adapters.GameJOINAdapter;

/**
 * 
 * Esta API es utilizada para
 * manejar todo lo relacionado
 * con el modo vanish.
 * 
 * @author zlToxicNetherlz
 *
 */
public class SpigotVanishAPI extends Tool {

	/**
	 * 
	 * Este set contiene las Network ID's de
	 * los jugadores que tienen el modo vanish
	 * activado.
	 * 
	 */
	public static final Set<String> VANISHED_PLAYERS = new HashSet<String>();
	
	@Override
	public void install() {
		
		CommandExecutor cmd = new VanishCommand();
		Listener listener = new VanishListener();
		
		ModTools.getInstance().getCommand("vanish").setExecutor(cmd);
		ModTools.getInstance().getCommand("v").setExecutor(cmd);
		
		ModTools.getInstance().getServer().getPluginManager().registerEvents(listener, ModTools.getInstance());
		
	}
	
	/**
	 * 
	 * Este metodo devuelve los jugadores
	 * que poseen el vanish activado y están
	 * conectados al servidor.
	 * 
	 * @return La lista de jugadores con el
	 * vanish activado.
	 */
	public static List<Player> getVanishedPlayers(){
		
		List<Player> result = new ArrayList<Player>();
		
		for(String name : VANISHED_PLAYERS) {
			
			Player player = Bukkit.getPlayer(name);
			
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
	 * jugador tiene el modo vanish activado.
	 * 
	 * @param player El jugador.
	 * @return true si tiene el modo vanish
	 * activado.
	 */
	public static boolean hasVanish(Player player) {
		
		String networkID = player.getName();
		return VANISHED_PLAYERS.contains(networkID);
		
	}
	
	/**
	 * 
	 * Este metodo permite activar o desactivar
	 * el modo vanish en base al estado actual
	 * del jugador.
	 * 
	 * @param player El jugador.
	 * @param args Los argumentos que afectan
	 * el vanish.
	 */
	public static void toggleVanish(Player player, String[] args) {
		
		if(hasVanish(player)) {

			if(args.length >= 1)
				if(args[0].equalsIgnoreCase("bolt")) {
					
					for(int i = 0; i < 2; i++)
						player.getWorld().strikeLightningEffect(player.getLocation());
					
				}
				
			if(GameJOINAdapter.BLACK_LIST.contains(player.getName()))
				GameJOINAdapter.BLACK_LIST.remove(player.getName());
			
			if(VANISHED_PLAYERS.contains(player.getName()))
				VANISHED_PLAYERS.remove(player.getName());
			
			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage(TextUtil.format("&7Has desactivado el modo vanish."));
			
			for(Player p : Bukkit.getOnlinePlayers())
				if(p != player)
					p.showPlayer(player);
				
			//PacketPlayOutPlayerInfo info = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) player).getHandle());
	        
	        //for(Player p : Bukkit.getOnlinePlayers())
	            //((CraftPlayer) p).getHandle().playerConnection.sendPacket(info);
			
			return;
			
		}
		
		VANISHED_PLAYERS.add(player.getName());
		GameJOINAdapter.BLACK_LIST.add(player.getName());
		
		player.setGameMode(GameMode.SPECTATOR);
		player.sendMessage(TextUtil.format("&aHas activado el modo vanish."));
		
		for(Player p : Bukkit.getOnlinePlayers())
			if(p != player)
				p.hidePlayer(player);
		
		//PacketPlayOutPlayerInfo info = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer) player).getHandle());
        
        //for(Player p : Bukkit.getOnlinePlayers())
            //((CraftPlayer) p).getHandle().playerConnection.sendPacket(info);
        
		return;
		
	}

}
