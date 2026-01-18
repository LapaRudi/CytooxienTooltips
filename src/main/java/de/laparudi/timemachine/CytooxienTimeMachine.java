package de.laparudi.timemachine;

import de.laparudi.timemachine.listener.ItemTooltipListener;
import de.laparudi.timemachine.listener.JoinListener;
import de.laparudi.timemachine.listener.KeyBindListener;
import net.fabricmc.api.ClientModInitializer;

public class CytooxienTimeMachine implements ClientModInitializer {
	
	public static boolean CXN = false;
	public static boolean DEBUG = false;
	
	@Override
	public void onInitializeClient() {
		ItemTooltipListener.register();
		JoinListener.register();
		KeyBindListener.register();
	}
	
	public static void toggleDebug() {
		DEBUG = !DEBUG;
	}
}