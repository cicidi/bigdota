package com.cicidi.bigdota.util;

import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.bigdota.domain.dota.MatchReplayView;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import jnr.ffi.annotations.In;
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
        List<String> list_1 = new LinkedList<>();
        List<String> list_2 = new LinkedList<>();
        combine("", 0, (int[]) matchReplayView.getData().get(Constants.TEAM_0_HEROS), list_1);
        combine("", 0, (int[]) matchReplayView.getData().get(Constants.TEAM_1_HEROS), list_2);
        Boolean team_0_win = (Boolean) matchReplayView.getData().get(Constants.TEAM_0_WIN);
        if (team_0_win == null) {
            return result.iterator();
        }
        for (String a : list_1) {
            for (String b : list_2) {
//                String c = a < b ? a * (Math.pow(10, lengthB)) + "-" + b : b * (Math.pow(10, lengthA)) + "-" + +a;
                String[] arr = new String[2];
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
        if (count_a > 2 || count_b > 2)
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

    //
    public static int[] getHeros(String rawData, int i) {
        return getHeros(getPick_Ban(rawData), i);
    }

    public static List<LinkedHashMap> getPick_Ban(String matchData) {
        try {
            return JsonPath.read(matchData, MatchJsonPath.picks_bans);
        } catch (PathNotFoundException pathNotFoundException) {
            return null;
        }
    }

    public static int[] getHeros(List<LinkedHashMap> list, int i) {
//        try {
//            list = JsonPath.read(matchData, MatchJsonPath.picks_bans);
//        } catch (PathNotFoundException pathNotFoundException) {
//            return null;
//        }
        if (list == null) {
            return null;
        }
        int[] result = new int[5];
        int index = 0;
        for (LinkedHashMap pick_ban : list) {
            if (pick_ban != null) {
                int team = (Integer) pick_ban.get("team");
                boolean isPick = pick_ban.get("is_pick") != null ? (boolean) pick_ban.get("is_pick") : false;
                if (team == i && isPick) {
                    result[index] = (int) pick_ban.get("hero_id");
                    index++;
                }
            } else {
                return null;
            }

        }
        Arrays.sort(result);
        return result;
    }

    public static void combine(String current, int start, int[] arr, List<String> list) {
        if (arr == null)
            return;
        for (int i = start; i < arr.length; i++) {
            String c = current + "+" + arr[i];
            combine(c, i + 1, arr, list);
            list.add(c);
        }


    }

    public static Boolean getMatchResult(String matchJson) {
        String query = "$.radiant_win";
        try {
            Boolean result = JsonPath.read(matchJson, query);
            matchCount++;
            return result;
        } catch (PathNotFoundException pathNotFoundException) {
            failed++;
            return null;
        }
    }

}
