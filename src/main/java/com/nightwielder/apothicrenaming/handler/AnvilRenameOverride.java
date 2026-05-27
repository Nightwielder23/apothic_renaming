package com.nightwielder.apothicrenaming.handler;

import com.nightwielder.apothicrenaming.ApothicRenaming;
import com.nightwielder.apothicrenaming.util.ApotheosisDetector;
import dev.shadowsoffire.apotheosis.Apoth;
import dev.shadowsoffire.apotheosis.affix.AffixHelper;
import dev.shadowsoffire.apotheosis.loot.LootRarity;
import dev.shadowsoffire.placebo.reload.DynamicHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

@EventBusSubscriber(modid = ApothicRenaming.MODID)
public final class AnvilRenameOverride {
    private AnvilRenameOverride() {}

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        if (!ApotheosisDetector.isApotheosisLoaded()) return;
        ItemStack left = event.getLeft();
        if (left.isEmpty()) return;
        if (!left.has(Apoth.Components.AFFIX_NAME)) return;
        String name = event.getName();
        if (name == null || name.isEmpty()) return;
        ItemStack output = left.copy();
        output.set(DataComponents.CUSTOM_NAME, Component.literal(name));
        Style wrapperStyle = Style.EMPTY;
        DynamicHolder<LootRarity> rarity = AffixHelper.getRarity(output);
        if (rarity.isBound()) {
            wrapperStyle = wrapperStyle.withColor(rarity.get().color());
        }
        // %2$s mirrors Apoth's Sigil of Unnaming: the affix-name template normally renders as
        // "<prefix> %1$s <suffix>" wrapping the item's base name. Replacing it with "%2$s" plus
        // two empty args collapses the wrapper to nothing, letting vanilla's CUSTOM_NAME show through.
        // We keep the AFFIX_NAME component present (just retemplated) because Apoth checks for the
        // marker's existence in tooltip/rendering paths; stripping it would break those expectations.
        // Italic is intentionally not forced false here. Vanilla renders CUSTOM_NAME in italic by
        // default at the renderer level, so leaving the wrapper style untouched preserves the
        // standard vanilla "renamed in anvil" look while still injecting rarity color.
        AffixHelper.setName(output, Component.translatable("%2$s", "", "").withStyle(wrapperStyle));
        event.setCost(1);
        event.setMaterialCost(0);
        event.setOutput(output);
    }
}
