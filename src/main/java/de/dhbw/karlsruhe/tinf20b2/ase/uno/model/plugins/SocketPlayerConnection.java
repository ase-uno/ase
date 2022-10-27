package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.plugins;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.*;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper.HighScoreMapper;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonConverter;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonElement;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonObject;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonString;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper.CardMapper;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper.CardStackMapper;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper.PlayerMapper;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class SocketPlayerConnection implements PlayerConnection {

    private static final String ACTION = "action";
    private static final String DATA = "data";

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
        request.put(DATA, new JsonObject(data));

        JsonObject jsonObject = new JsonObject(request);

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(jsonObject.toJson());

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String response = dataInputStream.readUTF();
            JsonElement jsonElement = new JsonConverter().fromJson(response);
           return CardMapper.cardFromJson(jsonElement);
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
    public void broadcastWinner(SimplePlayer winner) {
        broadcastPlayer("broadcastWinner", winner);
    }

    @Override
    public void broadcastActivePlayer(SimplePlayer player) {
        broadcastPlayer("broadcastActivePlayer", player);
    }

    @Override
    public void broadcastHighScore(HighScore highScore) {

        HashMap<String, JsonElement> data = new HashMap<>();
        data.put("highScore", HighScoreMapper.highScoreToJson(highScore));

        HashMap<String, JsonElement> request = new HashMap<>();
        request.put(ACTION, new JsonString("broadcastHighScore"));
        request.put(DATA, new JsonObject(data));

        broadcastMessage(new JsonObject(request));
    }

    private void broadcastPlayer(String action, SimplePlayer player) {
        HashMap<String, JsonElement> data = new HashMap<>();
        data.put("player", PlayerMapper.playerDTOToJson(player));


        HashMap<String, JsonElement> request = new HashMap<>();
        request.put(ACTION, new JsonString(action));
        request.put(DATA, new JsonObject(data));

        broadcastMessage(new JsonObject(request));
    }

    private void broadcastMessage(JsonObject jsonObject) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(jsonObject.toJson());
        } catch (Exception ignored) {
            //ignore error for this message
        }
    }
}
