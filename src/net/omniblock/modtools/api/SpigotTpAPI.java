package net.omniblock.modtools.api;

import org.bukkit.command.CommandExecutor;

import net.omniblock.modtools.ModTools;
import net.omniblock.modtools.Tool;
import net.omniblock.modtools.commands.TpCommand;

/**
 * 
 * Esta API es utilizada para
 * manejar todo lo relacionado
 * con los baneos.
 * 
 * @author zlToxicNetherlz
 *
 */
public class SpigotTpAPI extends Tool {
	
	@Override
	public void install() {
		
		CommandExecutor cmd = new TpCommand();
		
		ModTools.getInstance().getCommand("tp").setExecutor(cmd);
		
	}
	
}
