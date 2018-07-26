package net.omniblock.modtools;

import java.util.Arrays;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import net.omniblock.modtools.api.SpigotBanAPI;
import net.omniblock.modtools.api.SpigotFlyAPI;
import net.omniblock.modtools.api.SpigotFreezeAPI;
import net.omniblock.modtools.api.SpigotHealAPI;
import net.omniblock.modtools.api.SpigotInvSeeAPI;
import net.omniblock.modtools.api.SpigotKickAPI;
import net.omniblock.modtools.api.SpigotMuteAPI;
import net.omniblock.modtools.api.SpigotSensorAPI;
import net.omniblock.modtools.api.SpigotTpAPI;
import net.omniblock.modtools.api.SpigotVanishAPI;
import net.omniblock.network.handlers.Handlers;

public class ModTools extends JavaPlugin {

	/**
	 *
	 * Esta es la instancia de la clase
	 * ModTools, por ende, para acceder
	 * a esta instancia se debe hacer uso
	 * del método estático {@link #getInstance()}
	 *
	 *
	 */
	protected static ModTools instance;

	/**
	 * 
	 * Esta lista contiene las herramientas
	 * de moderación que serán inicializadas.
	 * 
	 */
	protected static final List<Tool> tools = Arrays.asList(
			
			new SpigotBanAPI(),
			new SpigotFlyAPI(),
			new SpigotInvSeeAPI(),
			new SpigotKickAPI(),
			new SpigotMuteAPI(),
			new SpigotTpAPI(),
			new SpigotVanishAPI(),
			new SpigotFreezeAPI(),
			new SpigotSensorAPI(),
			new SpigotHealAPI()
			
			);
	
	/**
	 *
	 * Metodo que carga todos los
	 * sistemas.
	 *
	 * Se inician cuando el servidor esta
	 * encendido o cuando se realice un
	 * reload.
	 *
	 */
	@Override
	public void onEnable(){

		instance = this;

		Handlers.LOGGER.sendModuleMessage("ModTools", "Inicializando sistema...");

		for(Tool tool : tools)
			tool.install();
		
	}

	/**
	 * 
	 * @return La instancia del ModTools
	 */
	public static ModTools getInstance(){
		return instance;
	}
	
}
