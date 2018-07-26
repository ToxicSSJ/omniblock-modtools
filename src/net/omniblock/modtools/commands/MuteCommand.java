package net.omniblock.modtools.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.omniblock.modtools.api.SpigotMuteAPI;
import net.omniblock.network.handlers.base.bases.type.RankBase;
import net.omniblock.network.handlers.network.NetworkManager;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.CommandPatcher;
import net.omniblock.network.systems.rank.type.RankType;

/**
 * 
 * Esta clase se encarga de manejar la
 * ejecución del comando silenciar.
 * 
 * @author zlToxicNetherlz
 *
 */
public class MuteCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player))
			return false;
		
		if(!(sender.hasPermission("omniblock.network.moderator.mute"))) {
			sender.sendMessage(NetworkManager.NOT_RECOGNIZED_COMMAND);
			return false;
		}

		if(cmd.getName().equalsIgnoreCase("mute") || cmd.getName().equalsIgnoreCase("silenciar")) {

			if(args.length >= 2) {
				
				Player moderator = (Player) sender;
				
				Player player = Bukkit.getPlayer(args[0]);
				RankType rank = RankBase.getRank(player);
				
				int time = SpigotMuteAPI.getTimeByFormat(args[1]);
				
				boolean hasmute = SpigotMuteAPI.hasMute(player);
				long oldtime = SpigotMuteAPI.getTime(player);
				
				if(time == -1) {
					
					sender.sendMessage(TextUtil.format("&cEl formato &7" + args[1] + " &ces invalido."));
					return true;
					
				}
				
				if(player == null) {
					
					sender.sendMessage(TextUtil.format("&cEl jugador &7" + args[0] + " &cno está online."));
					return true;
					
				}
				
				if(rank.isStaff()) {
					
					sender.sendMessage(TextUtil.format("&cEl jugador &7" + args[0] + " &ces del equipo Staff."));
					return true;
					
				}
				
				if(SpigotMuteAPI.mutePlayer(player, time)) {
					
					Bukkit.broadcastMessage(TextUtil.format("&cEl jugador &7" + player.getName() + " &cfue silenciado/a por " + moderator.getCustomName() + "&c, debido a que incumplió las normas de Omniblock Network!"));
					
					if(hasmute) {
						
						player.sendMessage("");
						player.sendMessage(TextUtil.getCenteredMessage("&c¡Se ha redifinido tu silencio!"));
						player.sendMessage("");
						player.sendMessage(TextUtil.getCenteredMessage(" &cModerador: &7" + sender.getName()));
						player.sendMessage(TextUtil.getCenteredMessage(" &cTiempo: &7" + time + " segundos."));
						player.sendMessage("");
						player.sendMessage(TextUtil.getCenteredMessage("&7Tu silencio se debe a que no has cumplido con las normas o"));
						player.sendMessage(TextUtil.getCenteredMessage("&7politicas de Omniblock Network."));
						player.sendMessage("");
						
						sender.sendMessage(" ");
						sender.sendMessage(CommandPatcher.BAR);
						sender.sendMessage(TextUtil.getCenteredMessage(" &a¡Haz redefinido el muteo de &7" + args[0] + "&a correctamente!"));
						sender.sendMessage(TextUtil.getCenteredMessage(""));
						sender.sendMessage(TextUtil.getCenteredMessage(" &cJugador: &7" + player.getName()));
						sender.sendMessage(TextUtil.getCenteredMessage(" &cAntes: &7" + oldtime + " segundos."));
						sender.sendMessage(TextUtil.getCenteredMessage(" &cAhora: &7" + SpigotMuteAPI.getTime(player) + " segundos."));
						sender.sendMessage(TextUtil.getCenteredMessage(""));
						sender.sendMessage(CommandPatcher.BAR);
						sender.sendMessage(" ");
						
						return true;
						
					}
					
					player.sendMessage("");
					player.sendMessage(TextUtil.getCenteredMessage("&c¡Has sido silenciado!"));
					player.sendMessage("");
					player.sendMessage(TextUtil.getCenteredMessage(" &cModerador: &7" + sender.getName()));
					player.sendMessage(TextUtil.getCenteredMessage(" &cTiempo: &7" + time + " segundos."));
					player.sendMessage("");
					player.sendMessage(TextUtil.getCenteredMessage("&7Tu silencio se debe a que no has cumplido con las normas o"));
					player.sendMessage(TextUtil.getCenteredMessage("&7politicas de Omniblock Network."));
					player.sendMessage("");
					
					sender.sendMessage(" ");
					sender.sendMessage(CommandPatcher.BAR);
					sender.sendMessage(TextUtil.getCenteredMessage(" &a¡Haz definido el muteo de &7" + args[0] + "&a correctamente!"));
					sender.sendMessage(TextUtil.getCenteredMessage(""));
					sender.sendMessage(TextUtil.getCenteredMessage(" &cJugador: &7" + player.getName()));
					sender.sendMessage(TextUtil.getCenteredMessage(" &cTiempo: &7" + SpigotMuteAPI.getTime(player) + " segundos."));
					sender.sendMessage(TextUtil.getCenteredMessage(""));
					sender.sendMessage(CommandPatcher.BAR);
					sender.sendMessage(" ");
					
					return true;
					
				} else {
					
					sender.sendMessage(" ");
					sender.sendMessage(CommandPatcher.BAR);
					sender.sendMessage(TextUtil.getCenteredMessage(" &8&lS&8ilencios &b&l» &7Has excedido el tiempo de 5 días!"));
					sender.sendMessage(TextUtil.getCenteredMessage(" &7El formato actual de silencios es el siguiente:"));
					sender.sendMessage(TextUtil.getCenteredMessage(" &bFormato:  &e/mute [jugador] [tiempo, s/m/h/d, max=5 días] [razón]"));
					sender.sendMessage(TextUtil.getCenteredMessage(" &bEjemplo:  &7/mute KamiKaze 15m Flood"));
					sender.sendMessage(CommandPatcher.BAR);
					sender.sendMessage(" ");
					return true;
					
				}

			}
			
			sender.sendMessage(" ");
			sender.sendMessage(CommandPatcher.BAR);
			sender.sendMessage(TextUtil.getCenteredMessage(" &8&lS&8ilencios &b&l» &7Te ha faltado un argumento!"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &7Recuerda que todos los datos deben estár puestos!"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &7El formato actual de silencios es el siguiente:"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &bFormato:  &e/mute [jugador] [tiempo, s/m/h/d, max=5 días] [razón]"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &bEjemplo:  &7/mute KamiKaze 15m Flood"));
			sender.sendMessage(CommandPatcher.BAR);
			sender.sendMessage(" ");
			return true;
			
		}
		
		if(cmd.getName().equalsIgnoreCase("unmute") || cmd.getName().equalsIgnoreCase("dessilenciar")) {
			
			if(args.length >= 1) {
				
				Player player = Bukkit.getPlayer(args[0]);
				
				if(SpigotMuteAPI.hasMute(player)) {
					
					SpigotMuteAPI.clearPlayer(player);
					sender.sendMessage(TextUtil.format("&a¡Se ha desmuteado a &7" + player.getCustomName() + "&a correctamente!"));
					return true;
					
				}
				
				sender.sendMessage(TextUtil.format("&c¡El jugador &7" + player.getCustomName() + "&c no está muteado!"));
				return true;
					
				
			}
			
			sender.sendMessage(" ");
			sender.sendMessage(CommandPatcher.BAR);
			sender.sendMessage(TextUtil.getCenteredMessage(" &8&lS&8ilencios &b&l» &7Te ha faltado un argumento!"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &7Recuerda que todos los datos deben estár puestos!"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &7El formato actual es el siguiente:"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &bFormato:  &e/unmute [jugador]"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &bEjemplo:  &7/unmute KamiKaze"));
			sender.sendMessage(CommandPatcher.BAR);
			sender.sendMessage(" ");
			return true;
			
		}
		
		return false;
		
	}
	
}
