package com.cicidi.bigdota.util;

import com.cicidi.bigdota.ruleEngine.DotaAnalyticsfield;
import com.cicidi.bigdota.ruleEngine.MatchReplayView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Created by cicidi on 9/17/2017.
 */
public class MatchReplayUtil {
    public static int matchCount = 0;
    public static int failed = 0;
//    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static String blogToString(ByteBuffer byteBuffer) {
        byte[] array = byteBuffer.array();
        String str = null;
        try {
            str = CompressUtil.decompress(array);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static ByteBuffer StringToBlob(String data) throws IOException {
        byte[] compressedData = CompressUtil.compress(data);
        ByteBuffer byteBuffer = ByteBuffer.wrap(compressedData);
        return byteBuffer;
    }

    public static Iterator<String> combine(MatchReplayView matchReplayView) {
        List<String> result = new LinkedList<>();
        List<String> list_0 = new LinkedList<>();
        List<String> list_1 = new LinkedList<>();
        Boolean team_0_win = (Boolean) matchReplayView.getData().get(DotaAnalyticsfield.TEAM_0_WIN.name());
        combine("", 0, matchReplayView.getTeam_hero(0), list_0);
        combine("", 0, matchReplayView.getTeam_hero(1), list_1);
//        combine("", 0, (int[]) matchReplayView.getData().get(Constants.TEAM_1_HEROS), list_2);

        if (team_0_win == null) {
            return result.iterator();
        }
        for (String a : list_0) {
            for (String b : list_1) {
//                String c = a < b ? a * (Math.pow(10, lengthB)) + "-" + b : b * (Math.pow(10, lengthA)) + "-" + +a;
//                String[] arr = new String[2];
                if (!comboSizeFilter(a, b))
                    continue;
                if (team_0_win) {
                    result.add(a + " vs " + b);
                } else {
                    result.add(b + " vs " + a);
                }
            }
        }
        return result.iterator();
    }


    public static boolean comboSizeFilter(String a, String b) {
        int count_a = StringUtils.countMatches(a, "+");
        int count_b = StringUtils.countMatches(b, "+");
        if (count_a > 0 || count_b > 0)
            return true;
        return false;
    }


    public static Integer getGame_Mode(String matchData) {
        try {
            return JsonPath.read(matchData, MatchJsonPath.game_mode);
        } catch (PathNotFoundException pathNotFoundException) {
            return null;
        }
    }

    public static Map<DotaAnalyticsfield, Object> getHeros_normalModel(String rawData) {
        int[] team0_heros = new int[5];
        int[] team1_heros = new int[5];
        LinkedHashMap json = null;
        try {
            json = JSONUtil.getObjectMapper().readValue(rawData, LinkedHashMap.class);
        } catch (JsonParseException e) {
            System.out.println(rawData);
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println(rawData);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(rawData);
            e.printStackTrace();
        }
        for (int i = 0, team_0 = 0, team_1 = 0; i < 10; i++) {
            List<LinkedHashMap> players = (List<LinkedHashMap>) json.get("players");
            if ((int) players.get(i).get("player_slot") < 5) {
                team0_heros[team_0] = (int) players.get(i).get("hero_id");
                team_0++;
            } else {
                team1_heros[team_1] = (int) players.get(i).get("hero_id");
                team_1++;
            }
        }
        Arrays.sort(team0_heros);
        Arrays.sort(team1_heros);
        Map<DotaAnalyticsfield, Object> result = new HashMap<>();
        result.put(DotaAnalyticsfield.TEAM_0_HERO_PICK, team0_heros);
        result.put(DotaAnalyticsfield.TEAM_1_HERO_PICK, team1_heros);
//        result.put(DotaAnalyticsfield.TEAM_0_WIN, false);
        return result;
    }

    public static Map<DotaAnalyticsfield, Object> getHeros(String rawData) {
        List<LinkedHashMap> pickBan = getPick_Ban(rawData);
        if (pickBan == null) return null;
        System.out.println(MatchReplayUtil.getGame_Mode(rawData));
        Map map = getHeros(pickBan);
        map.put(DotaAnalyticsfield.TEAM_0_WIN, getMatchResult(rawData));
        return map;
    }

    public static List<LinkedHashMap> getPick_Ban(String matchData) {
        try {
            return JsonPath.read(matchData, MatchJsonPath.picks_bans);
        } catch (PathNotFoundException pathNotFoundException) {
            return null;
        }
    }

    public static Map<DotaAnalyticsfield, Object> getHeros(List<LinkedHashMap> list) {
        if (list == null) {
            return null;
        }
        int[] team0_heros = new int[5];
        int[] team1_heros = new int[5];
        int team_0 = 0;
        int team_1 = 0;
        for (LinkedHashMap pick_ban : list) {
            if (pick_ban != null) {
                int team = (Integer) pick_ban.get("team");
                boolean isPick = pick_ban.get("is_pick") != null ? (boolean) pick_ban.get("is_pick") : false;
                if (team == 0 && isPick) {
                    team0_heros[team_0] = (int) pick_ban.get("hero_id");
                    team_0++;
                }
                if (team != 0 && isPick) {
                    team1_heros[team_1] = (int) pick_ban.get("hero_id");
                    team_1++;
                }
            } else {
                return null;
            }
        }
        Arrays.sort(team0_heros);
        Arrays.sort(team1_heros);
        Map<DotaAnalyticsfield, Object> result = new HashMap<>();
        result.put(DotaAnalyticsfield.TEAM_0_HERO_PICK, team0_heros);
        result.put(DotaAnalyticsfield.TEAM_1_HERO_PICK, team1_heros);
        return result;
    }

    public static void combine(String current, int start, List team_hero, List<String> list) {
        if (team_hero == null)
            return;
        for (int i = start; i < team_hero.size(); i++) {
            String c = current + "+" + team_hero.get(i);
            combine(c, i + 1, team_hero, list);
            list.add(c);
        }


    }

    public static Boolean getMatchResult(String matchJson) {
        try {
            Boolean result = JsonPath.read(matchJson, MatchJsonPath.match_result_path);
            matchCount++;
            return result;
        } catch (PathNotFoundException pathNotFoundException) {
            failed++;
            return null;
        }
    }

}
