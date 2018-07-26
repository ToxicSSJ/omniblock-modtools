package net.omniblock.modtools.api;

import org.bukkit.command.CommandExecutor;

import net.omniblock.modtools.ModTools;
import net.omniblock.modtools.Tool;
import net.omniblock.modtools.commands.HealCommand;

/**
 * 
 * Esta API es utilizada para
 * manejar todo lo relacionado
 * con la sanación.
 * 
 * @author zlToxicNetherlz
 *
 */
public class SpigotHealAPI extends Tool {
	
	@Override
	public void install() {
		
		CommandExecutor cmd = new HealCommand();
		
		ModTools.getInstance().getCommand("heal").setExecutor(cmd);
		ModTools.getInstance().getCommand("health").setExecutor(cmd);
		
	}
	
}
