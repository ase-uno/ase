package de.dhbw.karlsruhe.tinf20b2.ase.uno.persistance;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonElement;

public interface AbstractStorageRepository {

    JsonElement getFile(String fileName);
    void storeFile(String fileName, JsonElement content);

}
