package com.cicidi.bigdota.domain.dota.ruleEngine;

import com.cicidi.utilities.JSONUtil;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

    public ArrayList<Integer> getTeam_hero(int team) {
        ArrayList<Integer> team_pick = null;
        try {
            if (data == null)
                return null;
            Map matchDetail = (Map) this.data.get(DotaAnalyticsfield.MATCH_DETAIL.name());
            if (matchDetail == null)
                return null;
            Map heroPick = (Map) matchDetail.get(DotaAnalyticsfield.HERO_PICK.name());
            if (heroPick == null)
                return null;
            if (team == 0)
                team_pick = (ArrayList<Integer>) heroPick.get(DotaAnalyticsfield.TEAM_0_HERO_PICK.name());
            else {
                team_pick = (ArrayList<Integer>) heroPick.get(DotaAnalyticsfield.TEAM_1_HERO_PICK.name());
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

    public ArrayList<Integer> getTeamPlayer(int team) {
        ArrayList<Integer> team_players = null;
        try {
            if (data == null)
                return null;
            Map matchDetail = (Map) this.data.get(DotaAnalyticsfield.MATCH_DETAIL.name());
            if (matchDetail == null)
                return null;
            Map playersId = (Map) matchDetail.get(DotaAnalyticsfield.PLAYERS_ID.name());
            if (playersId == null)
                return null;
            if (team == 0)
                team_players = (ArrayList<Integer>) playersId.get(DotaAnalyticsfield.TEAM_0_PLAYERS.name());
            else {
                team_players = (ArrayList<Integer>) playersId.get(DotaAnalyticsfield.TEAM_1_PLAYERS.name());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
        return team_players;
    }
}
