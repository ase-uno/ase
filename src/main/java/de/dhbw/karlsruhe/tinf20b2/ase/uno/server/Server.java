package de.dhbw.karlsruhe.tinf20b2.ase.uno.server;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.ConnectionInstance;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.console.ConsoleOut;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonConverter;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonElement;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonString;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.server.game.Game;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.dto.PlayerWithConnection;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.Card;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.CardStack;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.Player;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.dto.SocketNameCombination;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.plugins.CardGenerator;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.plugins.ConsolePlayerConnection;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.plugins.SocketPlayerConnection;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Server extends ConnectionInstance {

    private Game game;

    private List<PlayerWithConnection> players;
    private List<Card> cards;
    private ServerSocket serverSocket;
    private boolean isServerSearchingForInput = true;
    private final List<SocketNameCombination> connections = new ArrayList<>();

    private final ConsoleOut console;

    public Server(String localName, ConsoleOut console) throws IOException {
        super(localName);
        this.console = console;

        startServer();

        initCards();
        initPlayers();
        initGame();
        game.start();
        closeServer();
    }

    private void initCards() {
        cards = new CardGenerator().listAllCards();
        Collections.shuffle(cards);
    }

    private CardStack getPlayerCards() {
        List<Card> playerCards = cards.subList(0, 7);
        cards = cards.subList(7, cards.size());
        return new CardStack(playerCards);
    }

    private void initPlayers() {
        players = new ArrayList<>();
        players.add(new PlayerWithConnection(
                new Player(getLocalName(), getPlayerCards()),
                new ConsolePlayerConnection(this.console)));
        for(SocketNameCombination snc : connections) {
            Player p = new Player(snc.getName(), getPlayerCards());
            PlayerWithConnection pwc = new PlayerWithConnection(p, new SocketPlayerConnection(snc.getSocket()));
            players.add(pwc);
        }
    }

    private void initGame() {
        Card activeCard;
        int i = -1;
        do {
            activeCard = cards.get(++i);
        } while(activeCard.hasAction());
        cards.remove(i);
        game = new Game(players, new CardStack(cards), activeCard);
    }

    private void startServer() throws IOException {

        console.println("Waiting for players");
        serverSocket = new ServerSocket(9999);

        Thread thread = new Thread(() -> {
            while(isServerSearchingForInput) {
                try {
                    Socket socket = serverSocket.accept();
                    if(!isServerSearchingForInput) {
                        socket.close();
                        return;
                    }
                    String name = readName(socket);
                    connections.add(new SocketNameCombination(socket, name));
                    console.println("Player " + name + " connected");
                } catch (IOException e) {
                    console.error("Error in Socket connection loop");
                }
            }
        });
        thread.start();

        console.println("Press Enter to proceed");
        Scanner scanner = new Scanner(System.in);
        while(connections.isEmpty()) {
            scanner.nextLine();
            if(connections.isEmpty()) {
                console.error("No players connected yet, not starting...");
            }
        }
        isServerSearchingForInput = false;
    }

    private void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException ignored) {
            // if that throws an error, the connection is probably already closed
        }
    }

    private String readName(Socket socket) {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            JsonElement jsonElement = new JsonConverter().fromJson(dataInputStream.readUTF());
            return ((JsonString) jsonElement).getValue();
        } catch (Exception e) {
            return "";
        }
    }

}
