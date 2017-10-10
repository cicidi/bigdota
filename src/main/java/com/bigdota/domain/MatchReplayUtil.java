package com.bigdota.domain;

import com.bigdota.util.StringUtils;
import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cicidi on 9/17/2017.
 */
public class MatchReplayUtil {
    private static final Logger logger = LoggerFactory.getLogger(MatchReplayUtil.class);

    public static List<String> getPlayers(MatchReplay matchReplay, String team) {

        List<String> players = new ArrayList<>();
        ByteBuffer byteBuffer = matchReplay.getData();
        String json = blogToString(byteBuffer);
        String query;
        if (team.equals("radiant"))
            query = "$.players.[?(@.player_slot<5)].account_id";
        else {
            query = "$.players.[?(@.player_slot>4)].account_id";
        }
        List<Object> list = JsonPath.read(json, query);
        logger.info("size {}", list.size());
        return players;
    }

    public static String blogToString(ByteBuffer byteBuffer) {
        byte[] array = byteBuffer.array();
        String str = null;
        try {
            str = StringUtils.decompress(array);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static ByteBuffer StringToBlob(String data) throws IOException {
        byte[] compressedData = StringUtils.compress(data);
        ByteBuffer byteBuffer = ByteBuffer.wrap(compressedData);
        return byteBuffer;
    }
}
