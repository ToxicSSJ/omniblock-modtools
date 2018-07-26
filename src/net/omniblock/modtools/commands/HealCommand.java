package net.omniblock.modtools.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.omniblock.network.handlers.network.NetworkManager;
import net.omniblock.network.library.utils.TextUtil;

public class HealCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player))
			return false;
		
		if(!(sender.hasPermission("omniblock.network.moderator.heal"))) {
			sender.sendMessage(NetworkManager.NOT_RECOGNIZED_COMMAND);
			return false;
		}

		if(cmd.getName().equalsIgnoreCase("heal") || cmd.getName().equalsIgnoreCase("health")) {
			
			Player player = (Player) sender;
			
			player.setHealth(player.getMaxHealth());
			player.setFoodLevel(20);
			
			player.sendMessage(TextUtil.format("&aHas sido sanado!"));
			return true;
			
		}
		
		return false;
		
	}
	
}
