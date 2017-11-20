package com.cicidi.bigdota.extermal;

import com.cicidi.bigdota.domain.dota.DotaPlayer;
import com.cicidi.bigdota.util.EnvConfig;
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

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private Client client;
    private String openDotaUrl = EnvConfig.OPENDOTA_API;
    private String matchEndpoint = EnvConfig.OPENDOTA_MATCHES;

    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 15000))
    public String getReplay(String matchId) {
        String path = openDotaUrl + matchEndpoint + "/" + matchId;
        logger.debug(path);
        WebResource webResource = client
                .resource(path);

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);
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
            backoff = @Backoff(delay = 15000))
    public List<LinkedHashMap> getMatchIdByAccountId(String id) {
        try {
            WebResource webResource = client
                    .resource("https://api.opendota.com/api/players/" + id + "/matches");
            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);
            if (response.getStatus() != 200) {
                logger.error("player failed: " + id);
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
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

