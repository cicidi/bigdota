package com.cicidi.bigdota.util;

/**
 * Created by cicidi on 10/11/2017.
 */
public class MatchJsonPath {
    public static final String match_result_path = "$.radiant_win";
    //    public static final String heros = "$.picks_bans.[?(@.team ={team}&&@.is_pick == true)]";
    public static final String picks_bans = "$.picks_bans";
    public static final String game_mode = "$.game_mode";
    public static final String normal_mode_hero = "$['players'][?]['hero_id']";
}
