package com.cicidi.bigdota.extermal;

import com.cicidi.bigdota.domain.DotaPlayer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by cicidi on 9/5/2017.
 */
public class DotaReplayApi {

    private static final Logger logger = LoggerFactory.getLogger(DotaReplayApi.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private Client client;

    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 30000))
    public String getReplay(long matchId) {
        String path = "https://api.opendota.com/api/matches/" + matchId;
        logger.debug(path);
        WebResource webResource = client
                .resource(path);

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

//        if (response.getStatus() != 200) {
//            logger.info("sleep for 10 sec then continue");
//            response = webResource.accept("application/json")
//                    .get(ClientResponse.class);
        if (response.getStatus() != 200) {
            logger.debug("Retry matchId: " + matchId);
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
//        }
        logger.debug(webResource.getURI().getPath());
        logger.debug("matchId: " + matchId);
        return response.getEntity(String.class);

    }

    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 30000))

    public List getMatchIdByAccountId(long id) {

        try {


            WebResource webResource = client
                    .resource("https://api.opendota.com/api/players/" + id + "/matches");

            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);

//            if (response.getStatus() != 200) {
//                logger.info("sleep for 10 sec then continue");
//                Thread.sleep(10000);
//                response = webResource.accept("application/json")
//                        .get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
//            }
            List<LinkedHashMap> matchList = objectMapper.readValue(response.getEntity(String.class), new TypeReference<List<LinkedHashMap>>() {
            });
            return matchList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }

    public List<DotaPlayer> getAllPlayers() {

        WebResource webResource = client
                .resource("https://api.opendota.com/api/proPlayers");

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);
        List<DotaPlayer> matchList = null;
        try {
            matchList = objectMapper.readValue(response.getEntity(String.class), new TypeReference<List<DotaPlayer>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matchList;
    }


}

