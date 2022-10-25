package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.*;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.*;

import java.util.HashMap;

public class CardMapper {

    private static final String CARD_CARDNUMBER = "cardNumber";
    private static final String CARD_CARDCOLOR = "cardColor";
    private static final String CARD_ACTION = "action";

    private static final String CARDACTION_ACTION = "action";
    private static final String CARDACTION_DRAW = "draw";

    private static final String CARDNUMBER_NUMBER = "number";

    private CardMapper() {}

    public static JsonElement cardToJson(Card card) {
        if(card == null) return new JsonNull();

        HashMap<String, JsonElement> props = new HashMap<>();
        props.put(CARD_CARDNUMBER, cardNumberToJson(card.getCardNumber()));
        props.put(CARD_CARDCOLOR, cardColorToJson(card.getColor()));
        props.put(CARD_ACTION, cardActionToJson(card.getAction()));

        return new JsonObject(props);
    }

    private static JsonElement cardNumberToJson(CardNumber cardNumber) {
        if(cardNumber == null) return new JsonNull();

        HashMap<String, JsonElement> props = new HashMap<>();
        props.put(CARDNUMBER_NUMBER, new JsonNumber(cardNumber.getNumber()));

        return new JsonObject(props);
    }

    public static JsonElement cardColorToJson(CardColor cardColor) {
        if(cardColor == null) return new JsonNull();

        return new JsonString(cardColor.name());
    }

    private static JsonElement cardActionToJson(CardAction action) {
        if(action == null) return new JsonNull();

        HashMap<String, JsonElement> props = new HashMap<>();
        props.put(CARDACTION_ACTION, new JsonString(action.getAction().toString()));
        props.put(CARDACTION_DRAW, new JsonNumber(action.getDraw()));

        return new JsonObject(props);
    }



    public static Card cardFromJson(JsonElement jsonElement) {
        if(jsonElement instanceof JsonNull) return null;

        JsonObject jsonObject = (JsonObject) jsonElement;

        CardNumber cardNumber = cardNumberFromJson(jsonObject.get(CARD_CARDNUMBER));
        CardColor cardColor = cardColorFromJson(jsonObject.get(CARD_CARDCOLOR));
        CardAction cardAction = cardActionFromJson(jsonObject.get(CARD_ACTION));

        return new Card(cardColor, cardNumber, cardAction);

    }

    private static CardNumber cardNumberFromJson(JsonElement jsonElement) {
        if(jsonElement instanceof JsonNull) return null;

        JsonObject jsonObject = (JsonObject) jsonElement;
        JsonNumber jsonNumber = (JsonNumber) jsonObject.get(CARDNUMBER_NUMBER);
        return new CardNumber((Integer) jsonNumber.getValue());
    }

    public static CardColor cardColorFromJson(JsonElement jsonElement) {
        if(jsonElement instanceof JsonNull) return null;

        return CardColor.valueOf(((JsonString) jsonElement).getValue());
    }

    private static CardAction cardActionFromJson(JsonElement jsonElement) {
        if(jsonElement instanceof JsonNull) return null;

        JsonObject jsonObject = (JsonObject) jsonElement;
        Action action = Action.valueOf(((JsonString) jsonObject.get(CARDACTION_ACTION)).getValue());
        Integer draw = (Integer) ((JsonNumber) jsonObject.get(CARDACTION_DRAW)).getValue();

        return new CardAction(action, draw);
    }

}
