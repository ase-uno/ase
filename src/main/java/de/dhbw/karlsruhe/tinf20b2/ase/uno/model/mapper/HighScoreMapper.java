package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.HighScore;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.SimplePlayer;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.*;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.stream.Collectors;

public class HighScoreMapper {

    private HighScoreMapper() {}

    public static JsonObject highScoreToJson(HighScore highScore) {
        HashMap<String, JsonElement> elements = highScore.getElements()
                .entrySet()
                .stream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey().getName(), new JsonNumber(e.getValue())))
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue,
                        (jsonElement, jsonElement2) -> {
                            Number n1 = ((JsonNumber) jsonElement).getValue();
                            Number n2 = ((JsonNumber) jsonElement2).getValue();
                            return new JsonNumber(n1.intValue() + n2.intValue());
                        },
                        HashMap::new
                ));

        return new JsonObject(elements);
    }

    public static HighScore highScoreFromJson(JsonElement jsonElement) {
        if(jsonElement instanceof JsonNull) return new HighScore();

        JsonObject jsonObject = (JsonObject) jsonElement;

        HashMap<SimplePlayer, Integer> data = jsonObject.getElements()
                .entrySet()
                .stream()
                .map(e -> new AbstractMap.SimpleEntry<>(new SimplePlayer(e.getKey()), ((JsonNumber) e.getValue()).getValue().intValue()))
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue,
                        Integer::sum,
                        HashMap::new
                ));

        return new HighScore(data);
    }

}
