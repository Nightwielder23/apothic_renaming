package com.nightwielder.apothicrenaming.util;

import net.neoforged.fml.ModList;

public final class ApotheosisDetector {
    private static Boolean apotheosisLoaded;

    private ApotheosisDetector() {}

    public static boolean isApotheosisLoaded() {
        if (apotheosisLoaded == null) {
            apotheosisLoaded = ModList.get().isLoaded("apotheosis");
        }
        return apotheosisLoaded;
    }
}
