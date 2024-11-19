package com.algoteque.system.dao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.*;

public class StringToListDeserializer extends JsonDeserializer<Map<String, List<String>>> {

    @Override
    public Map<String, List<String>> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        final Map<String, List<String>> providerToTopic = new HashMap<>();
        final TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);

        List<String> fieldNames = new ArrayList<>();
        for (Iterator<String> it = treeNode.fieldNames(); it.hasNext(); ) {
            fieldNames.add(it.next());
        }
        for (String fieldName : fieldNames) {
            List<String> topics = Arrays.asList(treeNode.get(fieldName).toString().replaceAll("\"", "")
                    .split("\\+"));
            providerToTopic.put(fieldName, topics);
        }
        return providerToTopic;
    }
}
