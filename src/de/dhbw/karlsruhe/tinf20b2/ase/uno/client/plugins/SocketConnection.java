package de.dhbw.karlsruhe.tinf20b2.ase.uno.client.plugins;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.*;
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
import java.io.IOException;
import java.net.Socket;

public class SocketConnection {

    private final PlayerConnection playerConnection;
    private Socket socket;

    public SocketConnection(String ip, String name, PlayerConnection playerConnection) throws IOException {
        this.playerConnection = playerConnection;
        connect(ip, name);

        while(socket.isConnected()) {
            waitForMessage();
        }
        socket.close();
    }

    private void connect(String ip, String name) throws IOException {
        socket = new Socket(ip, 9999);
        returnValue(new JsonString(name));
    }

    private void waitForMessage() throws IOException {
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        String message = dataInputStream.readUTF();
        try {
            parseMessage(message);
        } catch (Exception e) {
            System.err.println("Exception on parsing Server message");
            e.printStackTrace();
        }
    }

    private void parseMessage(String message) throws JsonConvertException {
        JsonObject jsonObject = (JsonObject) new JsonConverter().fromJson(message);

        String action = ((JsonString) jsonObject.get("action")).getValue();
        JsonElement element = jsonObject.get("data");
        switch (action) {
            case "input" -> {
                JsonObject jsonObject1 = (JsonObject) element;
                Card active = CardMapper.cardFromJson(jsonObject1.get("active"));
                CardStack cardStack = CardStackMapper.cardStackFromJson(jsonObject1.get("cardStack"));

                Card card = playerConnection.input(active, cardStack);
                returnValue(CardMapper.cardToJson(card));
            }
            case "inputColor" -> {
                CardColor color = playerConnection.inputColor();
                returnValue(CardMapper.cardColorToJson(color));
            }
            case "broadcastWinner" -> {
                JsonObject jsonObject1 = (JsonObject) element;
                PlayerDTO playerDTO = PlayerMapper.playerDTOFromJson(jsonObject1.get("player"));
                playerConnection.broadcastWinner(playerDTO);
            }
            case "broadcastActivePlayer" -> {
                JsonObject jsonObject1 = (JsonObject) element;
                PlayerDTO playerDTO = PlayerMapper.playerDTOFromJson(jsonObject1.get("player"));
                playerConnection.broadcastActivePlayer(playerDTO);
            }
            default -> System.err.println("Error, invalid message received from Server");
        }
    }

    private void returnValue(JsonElement jsonElement) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(jsonElement.toJson());
        } catch (Exception e) {
            System.err.println("Exception on returning Server message");
            e.printStackTrace();
        }
    }
}
