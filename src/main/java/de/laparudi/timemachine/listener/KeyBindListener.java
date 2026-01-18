package de.laparudi.timemachine.listener;

import com.mojang.blaze3d.platform.InputConstants;
import de.laparudi.timemachine.CytooxienTimeMachine;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;

public class KeyBindListener {

    private static KeyMapping debugToggleKey;

    public static void register() {
        debugToggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping("CXN Zeitmaschine Debug",  InputConstants.UNKNOWN.getValue(), KeyMapping.Category.MISC));
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (debugToggleKey.consumeClick()) {
                CytooxienTimeMachine.toggleDebug();
                
                client.player.displayClientMessage(Component.literal("[CXN Zeitmaschine] Debug-Modus: ").append(CytooxienTimeMachine.DEBUG
                        ? Component.literal("AN").withColor(0xFF00FF00)
                        : Component.literal("AUS").withColor(0xFFFF0000)), true);
            }
        });
    }
}
