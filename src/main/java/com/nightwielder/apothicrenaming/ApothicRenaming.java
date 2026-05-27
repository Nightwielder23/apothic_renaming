package com.nightwielder.apothicrenaming;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(ApothicRenaming.MODID)
public class ApothicRenaming {
    public static final String MODID = "apothic_renaming";

    public ApothicRenaming() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }
}
