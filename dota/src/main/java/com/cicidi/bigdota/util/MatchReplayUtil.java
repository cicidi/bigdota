package com.cicidi.bigdota.util;

import com.cicidi.bigdota.domain.dota.ruleEngine.DotaAnalyticsfield;
import com.cicidi.bigdota.domain.dota.ruleEngine.MatchReplayView;
import com.cicidi.utilities.JSONUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by cicidi on 9/17/2017.
 */
public class MatchReplayUtil {
    public static int failed = 0;
    public static long totalCombination = 0;
    public static Map<String, Boolean> map = new TreeMap<>();
    public static int team0_pick_amount_query;
    public static int team1_pick_amount_query;


    public static void addGameMode(String mode, Boolean canPick) {
        map.put(mode, canPick);
    }

    public static Iterator<String> combine(MatchReplayView matchReplayView) {
        List<String> result = new LinkedList<>();
        List<String> list_0 = new LinkedList<>();
        List<String> list_1 = new LinkedList<>();
        Boolean team_0_win = matchReplayView.getGameResult();
        ArrayList team1_pick = matchReplayView.getTeam_hero(0);
        ArrayList team2_pick = matchReplayView.getTeam_hero(1);
        if (team1_pick == null | team2_pick == null)
            return result.iterator();
        combine("", 0, team1_pick, list_0);
        combine("", 0, team2_pick, list_1);
        if (team_0_win == null) {
            return result.iterator();
        }
        for (String a : list_0) {
            for (String b : list_1) {
                if (!comboSizeFilter(a, b))
                    continue;
                if (team_0_win) {
                    result.add(a + " vs " + b);
                } else {
                    result.add(b + " vs " + a);
                }
            }
        }
        totalCombination += result.size();
        return result.iterator();
    }


    public static boolean comboSizeFilter(String a, String b) {
        int count_a = StringUtils.countMatches(a, "+");
        int count_b = StringUtils.countMatches(b, "+");
        if (count_a >= team0_pick_amount_query && count_b >= team1_pick_amount_query)
            return true;
        else
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
        int[] team0_players = new int[5];
        int[] team1_players = new int[5];
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
            if (players.size() != 10) {
                return null;
            }
            if ((int) players.get(i).get("player_slot") < 5) {
                if ((int) players.get(i).get("hero_id") == 0) {
                    System.out.println("error");
                    return null;
                }
                Object heroId = players.get(i).get("hero_id");
                if (heroId != null) {
                    team0_heros[team_0] = (int) heroId;
                } else {
                    return null;
                }
                team0_players[team_0] = getAccountIdNullSafe(players.get(i).get("account_id"));
                team_0++;
            } else {
                Object heroId = players.get(i).get("hero_id");
                if (heroId != null) {
                    team1_heros[team_1] = (int) heroId;
                } else {
                    return null;
                }
                team1_players[team_1] = getAccountIdNullSafe(players.get(i).get("account_id"));
                team_1++;
            }
        }
        Arrays.sort(team0_heros);
        Arrays.sort(team1_heros);
        Arrays.sort(team0_players);
        Arrays.sort(team1_players);
        Map<DotaAnalyticsfield, Object> heroPick = new HashMap<>();
        heroPick.put(DotaAnalyticsfield.TEAM_0_HERO_PICK, team0_heros);
        heroPick.put(DotaAnalyticsfield.TEAM_1_HERO_PICK, team1_heros);

        Map<DotaAnalyticsfield, Object> playersIdMap = new HashMap<>();
        playersIdMap.put(DotaAnalyticsfield.TEAM_0_PLAYERS, team0_players);
        playersIdMap.put(DotaAnalyticsfield.TEAM_1_PLAYERS, team1_players);
        Map<DotaAnalyticsfield, Object> result = new HashMap<>();

        result.put(DotaAnalyticsfield.HERO_PICK, heroPick);
        result.put(DotaAnalyticsfield.PLAYERS_ID, playersIdMap);

        return result;
    }

    public static Map<DotaAnalyticsfield, Object> getMatchDetails(String rawData) {
        List<LinkedHashMap> pickBan = getPick_Ban(rawData);
        if (pickBan == null) return null;
        Map<DotaAnalyticsfield, Object> map = getHeros(pickBan);

        List<LinkedHashMap> players = getPlayers(rawData);
        Map<DotaAnalyticsfield, Object> mapPlayers = getAccountId(players);
        map.putAll(mapPlayers);
        return map;
    }

    private static Map<DotaAnalyticsfield, Object> getAccountId(List<LinkedHashMap> players) {
        if (players == null) {
            return null;
        }
        int[] team0_account_id = new int[5];
        int[] team1_account_id = new int[5];
        int team_0 = 0;
        int team_1 = 0;
        for (LinkedHashMap player : players) {
            if (player != null) {
                Boolean isRadiant = (Boolean) player.get("isRadiant");
                if (isRadiant) {
                    team0_account_id[team_0] = getAccountIdNullSafe(player.get("account_id"));
                    team_0++;
                } else {
                    team1_account_id[team_1] = getAccountIdNullSafe(player.get("account_id"));
                    team_1++;
                }
            } else {
                return null;
            }
        }
        Arrays.sort(team0_account_id);
        Arrays.sort(team1_account_id);
        Map<DotaAnalyticsfield, Object> accoutIdMap = new HashMap<>();
        accoutIdMap.put(DotaAnalyticsfield.TEAM_0_PLAYERS, team0_account_id);
        accoutIdMap.put(DotaAnalyticsfield.TEAM_1_PLAYERS, team1_account_id);
        Map<DotaAnalyticsfield, Object> result = new HashMap<>();
        result.put(DotaAnalyticsfield.PLAYERS_ID, accoutIdMap);
        return result;

    }

    private static List<LinkedHashMap> getPlayers(String matchData) {
        try {
            return JsonPath.read(matchData, MatchJsonPath.players);
        } catch (PathNotFoundException pathNotFoundException) {
            return null;
        }
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
        Map<DotaAnalyticsfield, Object> heroPick = new HashMap<>();
        heroPick.put(DotaAnalyticsfield.TEAM_0_HERO_PICK, team0_heros);
        heroPick.put(DotaAnalyticsfield.TEAM_1_HERO_PICK, team1_heros);
        Map<DotaAnalyticsfield, Object> result = new HashMap<>();
        result.put(DotaAnalyticsfield.HERO_PICK, heroPick);
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
            return result;
        } catch (PathNotFoundException pathNotFoundException) {
            failed++;
            return null;
        }
    }

//    public static Map<DotaAnalyticsfield, Object> getPlayerAccountId(String matchJson) {
//        try {
//            Boolean result = JsonPath.read(matchJson, MatchJsonPath.account_id);
//            return result;
//        } catch (PathNotFoundException pathNotFoundException) {
//            failed++;
//            return null;
//        }
//    }


    public static int getAccountIdNullSafe(Object accountId) {
        if (null != accountId) {
            return (int) accountId;
        }
        return -1;
    }
}
