package net.omniblock.modtools.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.omniblock.modtools.api.SpigotVanishAPI;
import net.omniblock.network.handlers.network.NetworkManager;

/**
 * 
 * Esta clase se encarga de manejar la
 * ejecución del comando kickear.
 * 
 * @author zlToxicNetherlz
 *
 */
public class VanishCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player))
			return false;
		
		if(!(sender.hasPermission("omniblock.network.moderator.vanish"))) {
			sender.sendMessage(NetworkManager.NOT_RECOGNIZED_COMMAND);
			return false;
		}

		if(cmd.getName().equalsIgnoreCase("vanish") || cmd.getName().equalsIgnoreCase("v")) {

			SpigotVanishAPI.toggleVanish((Player) sender, args);
			return true;
			
		}
		
		return false;
		
	}
	
}
