package com.javiluli.copperbeacon.util;

import com.javiluli.copperbeacon.CopperBeacon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase de utilidad para centralizar el registro de eventos y errores del mod.
 */
public class ModLogger {
	// Usamos el ID del mod como nombre del Logger para que sea fácil filtrar en la consola
	private static final Logger LOGGER = LoggerFactory.getLogger(CopperBeacon.MODID);

	public static void info(String message) {
		LOGGER.info(message);
	}

	public static void warn(String message) {
		LOGGER.warn(message);
	}

	public static void error(String message, Throwable throwable) {
		LOGGER.error(message, throwable);
	}

	// Util para desarrollo, se puede desactivar en producción
	public static void debug(String message) {
		LOGGER.debug(message);
	}
}