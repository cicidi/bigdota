package com.cicidi.bigdota.ruleEngine;

import com.cicidi.bigdota.util.JSONUtil;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cicidi on 10/24/2017.
 */
public class MatchReplayView implements Serializable {
    private String matchId;
    private Map<String, Object> data;
    private long currentTimeStamp;

    public MatchReplayView(String match_id, String matchData) {
        this.matchId = match_id;
        this.data = parse(matchData);
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Map getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public long getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setCurrentTimeStamp(long currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

    public ArrayList getTeam_hero(int team) {
        ArrayList team_pick = null;
        try {
            if (data == null)
                return null;
            Map heroPick = (Map) this.data.get(DotaAnalyticsfield.HERO_PICK.name());
            if (heroPick == null)
                return null;

            if (team == 0)
                team_pick = (ArrayList) heroPick.get(DotaAnalyticsfield.TEAM_0_HERO_PICK.name());
            else {
                team_pick = (ArrayList) heroPick.get(DotaAnalyticsfield.TEAM_1_HERO_PICK.name());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
        return team_pick;
    }

    public Boolean getGameResult() {
        Boolean result = (Boolean) this.data.get(DotaAnalyticsfield.TEAM_0_WIN.name());
        return result;
    }

    public Map<String, Object> parse(String rawData) {
        try {
            Map<String, Object> map = JSONUtil.getObjectMapper().readValue(rawData, new TypeReference<LinkedHashMap<String, Object>>() {
            });
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
