package com.cicidi.bigdota.domain.dota.ruleEngine;

import com.cicidi.bigdota.util.DotaAnalyticsfieldDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = DotaAnalyticsfieldDeserializer.class)
public enum DotaAnalyticsfield {
    HERO_PICK, TEAM_0_HERO_PICK, TEAM_1_HERO_PICK, PLAYERS_ID, TEAM_0_PLAYERS, TEAM_1_PLAYERS, TEAM_0_WIN, GAME_MODE;

    DotaAnalyticsfield() {

    }
}
