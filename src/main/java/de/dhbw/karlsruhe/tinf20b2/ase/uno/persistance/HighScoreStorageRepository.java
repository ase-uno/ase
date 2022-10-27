package de.dhbw.karlsruhe.tinf20b2.ase.uno.persistance;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.dto.SimplePlayer;

public interface HighScoreStorageRepository {

    void addWin(SimplePlayer player);
    int getWins(SimplePlayer player);

}
