package com.cicidi.bigdota.domain.dota;

import com.cicidi.bigdota.util.Constants;
import com.cicidi.bigdota.util.MatchReplayUtil;
import org.json.simple.JSONObject;

import java.io.Serializable;

/**
 * Created by cicidi on 10/24/2017.
 */
public class MatchReplayView implements Serializable {
    private long matchId;
    private JSONObject data;
    private long currentTimeStamp;

    public MatchReplayView(MatchReplay matchReplay, ViewEnum viewEnum) {

    }

    public MatchReplayView(Long match_id, String matchData, Long current_time_stamp, ViewEnum viewEnum) {
        if (viewEnum.equals(ViewEnum.HERO)) {
            this.matchId = match_id;
            this.data = new JSONObject();
//            data.put(Constants.TEAM_0_HEROS, MatchReplayUtil.getHeros(matchData, 0));
//            data.put(Constants.TEAM_1_HEROS, MatchReplayUtil.getHeros(matchData, 1));
//            data.put(Constants.TEAM_0_WIN, MatchReplayUtil.getMatchResult(matchData));
        }
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public JSONObject getData() {
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
}
