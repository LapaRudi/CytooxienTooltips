package de.laparudi.tooltips.util;

import it.unimi.dsi.fastutil.Pair;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;

import java.util.List;
import java.util.Map;

@Getter
public class Generator {

    private final CompoundTag tag;
    private final String type;
    private final long brokenBlocks;
    private final int maxLevel;
    private final int delayLevel;

    public Generator(final CompoundTag tag) {
        this.tag = tag;
        type = tag.getString("treasurechestitems:cobblegenerator_type").orElse("unknown");
        brokenBlocks = tag.getLong("treasurechestitems:cobblegenerator_count").orElse(0L);
        delayLevel = tag.getInt("treasurechestitems:cobblegenerator_baselevel").orElse(0);
        maxLevel = type.equals("nether") ? 50 : 100;
    }

    public double getPrice() {
        final CompoundTag levelsTag = tag.getCompound("treasurechestitems:cobblegenerator_levels").orElse(null);
        if (levelsTag == null) return 0.0;

        final List<Double> list = type.equals("default") ? GeneratorData.getCobbleDelay() : GeneratorData.getNetherDeepslateDelay();
        final double delayPrice = list.stream().limit(delayLevel).mapToDouble(Double::doubleValue).sum();

        final List<Pair<String, Integer>> levels = levelsTag.keySet().stream().map(key -> Pair.of(key, levelsTag.getIntOr(key, 0))).toList();
        return delayPrice + levels.stream().mapToDouble(pair -> getBlockPrice(pair.key(), pair.value())).sum();
    }

    private double getBlockPrice(final String block, final int blockLevel) {
        final Map<String, List<Double>> map = switch (type) {
            case "default" -> GeneratorData.getCobblePrices();
            case "deepslate" -> GeneratorData.getDeepslatePrices();
            case "nether" -> GeneratorData.getNetherPrices();
            default -> null;
        };

        if (map == null) return 0.0;
        return map.get(block).stream().limit(blockLevel).mapToDouble(Double::doubleValue).sum();
    }

    public int getLevel() {
        int currentLevel = 1;
        int cost = 14;
        long remaining = brokenBlocks;

        while (remaining >= cost) {
            remaining -= cost;
            currentLevel++;

            if (currentLevel >= maxLevel) {
                return maxLevel;
            }

            if (currentLevel < 17) {
                cost += 4;
            } else if (currentLevel < 32) {
                cost += 10;
            } else {
                cost += 18;
            }
        }

        return currentLevel;
    }

    public int getAvailableLevels() {
        final CompoundTag levelsTag = tag.getCompound("treasurechestitems:cobblegenerator_levels").orElse(null);
        if (levelsTag == null) return 0;

        final int usedLevels = levelsTag.entrySet().stream().mapToInt(entry -> entry.getValue().asInt().orElse(0)).sum();
        final int maxBrokenBlocks = type.equals("nether") ? 10690 : 60474;
        final int maxLevelXPModifier = brokenBlocks >= maxBrokenBlocks ? 0 : 1;
        return this.getLevel() - usedLevels - delayLevel - maxLevelXPModifier;
    }
}
