package com.nightwielder.apothicrenaming;

import net.minecraftforge.common.ForgeConfigSpec;

public final class Config {
    private Config() {}

    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue ENABLED;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("anvil");
        ENABLED = builder
                .comment("Anvil renames override Apotheosis prefix/suffix decoration. Affixes, sockets, and rarity color stay intact.")
                .define("enabled", false);
        builder.pop();
        SPEC = builder.build();
    }
}
