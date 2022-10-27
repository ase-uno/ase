package de.dhbw.karlsruhe.tinf20b2.ase.uno.client;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.console.ConsoleColor;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.console.ConsoleOut;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.*;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper.HighScoreMapper;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.*;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper.CardMapper;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper.CardStackMapper;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.mapper.PlayerMapper;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketConnection {

    private final PlayerConnection playerConnection;
    private final ConsoleOut console;
    private Socket socket;

    public SocketConnection(
            String ip,
            String name,
            PlayerConnection playerConnection,
            ConsoleOut console) throws IOException {
        this.playerConnection = playerConnection;
        this.console = console;

        connect(ip, name);

        while(socket.isConnected()) {
            waitForMessage();
        }
        socket.close();
    }

    private void connect(String ip, String name) throws IOException {
        socket = new Socket(ip, 9999);
        returnValue(new JsonString(name));
        console.println(ConsoleColor.GREEN, "Connected");
    }

    private void waitForMessage() throws IOException {
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        String message = dataInputStream.readUTF();
        try {
            parseMessage(message);
        } catch (Exception e) {
            console.error("Exception on parsing Server message");
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
                SimplePlayer simplePlayer = PlayerMapper.playerDTOFromJson(jsonObject1.get("player"));
                playerConnection.broadcastWinner(simplePlayer);
            }
            case "broadcastActivePlayer" -> {
                JsonObject jsonObject1 = (JsonObject) element;
                SimplePlayer simplePlayer = PlayerMapper.playerDTOFromJson(jsonObject1.get("player"));
                playerConnection.broadcastActivePlayer(simplePlayer);
            }
            case "broadcastHighScore" -> {
                JsonObject jsonObject1 = (JsonObject) element;
                HighScore highScore = HighScoreMapper.highScoreFromJson(jsonObject1.get("highScore"));
                playerConnection.broadcastHighScore(highScore);
            }
            default -> console.error("Error, invalid message received from Server");
        }
    }

    private void returnValue(JsonElement jsonElement) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(jsonElement.toJson());
        } catch (Exception e) {
            console.error("Exception on returning Server message");
            e.printStackTrace();
        }
    }
}
