package com.nightwielder.apothicrenaming.handler;

import com.nightwielder.apothicrenaming.ApothicRenaming;
import com.nightwielder.apothicrenaming.util.ApotheosisDetector;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.loot.LootRarity;
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
        LootRarity rarity = AffixHelper.getRarity(output);
        if (rarity != null) {
            wrapperStyle = wrapperStyle.withColor(rarity.color());
        }
        // %2$s collapse mirrors Apoth's Sigil of Unnaming (UnnamingRecipe in Apoth 6.x). The
        // affix_data.name is stored as a TranslatableContents that ItemStackMixin re-templates at
        // render: it slots the vanilla hover name into the wrapper. Storing "%2$s" with empty args
        // makes the mixin write the vanilla name into arg 1, so only that name renders and the
        // prefix/suffix vanish. The name entry stays present so Apoth's renamed-marker check holds.
        AffixHelper.setName(output, Component.translatable("%2$s", "", "").withStyle(wrapperStyle));
        event.setCost(1);
        event.setMaterialCost(0);
        event.setOutput(output);
    }
}
