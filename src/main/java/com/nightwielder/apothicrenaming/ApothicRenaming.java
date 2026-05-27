package com.nightwielder.apothicrenaming;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(ApothicRenaming.MODID)
public class ApothicRenaming {
    public static final String MODID = "apothic_renaming";

    public ApothicRenaming(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }
}
