package net.omniblock.modtools.api;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import net.omniblock.modtools.ModTools;
import net.omniblock.modtools.Tool;
import net.omniblock.modtools.commands.KickCommand;
import net.omniblock.network.handlers.base.bases.type.RankBase;
import net.omniblock.network.library.utils.TextUtil;

/**
 * 
 * Esta API es utilizada para
 * manejar todo lo relacionado
 * con las expulsiones.
 * 
 * @author zlToxicNetherlz
 *
 */
public class SpigotKickAPI extends Tool {

	@Override
	public void install() {
		
		CommandExecutor cmd = new KickCommand();
		
		ModTools.getInstance().getCommand("kick").setExecutor(cmd);
		ModTools.getInstance().getCommand("kickear").setExecutor(cmd);
		
	}
	
	/**
	 * 
	 * Este metodo permite realizar el efecto
	 * de la expulsión, donde se enviará un mensaje 
	 * global al servidor de que se ha expulsado a 
	 * ese jugador.
	 * 
	 * @param player El jugador al que se le reproducirá
	 * el efecto.
	 */
	public static void applyKickEffect(Player moderator, String player) {
		
		String moderatorName = RankBase.getRank(moderator).getCustomName(moderator);
		
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(TextUtil
				.getCenteredMessage(" &c¡El jugador &4&l" + player + "&c fue expulsado por " + moderatorName + "!"));
		Bukkit.broadcastMessage(TextUtil
				.getCenteredMessage("&7Este jugador fue expulsado por no cumplir con las normas o"));
		Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&7politicas de Omniblock Network."));
		Bukkit.broadcastMessage("");
		
	}

}
