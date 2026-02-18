package com.javiluli.copperbeacon;

import com.javiluli.copperbeacon.client.gui.CustomBeaconScreen;
import com.javiluli.copperbeacon.util.ModLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.event.ScreenEvent;

/**
 * Clase principal del mod Copper Beacon.
 * Se encarga de la inicialización y de interceptar la interfaz original del faro.
 */
@Mod(CopperBeacon.MODID)
public class CopperBeacon {
    
    /**
     * CONSTANTES
     */
    public static final String MODID = "copperbeacon";

    /**
     * Constructor del mod. 
     * @param modEventBus Bus de eventos específico del mod (registros, setup).
     * @param dist Indica si estamos en cliente o servidor fisico.
     */
    public CopperBeacon(IEventBus modEventBus, Dist dist) {
    	// Registro de logs inicial
        ModLogger.info("Copper Beacon cargando...");
        
        // Solo registramos eventos de UI si estamos en el cliente fisico
        if (dist.isClient()) {
        	NeoForge.EVENT_BUS.register(new ClientHandler());
            ModLogger.info("Inicializando Copper Beacon en modo cliente...");
        }
    }

    /**
     * Clase estatica para aislar eventos que dependen de bibliotecas graficas (Client-Only).
     */
    private static class ClientHandler {
        @SubscribeEvent
        public void onScreenOpening(ScreenEvent.Opening event) {
            // Interceptamos la pantalla original del Beacon
            if (event.getScreen() instanceof BeaconScreen original && !(original instanceof CustomBeaconScreen)) {
                
                @SuppressWarnings("resource")
				CustomBeaconScreen customScreen = new CustomBeaconScreen(
                    original.getMenu(), 
                    Minecraft.getInstance().player.getInventory(), 
                    original.getTitle()
                );

                event.setNewScreen(customScreen);
                ModLogger.debug("GUI de Beacon interceptada: Aplicando version personalizada");
            }
        }
    }
}