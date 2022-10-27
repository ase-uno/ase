package de.dhbw.karlsruhe.tinf20b2.ase.uno.persistance;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonConverter;
import de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json.JsonElement;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileStorage implements AbstractStorageRepository {
    @Override
    public JsonElement getFile(String fileName) {

        try {
            String content = new String(Files.readAllBytes(Path.of(fileName)));
            return new JsonConverter().fromJson(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void storeFile(String fileName, JsonElement content) {
        Path path = Path.of(fileName);
        try {
            if(!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
        } catch (Exception e) {
            return;
        }
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(content.toJson());
        } catch (IOException ignored) {
            //if it does not work, we just ignore it :)
        }
    }
}
