package net.omniblock.modtools.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.omniblock.modtools.api.SpigotSensorAPI;
import net.omniblock.network.handlers.network.NetworkManager;

public class SensorCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player))
			return false;
		
		if(!(sender.hasPermission("omniblock.network.moderator.sensor"))) {
			sender.sendMessage(NetworkManager.NOT_RECOGNIZED_COMMAND);
			return false;
		}

		if(cmd.getName().equalsIgnoreCase("sensor")) {
			
			Player player = (Player) sender;
			SpigotSensorAPI.toggleSensor(player);
			return true;
			
		}
		
		return false;
		
	}
	
}
