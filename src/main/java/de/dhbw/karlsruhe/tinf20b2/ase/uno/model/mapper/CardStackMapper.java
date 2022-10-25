package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonArray;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonElement;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonObject;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.Card;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.CardStack;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CardStackMapper {

    private static final String CARDSTACK_CARDLIST = "cardList";

    private CardStackMapper() {}

    public static JsonElement cardStackToJson(CardStack cardStack) {
        HashMap<String, JsonElement> props = new HashMap<>();

        List<JsonElement> cards = cardStack.getCardList()
                .stream()
                .map(CardMapper::cardToJson)
                .toList();
        JsonArray elements = new JsonArray(cards);
        props.put(CARDSTACK_CARDLIST, elements);

        return new JsonObject(props);
    }

    public static CardStack cardStackFromJson(JsonElement jsonElement) {
        JsonObject jsonObject = (JsonObject) jsonElement;
        JsonArray jsonArray = (JsonArray) jsonObject.get(CARDSTACK_CARDLIST);

        List<Card> cardList = jsonArray.getElements()
                .stream()
                .map(CardMapper::cardFromJson)
                .filter(Objects::nonNull)
                .toList();
        return new CardStack(cardList);
    }

}
