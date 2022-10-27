package de.dhbw.karlsruhe.tinf20b2.ase.uno.server;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.dto.SimplePlayer;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.persistance.AbstractStorageRepository;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.persistance.HighScoreStorageRepository;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.*;

import java.io.File;

public class HighScoreStorage implements HighScoreStorageRepository {

    private final AbstractStorageRepository abstractStorageRepository;
    private static final String FILE_PATH = "storage" + File.separator + "highscore.json";

    public HighScoreStorage(AbstractStorageRepository abstractStorageRepository) {
        this.abstractStorageRepository = abstractStorageRepository;
    }

    @Override
    public void addWin(SimplePlayer player) {

        JsonObject jsonObject = getHighScoreFile();
        System.out.println(jsonObject.toJson());
        int wins = getWins(jsonObject, player);
        System.out.println(wins);
        jsonObject.set(player.getName(), new JsonNumber(wins + 1));
        System.out.println(jsonObject.toJson());
        abstractStorageRepository.storeFile(FILE_PATH, jsonObject);

    }

    @Override
    public int getWins(SimplePlayer player) {

        JsonObject jsonObject = getHighScoreFile();
        return getWins(jsonObject, player);

    }

    private JsonObject getHighScoreFile() {
        JsonObject jsonObject = (JsonObject) abstractStorageRepository.getFile(FILE_PATH);
        return jsonObject != null ? jsonObject : new JsonObject();
    }

    private int getWins(JsonObject highScoreFile, SimplePlayer player) {
        JsonNumber winsJson = (JsonNumber) highScoreFile.get(player.getName());
        return winsJson != null ? winsJson.getValue().intValue() : 0;
    }
}
