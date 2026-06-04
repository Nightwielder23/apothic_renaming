package com.nightwielder.apothicrenaming.handler;

import com.nightwielder.apothicrenaming.ApothicRenaming;
import com.nightwielder.apothicrenaming.util.ApotheosisDetector;
import dev.shadowsoffire.apotheosis.adventure.affix.AffixHelper;
import dev.shadowsoffire.apotheosis.adventure.loot.LootRarity;
import dev.shadowsoffire.placebo.reload.DynamicHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ApothicRenaming.MODID)
public final class AnvilRenameOverride {
    private AnvilRenameOverride() {}

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        if (!ApotheosisDetector.isApotheosisLoaded()) {
            return;
        }
        ItemStack left = event.getLeft();
        if (left.isEmpty()) {
            return;
        }
        if (!AffixHelper.hasAffixes(left)) {
            return;
        }
        String name = event.getName();
        if (name == null || name.isEmpty()) {
            return;
        }

        ItemStack output = left.copy();
        output.setHoverName(Component.literal(name));

        Style wrapperStyle = Style.EMPTY;
        DynamicHolder<LootRarity> rarity = AffixHelper.getRarity(output);
        if (rarity.isBound()) {
            wrapperStyle = wrapperStyle.withColor(rarity.get().getColor());
        }
        // %2$s collapse mirrors Apoth's Sigil of Unnaming (see UnnamingRecipe.assemble in Apoth 7.x).
        // The affix-name wrapper template normally renders "<prefix> %1$s <suffix>"; replacing it
        // with "%2$s" plus empty args makes the wrapper invisible so vanilla's display.Name shows
        // through. The affix_data.name entry stays present (just blanked) to preserve Apoth's
        // "this item was renamed" marker for any code that checks for its existence.
        AffixHelper.setName(output, Component.translatable("%2$s", "", "").withStyle(wrapperStyle));
        event.setCost(1);
        event.setMaterialCost(0);
        event.setOutput(output);
    }
}
