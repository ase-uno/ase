package de.dhbw.karlsruhe.tinf20b2.ase.uno.server.game;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.dto.PlayerWithConnection;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.*;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.persistance.HighScoreStorageRepository;

import java.util.List;
import java.util.Optional;

public class Game {

    private final List<PlayerWithConnection> players;
    private final CardStack cardStack;

    private Card activeCard;
    private int activeIndex = 0;
    private int direction = 1;
    private final HighScoreStorageRepository highScoreStorageRepository;


    public Game(
            List<PlayerWithConnection> players,
            CardStack cardStack,
            Card activeCard,
            HighScoreStorageRepository highScoreStorageRepository) {
        this.players = players;
        this.cardStack = cardStack;
        this.activeCard = activeCard;
        this.highScoreStorageRepository = highScoreStorageRepository;
    }

    public void start() {
        while(!isGameFinished()) {
            broadcastActivePlayer(getActivePlayer().getPlayer());
            next();
        }
        Player winner = getWinner();
        highScoreStorageRepository.addWin(winner);
        broadcastWinner(winner);
        broadcastHighScore();
    }

    private void broadcastHighScore() {
        List<SimplePlayer> simplePlayers = players.stream()
                .map(pwc -> (SimplePlayer) pwc.getPlayer())
                .toList();
        HighScore highScore = highScoreStorageRepository.getHighScore()
                .filter(simplePlayers);
        for(PlayerWithConnection p : players) {
            p.getPlayerConnection().broadcastHighScore(highScore);
        }
    }

    private void broadcastActivePlayer(Player activePlayer) {
        for(PlayerWithConnection p : players) {
            p.getPlayerConnection().broadcastActivePlayer(activePlayer);
        }
    }

    public void next() {
        Card card;
        do {
            card = getActivePlayer().input(activeCard);
        } while(card != null && !activeCard.isCompatibleWith(card));

        int nextPlayerOffset = 1;
        Card nextNewCard = card;

        if(card != null) {
            if(card.hasAction()) {

                for (int i = 0; i < card.getAction().getDraw(); i++) {
                    getNextPlayer().getPlayer().getCardStack().add(cardStack.consumeFirst());
                }

                if (card.getAction().getAction() == Action.BLOCK) {
                    nextPlayerOffset = 2;
                } else if (card.getAction().getAction() == Action.CHANGE_DIRECTION) {
                    changeDirection();
                } else if (card.getAction().getAction() == Action.CHANGE_COLOR) {
                    CardColor color = getActivePlayer().getPlayerConnection().inputColor();
                    nextNewCard = new Card(color);
                }
            }
            getActivePlayer().getPlayer().getCardStack().remove(card);
            activeCard = nextNewCard;
            cardStack.add(card);
        } else {
            getActivePlayer().getPlayer().getCardStack().add(cardStack.consumeFirst());
        }

        activeIndex = getNextPlayerIndex(nextPlayerOffset);
    }

    public boolean isGameFinished() {
        if(cardStack.isFinished()) {
            return true;
        }
        for(PlayerWithConnection player : players) {
            if(player.getPlayer().getCardStack().isFinished()) {
                return true;
            }
        }
        return false;
    }

    private void changeDirection() {
        direction = direction * -1;
    }

    private PlayerWithConnection getActivePlayer() {
        return players.get(activeIndex);
    }

    private int getNextPlayerIndex(int offset) {
        return (activeIndex + offset * direction + players.size()) % players.size();
    }

    private  PlayerWithConnection getNextPlayer() {
        return players.get(getNextPlayerIndex(1));
    }

    private Player getWinner() {
        Optional<Player> optionalPlayer = players.stream()
                .map(PlayerWithConnection::getPlayer)
                .filter(p -> p.getCardStack().isFinished())
                .findFirst();
        return optionalPlayer.orElse(null);
    }

    private void broadcastWinner(Player winner) {
        for(PlayerWithConnection player : players) {
            player.getPlayerConnection().broadcastWinner(winner);
        }
    }

}
