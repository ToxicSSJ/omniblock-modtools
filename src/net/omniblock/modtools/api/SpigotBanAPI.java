package net.omniblock.modtools.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.omniblock.modtools.ModTools;
import net.omniblock.modtools.Tool;
import net.omniblock.modtools.commands.BanCommand;
import net.omniblock.modtools.listener.BanListener;
import net.omniblock.network.handlers.base.bases.type.BanBase;
import net.omniblock.network.handlers.base.sql.type.TableType;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.EventPatcher;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.data.PacketSocketData;
import net.omniblock.packets.network.structure.data.PacketStructure;
import net.omniblock.packets.network.structure.data.PacketStructure.DataType;
import net.omniblock.packets.network.structure.packet.ResposePlayerBanPacket;
import net.omniblock.packets.network.tool.object.PacketReader;

/**
 * 
 * Esta API es utilizada para
 * manejar todo lo relacionado
 * con los baneos.
 * 
 * @author zlToxicNetherlz
 *
 */
public class SpigotBanAPI extends Tool {
	
	/**
	 * 
	 * Mapa principal que maneja el jugador baneado y los
	 * detalles del baneo, para reutilizarse y evitar
	 * hacer llamados constantes a la base de datos.
	 * 
	 * Pronto se cambiará.
	 * 
	 */
	public static final Map<String, BanDetails> DUEDATE = new HashMap<String, BanDetails>();
	
	@Override
	public void install() {
		
		CommandExecutor cmd = new BanCommand();
		Listener listener = new BanListener();
		
		ModTools.getInstance().getCommand("ban").setExecutor(cmd);
		ModTools.getInstance().getCommand("banear").setExecutor(cmd);
		
		ModTools.getInstance().getServer().getPluginManager().registerEvents(listener, ModTools.getInstance());
		
		/*
		 * 
		 * Este reader es el encargado de expulsar a un usuario con el sistema
		 * de baneo incluyendo el mensaje y efecto de baneo generado por el
		 * sistema.
		 * 
		 */
		Packets.READER.registerReader(new PacketReader<ResposePlayerBanPacket>() {

			@Override
			public void readPacket(PacketSocketData<ResposePlayerBanPacket> packetsocketdata) {
				
				PacketStructure data = packetsocketdata.getStructure();

				String name = data.get(DataType.STRINGS, "playername");
				Player player = Bukkit.getPlayer(name);

				if(player == null)
					return;
				
				applyBanEffect(player);
				
			}

			@Override
			public Class<ResposePlayerBanPacket> getAttachedPacketClass() {
				return ResposePlayerBanPacket.class;
			}

		});
		
	}
	
	/**
	 * 
	 * Este metodo permite realizar el efecto
	 * del baneo, donde caerán rayos al jugador
	 * y se enviará un mensaje global al servidor
	 * de que se ha baneado ese jugador.
	 * 
	 * @param player El jugador al que se le reproducirá
	 * el efecto.
	 */
	public static void applyBanEffect(Player player) {
		
		if(player.isOnline()) {

			player.getLocation().getWorld().strikeLightningEffect(player.getLocation());
			player.getLocation().getWorld().strikeLightningEffect(player.getLocation());
			player.getLocation().getWorld().strikeLightningEffect(player.getLocation());

			if(checkPlayerBan(player)) {

				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage(TextUtil
						.getCenteredMessage("&7¡El jugador &4&l" + player.getName() + "&7 fue baneado!"));
				Bukkit.broadcastMessage(TextUtil
						.getCenteredMessage("&7Este jugador fue baneado por no cumplir con las normas o"));
				Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&7politicas de Omniblock Network."));
				Bukkit.broadcastMessage("");

				Bukkit.getPlayer(player.getName()).kickPlayer(DUEDATE.containsKey(player.getName())
						? new String(EventPatcher.YOURE_BANNED)
								.replaceFirst("VAR_BAN_HASH", DUEDATE.get(player.getName()).getHash())
								.replaceFirst("VAR_TO_DATE", DUEDATE.get(player.getName()).getTo())
								.replaceFirst("VAR_REASON", DUEDATE.get(player.getName()).getReason())
						: EventPatcher.YOURE_BANNED_WITHOUT_VARS);

				return;

			} else {

				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage(TextUtil
						.getCenteredMessage("&7¡El jugador &4&l" + player.getName() + "&7 fue baneado!"));
				Bukkit.broadcastMessage(TextUtil
						.getCenteredMessage("&7Este jugador fue baneado por no cumplir con las normas o"));
				Bukkit.broadcastMessage(TextUtil.getCenteredMessage("&7politicas de Omniblock Network."));
				Bukkit.broadcastMessage("");

				Bukkit.getPlayer(player.getName()).kickPlayer(EventPatcher.YOURE_BANNED_WITHOUT_VARS);

				return;

			}
		}
		
	}
	
	/**
	 * 
	 * Este metodo permite saber si el usuario en cuestión
	 * fue baneado o no.
	 * 
	 * @param player El usuario a comprobar.
	 * @return true si fue baneado.
	 */
	public static boolean checkPlayerBan(Player player) {

		if(!BanBase.isBanned(player))
			return false;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		List<String[]> registries = BanBase.getBanRegistry(player.getName());
		
		if(registries.size() <= 0)
			return false;

		for(String[] registry : registries) {

			if (!(registry.length >= 5))
				continue;

			String from = registry[3];
			String to = registry[4];

			Date fromdate = null;
			Date todate = null;

			try {
				
				fromdate = format.parse(from);
				todate = format.parse(to);
				
			} catch (ParseException e) {
				e.printStackTrace();
				continue;
			}

			if(fromdate != null && todate != null) {

				Date now = new Date();

				if(now.after(fromdate) && now.before(todate)) {

					DUEDATE.put(player.getName(), new BanDetails(registry[0], registry[4], registry[2]));
					return true;

				}

			}

			continue;

		}

		BanBase.setBanStatus(player, false);
		return false;

	}
	
	/**
	 * 
	 * Este metodo permite recibir el SQL que se debe ejecutar
	 * para definir el baneo en la base de datos.
	 * 
	 * @param data Los datos a rellenar.
	 * @return El SQL.
	 */
	public static String getInsertSQL(String[] data) {

		if (data.length >= 6) {

			String SQL = TableType.BAN_REGISTRY.getInserter().getInserterSQL();

			if (SQL.contains("VAR_BAN_HASH")) {
				SQL = SQL.replaceAll("VAR_BAN_HASH", "'" + data[0] + "'");
			}
			if (SQL.contains("VAR_MOD")) {
				SQL = SQL.replaceAll("VAR_MOD", "'" + data[1] + "'");
			}
			if (SQL.contains("VAR_BANNED")) {
				SQL = SQL.replaceAll("VAR_BANNED", "'" + data[2] + "'");
			}
			if (SQL.contains("VAR_REASON")) {
				SQL = SQL.replaceAll("VAR_REASON", "'" + data[3] + "'");
			}
			if (SQL.contains("VAR_BAN_TIME_FROM")) {
				SQL = SQL.replaceAll("VAR_BAN_TIME_FROM", "'" + data[4] + "'");
			}
			if (SQL.contains("VAR_BAN_TIME_TO")) {
				SQL = SQL.replaceAll("VAR_BAN_TIME_TO", "'" + data[5] + "'");
			}

			return SQL;

		}

		return null;
	}
	
	/**
	 * 
	 * Clase para manejar los datos
	 * de un baneo.
	 * 
	 * @author zlToxicNetherlz
	 *
	 */
	public static class BanDetails {

		private String hash;

		private String to;
		private String reason;

		/**
		 * 
		 * Constructor.
		 * 
		 * @param hash El codigo del baneo.
		 * @param to A quien fue dirigido el baneo.
		 * @param reason La razón del baneo.
		 */
		public BanDetails(String hash, String to, String reason) {

			this.hash = hash;
			this.to = to;
			this.reason = reason;

		}

		public String getHash() {
			return hash;
		}

		public String getTo() {
			return to;
		}

		public String getReason() {
			return reason;
		}

	}
	
}
