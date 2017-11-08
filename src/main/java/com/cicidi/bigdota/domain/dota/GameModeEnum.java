package com.cicidi.bigdota.domain.dota;

public enum GameModeEnum {
    MODE_22(22),
    MODE_23(23);

    private final int value;

    GameModeEnum(int value) {
        this.value = value;
    }

    public static GameModeEnum getEnum(int value) {
        for (GameModeEnum e : GameModeEnum.values()) {
            if (e.value == value) {
                return e;
            }
        }
        return null;// not found
    }
}
