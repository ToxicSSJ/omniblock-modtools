package net.omniblock.modtools.commands;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.modtools.api.SpigotBanAPI;
import net.omniblock.network.OmniNetwork;
import net.omniblock.network.handlers.base.bases.type.BanBase;
import net.omniblock.network.handlers.base.bases.type.RankBase;
import net.omniblock.network.handlers.base.sql.util.Resolver;
import net.omniblock.network.handlers.network.NetworkManager;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.CommandPatcher;
import net.omniblock.network.systems.rank.type.RankType;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.packet.PlayerSendBanPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;

/**
 * 
 * Esta clase se encarga de manejar la
 * ejecución del comando banear.
 * 
 * @author zlToxicNetherlz
 *
 */
public class BanCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player))
			return false;
		
		if(!(sender.hasPermission("omniblock.network.moderator.ban"))) {
			sender.sendMessage(NetworkManager.NOT_RECOGNIZED_COMMAND);
			return false;
		}

		if(cmd.getName().equalsIgnoreCase("ban") || cmd.getName().equalsIgnoreCase("banear")) {

			if (args.length >= 3) {

				String player = args[0];
				String days = args[1];

				String reason = args[2];

				if (args.length >= 4) {

					reason = "";
					StringBuffer cache = new StringBuffer("");

					for (int i = 2; i >= (args.length - 1); i++) {

						cache.append(args[i] + " ");
						continue;

					}

					reason = cache.toString();

				}

				if (player.equals(sender.getName())) {
					sender.sendMessage(TextUtil.format("&cNo te puedes banear a ti mismo!"));
					return true;
				}

				if (!NumberUtils.isNumber(days)) {
					sender.sendMessage(TextUtil.format("&cEl formato de dias es incorrecto!"));
					return true;
				}

				UUID offlineUUID = Resolver.getOfflineUUIDByName(player);

				if (offlineUUID == null) {
					sender.sendMessage(
							TextUtil.format("&cEl jugador seleccionado nunca ha entrado a Omniblock Network!"));
					return true;
				}

				if (BanBase.isBanned(player)) {
					sender.sendMessage(TextUtil.format("&cEl jugador al que intentas banear ya fue baneado!"));
					return true;
				}

				RankType rank = RankBase.getRank(player);

				if(rank.isStaff()) {

					RankType senderrank = RankBase.getRank(sender.getName());

					if(!(senderrank == RankType.DIRECTOR || senderrank == RankType.ADMIN || senderrank == RankType.GM)) {

						sender.sendMessage(" ");
						sender.sendMessage(CommandPatcher.BAR);
						sender.sendMessage(TextUtil
								.getCenteredMessage(" &8§lB§8aneos &b&l» &7Se ha detectado que estás tratando"));
						sender.sendMessage(TextUtil.getCenteredMessage(" &7de banear a un miembro del equipo Staff!"));
						sender.sendMessage(TextUtil
								.getCenteredMessage(" &7Por consiguiente serás baneado con el codigo &b&lBPBS#1"));
						sender.sendMessage(TextUtil
								.getCenteredMessage(" &7que implica que cuando un administrador se entere de tu "));
						sender.sendMessage(TextUtil.getCenteredMessage(" &7acción, serás expulsado del equipo."));
						sender.sendMessage(CommandPatcher.BAR);
						sender.sendMessage(" ");

						Date date = new Date();
						String hash = UUID.randomUUID().toString().toString().substring(1, 10);

						String banned_id = Resolver.getNetworkIDByName(sender.getName());
						String mod_id = "CONSOLE";

						reason = "BPBS#1";

						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						calendar.add(Calendar.YEAR, 10);

						String from_str = format.format(date);
						String to_str = format.format(calendar.getTime());

						if (player == null || days == null || reason == null || offlineUUID == null || hash == null
								|| banned_id == null || mod_id == null) {
							
							sender.sendMessage(
									TextUtil.format("&cHa ocurrido un error mientras se procesaba el baneo."));
							return true;
							
						}

						String[] data = new String[] { hash, mod_id, banned_id, reason, from_str, to_str };
						String insert = SpigotBanAPI.getInsertSQL(data);

						if (insert == null) {
							
							sender.sendMessage(TextUtil
									.format("&cEl sistema de baneos se encuentra deshabilitado temporalmente..."));
							return true;
							
						}

						new BukkitRunnable() {
							@Override
							public void run() {

								Packets.STREAMER.streamPacket(new PlayerSendBanPacket().setPlayername(sender.getName())
										.build().setReceiver(PacketSenderType.OMNICORD));

								BanBase.setBanStatus(sender.getName(), true);
								BanBase.insertBanRegistry(insert);

							}
						}.runTaskLater(OmniNetwork.getInstance(), 20 * 3);

						return true;

					}

				}

				Date date = new Date();
				String hash = UUID.randomUUID().toString().toString().substring(1, 10);

				String banned_id = Resolver.getNetworkIDByName(player);
				String mod_id = Resolver.getNetworkIDByName(sender.getName());

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, Integer.valueOf(days));

				String from_str = format.format(date);
				String to_str = format.format(calendar.getTime());

				if (player == null || days == null || reason == null || offlineUUID == null || hash == null
						|| banned_id == null || mod_id == null) {
					sender.sendMessage(TextUtil.format("&cHa ocurrido un error mientras se procesaba el baneo."));
					return true;
				}

				String[] data = new String[] { hash, mod_id, banned_id, reason, from_str, to_str };
				String insert = SpigotBanAPI.getInsertSQL(data);

				if (insert == null) {
					sender.sendMessage(
							TextUtil.format("&cEl sistema de baneos se encuentra deshabilitado temporalmente..."));
					return true;
				}

				Packets.STREAMER.streamPacket(new PlayerSendBanPacket().setPlayername(player)
						.build().setReceiver(PacketSenderType.OMNICORD));

				BanBase.setBanStatus(player, true);
				BanBase.insertBanRegistry(insert);

				sender.sendMessage(" ");
				sender.sendMessage(CommandPatcher.BAR);
				sender.sendMessage(TextUtil.getCenteredMessage(" &a¡Has registrado un baneo correctamente!"));
				sender.sendMessage(TextUtil.getCenteredMessage(""));
				sender.sendMessage(TextUtil.getCenteredMessage(" &cHash: &7" + hash));
				sender.sendMessage(TextUtil.getCenteredMessage(" &cBaneado: &7" + player));
				sender.sendMessage(TextUtil.getCenteredMessage(" &cHasta: &7" + to_str));
				sender.sendMessage(TextUtil.getCenteredMessage(" &cRazón: &7" + reason));
				sender.sendMessage(TextUtil.getCenteredMessage(""));
				sender.sendMessage(CommandPatcher.BAR);
				sender.sendMessage(" ");
				return true;

			}

			sender.sendMessage(" ");
			sender.sendMessage(CommandPatcher.BAR);
			sender.sendMessage(TextUtil.getCenteredMessage(" &8§lB§8aneos &b&l» &7Te ha faltado un argumento!"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &7Rercuerda que todos los datos deben estár puestos!"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &7El formato actual de baneos es el siguiente: "));
			sender.sendMessage(TextUtil.getCenteredMessage(" &bFormato:  &e/banear [jugador] [dias] [prueba]"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &bEjemplo:  &7/banear KamiKaze 2 http://bit.ly/u3m4l2"));
			sender.sendMessage(CommandPatcher.BAR);
			sender.sendMessage(" ");
			return true;

		}

		return false;

	}

}
