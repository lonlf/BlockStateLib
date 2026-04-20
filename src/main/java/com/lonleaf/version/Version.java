package com.lonleaf.version;

public enum Version {
    V26_1,
    V1_21_11,
    V1_21_9,
    V1_21_7,
    V1_21_6,
    V1_21_5,
    V1_21_4,
    V1_21_3,
    V1_21,
    V1_20_5,
    V1_20_3,
    V1_20_2,
    V1_20,
    V1_19_4;

    /**
     *
     * @param version e.g., V1_20_1
     * @return e.g., 1.20.1
     */
    public String getMinecraftVersion(Version version){
        return version.name()
                .replace("V","")
                .replace("_",".");
    }
}
