package net.omniblock.modtools.api;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import net.omniblock.modtools.ModTools;
import net.omniblock.modtools.Tool;
import net.omniblock.modtools.commands.InvSeeCommand;

/**
 * 
 * Esta API es utilizada para
 * manejar todo lo relacionado
 * al espionaje del inventario.
 * 
 * @author zlToxicNetherlz
 *
 */
public class SpigotInvSeeAPI extends Tool {

	@Override
	public void install() {
		
		CommandExecutor cmd = new InvSeeCommand();
		
		ModTools.getInstance().getCommand("invsee").setExecutor(cmd);
		ModTools.getInstance().getCommand("verinv").setExecutor(cmd);
		ModTools.getInstance().getCommand("verinventario").setExecutor(cmd);
		
	}
	
	/**
	 * 
	 * Este metodo simplemente le abrirá el inventario de un
	 * jugador al moderador solicitante.
	 * 
	 * @param player El jugador al que se le espiará el
	 * inventario.
	 */
	public static void spyInventory(Player moderator, Player user) {
		
		moderator.openInventory(user.getInventory());
		return;
		
	}

}
