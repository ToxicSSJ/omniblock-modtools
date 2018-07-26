package net.omniblock.modtools.api;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.omniblock.modtools.ModTools;
import net.omniblock.modtools.Tool;
import net.omniblock.modtools.commands.FreezeCommand;
import net.omniblock.modtools.listener.FreezeListener;
import net.omniblock.network.library.utils.TextUtil;

/**
 * 
 * Esta API es utilizada para
 * manejar todo lo relacionado
 * con los congelamientos.
 * 
 * @author zlToxicNetherlz
 *
 */
public class SpigotFreezeAPI extends Tool {

	/**
	 * 
	 * Este set contiene a los jugadores
	 * congelados.
	 * 
	 */
	private static final Set<Player> FROZEN_PLAYERS = new HashSet<Player>();
	
	@Override
	public void install() {
		
		CommandExecutor cmd = new FreezeCommand();
		Listener listener = new FreezeListener();
		
		ModTools.getInstance().getCommand("freeze").setExecutor(cmd);
		ModTools.getInstance().getCommand("ss").setExecutor(cmd);
		
		ModTools.getInstance().getServer().getPluginManager().registerEvents(listener, ModTools.getInstance());
		
	}
	
	/**
	 * 
	 * Este metodo permite saber si un jugador
	 * se encuentra congelado o no.
	 * 
	 * @param player El jugador a comprobar.
	 * @return true si está congelado.
	 */
	public static boolean hasFrozen(Player player) {
		return FROZEN_PLAYERS.contains(player);
	}
	
	/**
	 * 
	 * Este metodo permite remover el congelamiento
	 * a un jugador congelado.
	 * 
	 * @param player El jugador congelado.
	 */
	public static void clearPlayer(Player player) {
		
		if(FROZEN_PLAYERS.contains(player))
			FROZEN_PLAYERS.remove(player);
		
		return;
		
	}
	
	/**
	 * 
	 * Este metodo permite congelar/descongelar a un
	 * jugador.
	 * 
	 * @param moderator El moderador quien está congelando o
	 * descongelando.
	 * @param player El usuario congelado/descongelado.
	 */
	public static void toggleFrozen(Player moderator, Player player) {
		
		if(hasFrozen(player)) {
			
			if(FROZEN_PLAYERS.contains(player))
				FROZEN_PLAYERS.remove(player);
			
			moderator.sendMessage(TextUtil.format("&aHas &7descongelado&a a &7" + player.getName() + " &acorrectamente!"));
			player.sendMessage(TextUtil.format("&aHas sido descongelado/a."));
			
			player.setWalkSpeed(0.2F);
			player.setFlySpeed(0.2F);
			return;
			
		}
		
		FROZEN_PLAYERS.add(player);
		moderator.sendMessage(TextUtil.format("&aHas &4&lcongelado&a a &7" + player.getName() + " &acorrectamente!"));
		
		player.sendMessage(TextUtil.getCenteredMessage("&4¡Has sido congelado/a por " + moderator.getCustomName() + "&4, si te desconectas serás &nBANEADO/A&r&4!"));
		player.sendMessage(TextUtil.format("&6&lSANCIONES: &cA continuación conectese a nuestro Discord público: &7https://discordapp.com/invite/Mq2t8Ww &cen un máximo de 5 minutos y espere más instrucciones del moderador."));
		
		player.setWalkSpeed(0.0F);
		player.setFlySpeed(0.0F);
		return;
		
	}

}
