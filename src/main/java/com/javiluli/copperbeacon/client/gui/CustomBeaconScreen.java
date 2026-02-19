package com.javiluli.copperbeacon.client.gui;

import java.lang.reflect.Field;

import com.javiluli.copperbeacon.CopperBeacon;
import com.javiluli.copperbeacon.util.ModLogger;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * Pantalla personalizada para el Faro (Beacon) que expande el ancho original
 * para dar cabida a nuevos ingredientes (Cobre) y ajusta la posicion de los
 * elementos
 */
public class CustomBeaconScreen extends BeaconScreen {
    
    /**
     * TEXTURAS
     */
	private static final Identifier CUSTOM_GUI_TEXTURE = Identifier.fromNamespaceAndPath(CopperBeacon.MODID, "textures/gui/container/beacon.png");

	/**
	 * CONFIGURACION DE DIMENSIONES
	 */
	private static final int GUI_WIDTH = 230;
	private static final int GUI_HEIGHT = 219;

	/**
	 * CONFIGURACION DEL SLOT DE "PAGO"
	 */
	private static final int PAYMENT_SLOT_INDEX = 0;
	private static final int PAYMENT_SLOT_X_POSITION = 142;

	/**
	 * CONFIGURACION DE ICONOS
	 */
	private static final int ICONS_BASE_X = 42;
	private static final int ICONS_BASE_Y = 109;
	private static final int ICONS_OFFSET_X = -31;
	private static final int ICONS_SPACING = 22;

    public CustomBeaconScreen(BeaconMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = GUI_WIDTH;
        this.imageHeight = GUI_HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
        adjustPaymentSlotLogic();
    }

	/**
	 * Utiliza reflexion para mover la posicion logica del slot de entrada de items.
	 * Esto asegura que el area donde se hace clic coincida con el dibujo de la
	 * interfaz
	 */
	private void adjustPaymentSlotLogic() {
		try {
			Slot paymentSlot = this.menu.getSlot(PAYMENT_SLOT_INDEX);
			// "x" es el campo interno de la clase Slot de Minecraft
			Field xField = Slot.class.getDeclaredField("x");
			xField.setAccessible(true);
			xField.setInt(paymentSlot, PAYMENT_SLOT_X_POSITION);
		} catch (Exception e) {
			ModLogger.error("Fallo critico al posicionar el slot del Beacon mediante reflexion", e);
		}
	}

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // Calculo de las coordenadas centrales de la pantalla
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;

        // Renderizar el fondo de la interfaz del beacon
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CUSTOM_GUI_TEXTURE, relX, relY, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        // Renderizar los iconos de los ingredientes
        renderIngredient(guiGraphics, relX, relY);
    }

	/**
	 * Dibuja los items representativos en la interfaz
	 * 
	 * @param guiGraphics Herramienta de dibujo de Minecraft
	 * @param x           Coordenada X base de la GUI
	 * @param y           Coordenada Y base de la GUI
	 */
    private void renderIngredient(GuiGraphics guiGraphics, int x, int y) {
        int finalX = x + ICONS_BASE_X + ICONS_OFFSET_X;
        int finalY = y + ICONS_BASE_Y;

        // Dibujamos cada item en su posicion calculada
        // Se han mantenido los microajustes de -1 para centrado visual exacto
		guiGraphics.renderFakeItem(new ItemStack(Items.NETHERITE_INGOT), 	finalX, 							finalY);
		guiGraphics.renderFakeItem(new ItemStack(Items.EMERALD), 			finalX + (1 * ICONS_SPACING) - 1, 	finalY);
		guiGraphics.renderFakeItem(new ItemStack(Items.DIAMOND), 			finalX + (2 * ICONS_SPACING) - 1, 	finalY);
		guiGraphics.renderFakeItem(new ItemStack(Items.GOLD_INGOT), 		finalX + (3 * ICONS_SPACING), 		finalY);
		guiGraphics.renderFakeItem(new ItemStack(Items.IRON_INGOT), 		finalX + (4 * ICONS_SPACING), 		finalY);
		guiGraphics.renderFakeItem(new ItemStack(Items.COPPER_INGOT), 		finalX + (5 * ICONS_SPACING), 		finalY);
    }
}