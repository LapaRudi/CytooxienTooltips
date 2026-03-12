package de.laparudi.tooltips.registry;

import de.laparudi.tooltips.util.LoreUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public enum Event {

    NEW_YEAR("Neujahr", 0xFF1E90FF, 0xFFFF7100),
    VALENTINES_DAY("Valentinstag", 0xFFFF007B, 0xFFFF23C8),
    EASTER("Ostern", 0xFFC89F7F, 0xFF23C623),
    SUMMER("Sommer", 0xFF32C6B7, 0xFF00BFFF),
    ANNIVERSARY("Jubiläum", 0xFFC69B00, 0xFF95A59B),
    HALLOWEEN("Halloween", 0xFFFF6600, 0xFFA569BD),
    CHRISTMAS("Weihnachten", 0xFFDC143C, 0xFF3CB371),
    CHRISTMAS_ROD("Weihnachtsangel", 0xFFDC143C, 0xFF3CB371),
    COLLECTION("Collection", 0xFFB080D0, 0);
    
    private final String name;
    private final int color;
    private final int secondColor;
    
    Event(final String name, final int color, final int secondColor) {
        this.name = name;
        this.color = color;
        this.secondColor = secondColor;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public int getSecondColor() {
        return secondColor;
    }
    
    public Component getText() {
        return LoreUtils.formatCustomTag(this.getName(), false).withColor(this.getColor());
    }

    public Component getText(final int year) {
        MutableComponent nameTag = LoreUtils.formatCustomTag(this.getName(), false).withColor(this.getColor());
        MutableComponent yearTag = LoreUtils.formatCustomTag(String.valueOf(year), true).withColor(this.getSecondColor());

        return nameTag.append(Component.literal(" ")).append(yearTag);
    }
}
