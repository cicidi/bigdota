package com.cicidi.bigdota.domain.dota.ruleEngine;

import com.cicidi.bigdota.util.GameModeEnumDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = GameModeEnumDeserializer.class)
public enum GameModeEnum {
    MODE_0(0),
    MODE_1(1),
    MODE_2(2),
    MODE_3(3),
    MODE_4(4),
    MODE_5(5),
    MODE_6(6),
    MODE_7(7),
    MODE_8(8),
    MODE_9(9),
    MODE_10(10),
    MODE_11(11),
    MODE_12(12),
    MODE_13(13),
    MODE_14(14),
    MODE_15(15),
    MODE_16(16),
    MODE_17(17),
    MODE_18(18),
    MODE_19(19),
    MODE_20(20),
    MODE_21(21),
    MODE_22(22),
    MODE_23(23),
    MODE_24(24),
    MODE_25(25),
    MODE_26(26),
    MODE_27(27),
    MODE_28(28),
    MODE_29(29),
    MODE_30(30),
    MODE_31(31),
    MODE_32(32),
    MODE_33(33),
    MODE_34(34),
    MODE_35(35),
    MODE_36(36),
    MODE_37(37),
    MODE_38(38),
    MODE_39(39),
    MODE_40(40),
    MODE_41(41),
    MODE_42(42),
    MODE_43(43),
    MODE_44(44),
    MODE_45(45),
    MODE_46(46),
    MODE_47(47),
    MODE_48(48),
    MODE_49(49),
    MODE_404(404);


    private int value;

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
