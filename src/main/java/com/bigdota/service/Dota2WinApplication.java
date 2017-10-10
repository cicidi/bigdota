package com.bigdota.service;

import com.bigdota.cassandra.CassandraConnector;
import com.bigdota.domain.MatchReplay;
import com.bigdota.domain.MatchReplayUtil;
import com.bigdota.extermal.SteamClient;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by cicidi on 8/21/2017.
 */
public class Dota2WinApplication {

    private static final Logger logger = Logger.getLogger(Dota2WinApplication.class);

    public static void main(String[] args) throws IOException {
        CassandraConnector cassandraConnector = new CassandraConnector();
        SteamClient steamClient = new SteamClient();
        List<LinkedHashMap> matchList = steamClient.getMatchIdByAccountId("105248644");
        long current = System.currentTimeMillis();
        int i = 0;
        cassandraConnector.connect();
        for (LinkedHashMap map : matchList) {
            if (i > 99) {
                break;
            }
            long matchId = (Long) map.get("match_id");
            i++;
            String data = steamClient.getReplay(matchId);


            MatchReplay matchReplay = new MatchReplay(matchId, MatchReplayUtil.StringToBlob(data), current);
            MatchReplayUtil.getPlayers(matchReplay, "all");
//            if (!cassandraConnector.isExist(matchReplay)) {
            cassandraConnector.saveReplay(matchReplay);
//            }
        }
        cassandraConnector.close();
    }


    public static int createRandomNumber() {
        Random rand = new Random();
        int n = rand.nextInt(20) + 1;
        return n;
    }

    public static int[] createTeam() {
        int[] team = new int[5];
        for (int i = 0; i < 5; i++) {
            team[i] = createRandomNumber();
        }
        return team;
    }

    public static List<int[]> createGame() {
        List<int[]> game = new ArrayList<>();
        int[] team_1 = createTeam();
        int[] team_2 = createTeam();
        game.add(team_1);
        game.add(team_2);
        return game;
    }

    public static void print(int[] team) {
        for (int i = 0; i < team.length; i++) {
            System.out.print(team[i]);
            System.out.print(",");
        }
        System.out.println();
    }
}