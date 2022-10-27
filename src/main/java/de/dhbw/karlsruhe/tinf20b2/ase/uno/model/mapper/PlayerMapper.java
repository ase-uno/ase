package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonElement;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonObject;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonString;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.SimplePlayer;

import java.util.HashMap;

public class PlayerMapper {

    private static final String PLAYERDTO_NAME = "name";


    private PlayerMapper() {}

    public static JsonElement playerDTOToJson(SimplePlayer player) {
        HashMap<String, JsonElement> props = new HashMap<>();
        props.put(PLAYERDTO_NAME, new JsonString(player.getName()));

        return new JsonObject(props);
    }

    public static SimplePlayer playerDTOFromJson(JsonElement jsonElement) {
        JsonObject jsonObject = (JsonObject) jsonElement;
        String name = ((JsonString) jsonObject.get(PLAYERDTO_NAME)).getValue();

        return new SimplePlayer(name);
    }

}
