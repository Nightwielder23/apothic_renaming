package com.nightwielder.apothicrenaming;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class Config {
    private Config() {}

    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue ENABLED;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("anvil");
        ENABLED = builder
                .comment("Anvil renames override Apotheosis prefix/suffix decoration. Affixes, sockets, and rarity color stay intact.")
                .define("enabled", false);
        builder.pop();
        SPEC = builder.build();
    }
}
