package com.cicidi.bigdota.util;

import com.cicidi.bigdota.domain.dota.ruleEngine.DotaAnalyticsfield;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.util.Iterator;

public class DotaAnalyticsfieldDeserializer extends JsonDeserializer<DotaAnalyticsfield> {
    @Override
    public DotaAnalyticsfield deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode jn = oc.readTree(jsonParser);
        Iterator<JsonNode> elements = jn.getElements();
        if (elements.hasNext()) {
            JsonNode jsonNode = elements.next();
            String i = jsonNode.has("GameMode") ? jsonNode.get("GameMode").asText() : null;
            DotaAnalyticsfield dotaAnalyticsfield = DotaAnalyticsfield.valueOf(i);
            return dotaAnalyticsfield;
        }
        return null;
    }
}
