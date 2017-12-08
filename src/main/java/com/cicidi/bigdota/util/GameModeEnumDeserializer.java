package com.cicidi.bigdota.util;

import com.cicidi.bigdota.domain.dota.ruleEngine.GameModeEnum;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.util.Iterator;

public class GameModeEnumDeserializer extends JsonDeserializer<GameModeEnum> {
    @Override
    public GameModeEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode jn = oc.readTree(jsonParser);
        Iterator<JsonNode> elements = jn.getElements();
        if (elements.hasNext()) {
            JsonNode jsonNode = elements.next();
            int i = jsonNode.has("GameMode") ? jsonNode.get("GameMode").asInt() : -1;
            GameModeEnum gameModeEnum = GameModeEnum.getEnum(i);
            return gameModeEnum;
        }
        return null;
    }
}
