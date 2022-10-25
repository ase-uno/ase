package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.plugins;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonConverter;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonElement;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonObject;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonString;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper.CardMapper;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper.CardStackMapper;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper.PlayerMapper;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.*;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.Card;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.CardColor;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.CardStack;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.dto.PlayerDTO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class SocketPlayerConnection implements PlayerConnection {

    private static final String ACTION = "action";

    private final Socket socket;

    public SocketPlayerConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Card input(Card active, CardStack cardStack) {

        HashMap<String, JsonElement> data = new HashMap<>();
        data.put("active", CardMapper.cardToJson(active));
        data.put("cardStack", CardStackMapper.cardStackToJson(cardStack));


        HashMap<String, JsonElement> request = new HashMap<>();
        request.put(ACTION, new JsonString("input"));
        request.put("data", new JsonObject(data));

        JsonObject jsonObject = new JsonObject(request);

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(jsonObject.toJson());

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String response = dataInputStream.readUTF();
            JsonObject jsonResponse = (JsonObject) new JsonConverter().fromJson(response);
           return CardMapper.cardFromJson(jsonResponse);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CardColor inputColor() {
        HashMap<String, JsonElement> request = new HashMap<>();
        request.put(ACTION, new JsonString("inputColor"));

        JsonObject jsonObject = new JsonObject(request);

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(jsonObject.toJson());

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String response = dataInputStream.readUTF();
            JsonElement jsonResponse = new JsonConverter().fromJson(response);
            return CardMapper.cardColorFromJson(jsonResponse);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void broadcastWinner(PlayerDTO winner) {
        broadcastPlayer("broadcastWinner", winner);
    }

    @Override
    public void broadcastActivePlayer(PlayerDTO player) {
        broadcastPlayer("broadcastActivePlayer", player);
    }

    private void broadcastPlayer(String action, PlayerDTO player) {
        HashMap<String, JsonElement> data = new HashMap<>();
        data.put("player", PlayerMapper.playerDTOToJson(player));


        HashMap<String, JsonElement> request = new HashMap<>();
        request.put(ACTION, new JsonString(action));
        request.put("data", new JsonObject(data));

        JsonObject jsonObject = new JsonObject(request);

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(jsonObject.toJson());
        } catch (Exception ignored) {
            //ignore error for this message
        }
    }
}
