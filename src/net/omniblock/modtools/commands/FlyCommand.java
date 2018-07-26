package net.omniblock.modtools.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.omniblock.modtools.api.SpigotFlyAPI;
import net.omniblock.network.handlers.network.NetworkManager;

public class FlyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player))
			return false;
		
		if(!(sender.hasPermission("omniblock.network.moderator.fly"))) {
			sender.sendMessage(NetworkManager.NOT_RECOGNIZED_COMMAND);
			return false;
		}

		if(cmd.getName().equalsIgnoreCase("fly") || cmd.getName().equalsIgnoreCase("volar")) {
			
			Player player = (Player) sender;
			SpigotFlyAPI.toggleFly(player);
			return true;
			
		}
		
		return false;
		
	}
	
}
