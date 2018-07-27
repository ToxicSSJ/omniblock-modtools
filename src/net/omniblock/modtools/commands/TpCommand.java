package net.omniblock.modtools.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.omniblock.network.handlers.network.NetworkManager;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.CommandPatcher;

/**
 * 
 * Esta clase se encarga de manejar la
 * ejecución del comando kickear.
 * 
 * @author zlToxicNetherlz
 *
 */
public class TpCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player))
			return false;
		
		if(!(sender.hasPermission("omniblock.network.moderator.tp"))) {
			sender.sendMessage(NetworkManager.NOT_RECOGNIZED_COMMAND);
			return false;
		}

		if(cmd.getName().equalsIgnoreCase("tp")) {

			if(args.length >= 1) {

				Player player = Bukkit.getPlayer(args[0]);

				if(player == null) {
					
					sender.sendMessage(TextUtil.format("&cEl jugador &7" + args[0] + " &cno está online."));
					return true;
					
				}

				if(player.equals((Player) sender)) {
					
					sender.sendMessage(TextUtil.format("&cNo te puedes teletransportar a tu misma posición."));
					return true;
					
				}

				((Player) sender).teleport(player);
				sender.sendMessage(TextUtil.format("&aTe has teletransportado a &7" + player.getName() + " &acorrectamente."));
				return true;

			}
			
			sender.sendMessage(" ");
			sender.sendMessage(CommandPatcher.BAR);
			sender.sendMessage(TextUtil.getCenteredMessage(" &8&lU&8tilidades &b&l» &7Te ha faltado un argumento!"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &7Rercuerda que todos los datos deben estár puestos!"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &7El formato actual de teletransportes es el siguiente:"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &bFormato:  &e/tp [jugador]"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &bEjemplo:  &7/tp KamiKaze"));
			sender.sendMessage(CommandPatcher.BAR);
			sender.sendMessage(" ");
			return true;
			
		}
		
		return false;
		
	}
	
}
