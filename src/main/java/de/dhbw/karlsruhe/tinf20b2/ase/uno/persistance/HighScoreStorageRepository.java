package de.dhbw.karlsruhe.tinf20b2.ase.uno.persistance;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.HighScore;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.SimplePlayer;

public interface HighScoreStorageRepository {

    void addWin(SimplePlayer player);
    HighScore getHighScore();

}
