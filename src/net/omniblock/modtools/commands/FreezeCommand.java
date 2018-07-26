package net.omniblock.modtools.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.omniblock.modtools.api.SpigotFreezeAPI;
import net.omniblock.network.handlers.base.bases.type.RankBase;
import net.omniblock.network.handlers.network.NetworkManager;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.CommandPatcher;
import net.omniblock.network.systems.rank.type.RankType;

/**
 * 
 * Esta clase se encarga de manejar la
 * ejecución del comando freeze.
 * 
 * @author zlToxicNetherlz
 *
 */
public class FreezeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player))
			return false;
		
		if(!(sender.hasPermission("omniblock.network.moderator.freeze"))) {
			sender.sendMessage(NetworkManager.NOT_RECOGNIZED_COMMAND);
			return false;
		}

		if(cmd.getName().equalsIgnoreCase("freeze") || cmd.getName().equalsIgnoreCase("ss")) {

			if(args.length >= 1) {
				
				Player moderator = (Player) sender;
				
				Player player = Bukkit.getPlayer(args[0]);
				RankType rank = RankBase.getRank(player);
				
				if(player == null) {
					
					sender.sendMessage(TextUtil.format("&cEl jugador &7" + args[0] + " &cno está online."));
					return true;
					
				}
				
				if(rank.isStaff()) {
					
					sender.sendMessage(TextUtil.format("&cEl jugador &7" + args[0] + " &ces del equipo Staff."));
					return true;
					
				}
				
				if(player.equals((Player) sender)) {
					
					sender.sendMessage(TextUtil.format("&cNo te puedes congelar a ti mismo/a."));
					return true;
					
				}
				
				SpigotFreezeAPI.toggleFrozen(moderator, player);
				return true;
				
			}
			
			sender.sendMessage(" ");
			sender.sendMessage(CommandPatcher.BAR);
			sender.sendMessage(TextUtil.getCenteredMessage(" &8&lC&8ongelamientos &b&l» &7Te ha faltado un argumento!"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &7Recuerda que todos los datos deben estár puestos!"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &7El formato actual de congelamientos es el siguiente:"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &bFormato:  &e/freeze [jugador]"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &bEjemplo:  &7/freeze KamiKaze"));
			sender.sendMessage(CommandPatcher.BAR);
			sender.sendMessage(" ");
			return true;
			
		}
		
		return false;
		
	}
	
}
