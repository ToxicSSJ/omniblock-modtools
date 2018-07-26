package net.omniblock.modtools;

import org.bukkit.plugin.java.JavaPlugin;

import net.omniblock.network.handlers.Handlers;

public class ModToolsCord extends JavaPlugin {

	/**
	 *
	 * Esta es la instancia de la clase
	 * ModTools, por ende, para acceder
	 * a esta instancia se debe hacer uso
	 * del método estático {@link #getInstance()}
	 *
	 *
	 */
	protected static ModToolsCord instance;

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

	}

	/**
	 * 
	 * @return La instancia del ModTools
	 */
	public static ModToolsCord getInstance(){
		return instance;
	}
	
}
