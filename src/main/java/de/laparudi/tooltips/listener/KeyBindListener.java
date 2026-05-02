package de.laparudi.tooltips.listener;

import com.mojang.blaze3d.platform.InputConstants;
import de.laparudi.tooltips.CytooxienTooltips;
import de.laparudi.tooltips.Language;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;

public class KeyBindListener {
    
    private static KeyMapping debugToggleKey;

    public static void register() {
        debugToggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping("CXN-Tooltips Debug",  InputConstants.UNKNOWN.getValue(), KeyMapping.Category.MISC));
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (debugToggleKey.consumeClick()) {
                CytooxienTooltips.toggleDebug();
                
                client.player.sendOverlayMessage(Component.literal("[CXN Tooltips] " + Language.get("debug.display") + ": ").append(CytooxienTooltips.debug
                        ? Component.literal(Language.get("debug.on")).withColor(0xFF00FF00)
                        : Component.literal(Language.get("debug.off")).withColor(0xFFFF0000)));
            }
        });
    }
}
