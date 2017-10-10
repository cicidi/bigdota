package com.bigdota.extermal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by cicidi on 9/5/2017.
 */
public class SteamClient {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(SteamClient.class);

    public String getReplay(long matchId) {
        try {

            Client client = Client.create();

//            String path = "https://api.steampowered.com/IDOTA2Match_570GetMatchDetails/V001/?match_id=" + matchId + "?key=5F1688B85B0E81D26D5560B05728490D";
            String path = "https://api.opendota.com/api/matches/" + matchId;
            System.out.println(path);
            WebResource webResource = client
                    .resource(path);

            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            return response.getEntity(String.class);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }

    }

    //105248644
    public List getMatchIdByAccountId(String id) {

        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("https://api.opendota.com/api/players/" + id + "/matches");

            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            List<LinkedHashMap> matchList = objectMapper.readValue(response.getEntity(String.class), new TypeReference<List<LinkedHashMap>>() {
            });
            return matchList;
//            String output = response.getEntity(String.class);
//
//            System.out.println("Output from Server .... \n");
//            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }
}

